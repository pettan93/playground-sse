import {Injectable} from '@angular/core';
import {BehaviorSubject, interval, Observable} from 'rxjs';
import {startWith, switchMap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DogService {

  private backendUrl = 'http://localhost:8080';

  private dogObservable: BehaviorSubject<Dog[]> = new BehaviorSubject([]);

  constructor(private http: HttpClient) {
    this.loadInitialData();
  }

  get dogs() {
    return this.dogObservable.asObservable();
  }

  loadInitialData() {

    interval(15000)
      .pipe(
        startWith(0),
        switchMap(() => this.fetchDogs())
      )
      .subscribe((dogs: Dog[]) => {
        this.dogObservable.next(dogs);
      });
  }


  fetchDogs(): Observable<Dog[]> {
    console.log('periodically fetching dogs..');
    return this.http.get<Dog[]>(this.backendUrl + '/dogs');
  }


  // addAttribute(attribute: Attribute): Observable<Attribute> {
  //
  //   const obs = this.adminService.createAttribute(attribute);
  //
  //   obs.subscribe((data) => {
  //     const array = this.attributesObservable.value;
  //     array.push(data);
  //
  //     this.attributesObservable.next(array);
  //   });
  //
  //
  //   return obs;
  // }


}
