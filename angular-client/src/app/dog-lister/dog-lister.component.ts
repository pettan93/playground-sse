import {Component, OnInit} from '@angular/core';
import {DogService} from '../services/dog.service';
import {MatSnackBar} from '@angular/material';
import {Dog} from '../models/Dog';
import {interval, Observable} from 'rxjs';
import {SocketClientService} from '../services/socket-client.service';
import {first, map} from 'rxjs/operators';

@Component({
  selector: 'app-dog-lister',
  templateUrl: './dog-lister.component.html',
  styleUrls: ['./dog-lister.component.css']
})
export class DogListerComponent implements OnInit {

  constructor(private dogService: DogService,
              private socketClientService: SocketClientService,
              private _snackBar: MatSnackBar) {
  }

  dogs: Dog[];


  static getPostListing(post: any): String {
    console.log(post);
    const postedAt = new Date(post['postedAt']);
    return {...post, postedAt};
  }

  ngOnInit() {
    this.dogService.dogs.subscribe((dogs) => {
        this.dogs = dogs;
      }
    );

    this.findAll().subscribe((events) => {
        console.log(events);
      }
    );


    const source = interval(5000);
    source.subscribe(val => this.save('ahoj' + new Date()));


  }

  save(post: string) {
    console.log('save!');
    return this.socketClientService.send('/events/create', post);
  }


  findAll(): Observable<string[]> {
    return this.socketClientService
      .onMessage('/events/get')
      .pipe(map(events => events));
    // .pipe(first(), map(posts => posts.map(DogListerComponent.getPostListing)));

  }

}
