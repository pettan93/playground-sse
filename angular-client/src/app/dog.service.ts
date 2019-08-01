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

  private dogObservable: BehaviorSubject<Dog[]> = new BehaviorSubject([]);

  constructor(private http: HttpClient,
              private _snackBar: MatSnackBar) {
    this.loadInitialData();
  }

  get dogs() {
    return this.dogObservable.asObservable();
  }

  loadInitialData() {

    interval(30000)
      .pipe(
        startWith(0),
        switchMap(() => {
          this._snackBar.open('Periodical dog fetch.', null, {duration: 2000});
          return this.performFetch();
        })
      )
      .subscribe((dogs: Dog[]) => {
        this.dogObservable.next(dogs);
      });
  }


  performFetch(): Observable<Dog[]> {
    return this.http.get<Dog[]>(this.backendUrl + '/dog/list');
  }

  performDelete(): Observable<void> {
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


}
