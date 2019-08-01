import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { DogListerComponent } from './dog-lister/dog-lister.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {MatListModule} from '@angular/material/list';

@NgModule({
  declarations: [
    AppComponent,
    DogListerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatListModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
