import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from '@angular/material';
import {DogService} from './services/dog.service';
import {DogStationState} from './models/DogStationState';

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
  dogStationStatusWorking = false;
  dogStationSessionUptime: number;

  ngOnInit() {
    this.dogService.dogs.subscribe((dogs) => {
        this.dogsCount = dogs != null ? dogs.length : 0;
      }
    );

    // this.dogService.dogStationStatus.subscribe((status) => {
    //     this.dogStationStatusWorking = (status.valueOf() === DogStationState.WORKING.valueOf());
    //   }
    // );
    //
    // this.dogService.dogStationSessionUptime.subscribe((uptime) => {
    //     this.dogStationSessionUptime = uptime;
    //   }
    // );
    //
    // this.dogService.subscribeNotification();
    // this.dogService.subscribeStatus();
    // this.dogService.subscribeSessionUptime();
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
