import {Component, OnInit} from '@angular/core';
import {DogService} from '../dog.service';

@Component({
  selector: 'app-dog-lister',
  templateUrl: './dog-lister.component.html',
  styleUrls: ['./dog-lister.component.css']
})
export class DogListerComponent implements OnInit {

  constructor(private dogService: DogService) {
  }

  dogs: Dog[];

  ngOnInit() {

    // load attribs
    this.dogService.dogs.subscribe((dogs) => {
        this.dogs = dogs;
      }
    );
  }


}
