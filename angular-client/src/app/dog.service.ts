import {Injectable} from '@angular/core';
import {BehaviorSubject, interval, Observable} from 'rxjs';
import {delay, startWith, switchMap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material';
import {Dog} from './Dog';
import {DogNotification} from './DogNotification';
import {NotifyType} from './NotifyType';
import {DogStationState} from './DogStationState';

@Injectable({
  providedIn: 'root'
})
export class DogService {

  private backendUrl = 'http://localhost:8080';

  private dogObservable: BehaviorSubject<Dog[]> = new BehaviorSubject([]);

  private dogStationStatusObservable: BehaviorSubject<DogStationState> = new BehaviorSubject(DogStationState.IDLE);

  private dogStationSessionUptimeObservable: BehaviorSubject<number> = new BehaviorSubject(1);

  constructor(private http: HttpClient,
              private snackBar: MatSnackBar) {
    this.loadInitialData();
  }

  get dogs() {
    return this.dogObservable.asObservable();
  }

  get dogStationStatus() {
    return this.dogStationStatusObservable.asObservable();
  }

  get dogStationSessionUptime() {
    return this.dogStationSessionUptimeObservable.asObservable();
  }

  private loadInitialData() {
    interval(150000)
      .pipe(
        startWith(0),
        switchMap(() => {
          this.snackBar.open('Periodical dog fetch.', null, {duration: 2000});
          return this.performFetch();
        })
      )
      .subscribe((dogs: Dog[]) => {
        this.dogObservable.next(dogs);
      });
  }


  private performFetch(): Observable<Dog[]> {
    return this.http.get<Dog[]>(this.backendUrl + '/dog/list');
  }

  private performDelete(): Observable<void> {
    return this.http.post<void>(this.backendUrl + '/dog/delete/all', null);
  }


  manualReload() {
    this.performFetch()
      .subscribe((dogs: Dog[]) => {
        this.dogObservable.next(dogs);
      });
  }

  deleteAll() {
    const obs = this.performDelete()
      .subscribe(() => {
        this.dogObservable.next([]);
      });

  }

  createInstantDog(): Observable<Dog> {
    const obs = this.http.post<Dog>(this.backendUrl + '/dog/create/instant', null);

    obs.subscribe((data) => {
      const array = this.dogObservable.value;
      array.push(data);

      this.dogObservable.next(array);
    });

    return obs;
  }

  createDelayedDog(): Observable<DogNotification> {
    const obs = this.http.post<DogNotification>(this.backendUrl + '/dog/create/delayed', null);

    obs.subscribe((data) => {
      this.snackBar.open(data.message, null, {duration: 2000});
    });

    return obs;
  }


  /**
   * SSE
   */
  subscribeNotification(): void {
    const source = new EventSource(this.backendUrl + '/dog/notification/sse');
    source.addEventListener('message', message => {

      // console.log(message);

      const notification: DogNotification = JSON.parse(message.data);
      this.snackBar.open(notification.message, null, {duration: 2000});

      if (notification.notifyType.valueOf() === NotifyType.TRIGGER.valueOf()) {
        this.manualReload();
      }
    });
  }


  subscribeStatus(): void {
    const source = new EventSource(this.backendUrl + '/dog/notification/stream-flux');
    source.addEventListener('message', message => {

      // console.log(message);

      const dogStationState: DogStationState = JSON.parse(message.data);
      this.dogStationStatusObservable.next(dogStationState);
    });
  }

  subscribeSessionUptime(): void {
    const source = new EventSource(this.backendUrl + '/dog/info/stream-flux');
    source.addEventListener('message', message => {

      console.log(message);

      this.dogStationSessionUptimeObservable.next(parseInt(message.data, 0));
    });
  }

}
