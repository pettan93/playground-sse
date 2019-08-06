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
              private snackBar: MatSnackBar) {
  }

  dogsCount: number;
  dogStationStatus = false;

  ngOnInit() {
    this.dogService.dogs.subscribe((dogs) => {
        this.dogsCount = dogs != null ? dogs.length : 0;
      }
    );

    this.dogService.dogStationStatus.subscribe((status) => {
        this.dogStationStatus = status;
      }
    );

    this.dogService.connectToNotification();
    this.dogService.connectToStatus();
  }


  refresh() {
    this.snackBar.open('Manual dogs reload!', null, {duration: 2000});
    this.dogService.manualReload();
  }


  makeInstantDog() {
    this.dogService.createInstantDog();
  }

  makeDelayedDog() {
    this.dogService.createDelayedDog();
  }

  deleteAll() {
    this.dogService.deleteAll();
  }


}
