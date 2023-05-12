import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ViewOneComponent } from './components/view-one.component';
import { ViewTwoComponent } from './components/view-two.component';
import { ViewZeroComponent } from './components/view-zero.component';
import { ReactiveFormsModule } from '@angular/forms';
import{ HttpClientModule } from '@angular/common/http'


@NgModule({
  declarations: [
    AppComponent,
    ViewOneComponent,
    ViewTwoComponent,
    ViewZeroComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
