import {Injectable} from '@angular/core';
import {BehaviorSubject, interval, Observable} from 'rxjs';
import {startWith, switchMap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class DogService {

  private backendUrl = 'http://localhost:8080';

  private dogObservable: BehaviorSubject<Notification[]> = new BehaviorSubject([]);

  constructor(private http: HttpClient,
              private snackBar: MatSnackBar) {
    this.loadInitialData();
  }

  get dogs() {
    return this.dogObservable.asObservable();
  }

  private loadInitialData() {
    interval(30000)
      .pipe(
        startWith(0),
        switchMap(() => {
          this.snackBar.open('Periodical dog fetch.', null, {duration: 2000});
          return this.performFetch();
        })
      )
      .subscribe((dogs: Notification[]) => {
        this.dogObservable.next(dogs);
      });
  }


  private performFetch(): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.backendUrl + '/dog/list');
  }

  private performDelete(): Observable<void> {
    return this.http.post<void>(this.backendUrl + '/dog/delete/all', null);
  }


  manualReload() {
    this.performFetch()
      .subscribe((dogs: Notification[]) => {
        this.dogObservable.next(dogs);
      });
  }

  deleteAll() {
    const obs = this.performDelete()
      .subscribe(() => {
      this.dogObservable.next([]);
    });

  }

  createInstantDog(): Observable<Notification> {
    const obs = this.http.post<Notification>(this.backendUrl + '/dog/create/instant', null);

    obs.subscribe((data) => {
      const array = this.dogObservable.value;
      array.push(data);

      this.dogObservable.next(array);
    });

    return obs;
  }

  createDelayedDog(): Observable<Notification> {
    const obs = this.http.post<Notification>(this.backendUrl + '/dog/create/delayed', null);

    obs.subscribe((data) => {
      this.snackBar.open(data.message, null, {duration: 2000});
    });

    return obs;
  }


  // connect(): void {
  //   const source = new EventSource(this.backendUrl + '/dog/sse');
  //   source.addEventListener('message', message => {
  //     console.log(message);
  //     // this.myData = JSON.parse(message.data);
  //   });
  // }

}
