import {Component, OnInit} from '@angular/core';
import {DogService} from '../dog.service';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-dog-lister',
  templateUrl: './dog-lister.component.html',
  styleUrls: ['./dog-lister.component.css']
})
export class DogListerComponent implements OnInit {

  constructor(private dogService: DogService,
              private _snackBar: MatSnackBar) {
  }

  dogs: Dog[];

  ngOnInit() {
    this.dogService.dogs.subscribe((dogs) => {
        this.dogs = dogs;
      }
    );
  }



}
