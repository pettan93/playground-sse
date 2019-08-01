import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from '@angular/material';
import {DogService} from './dog.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private dogService: DogService,
              private _snackBar: MatSnackBar) {
  }


  dogsCount: number;

  ngOnInit() {
    this.dogService.dogs.subscribe((dogs) => {
        this.dogsCount = dogs != null ? dogs.length : 0;
      }
    );
  }


  refresh() {
    this.dogService.manualReload();
    this._snackBar.open('Manual dogs refresh!', null, {duration: 2000});
  }


  instantDog() {
    this.dogService.createInstantDog();
  }

  deleteAll() {
    this.dogService.deleteAll();
  }


}
