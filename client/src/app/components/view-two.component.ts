import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-view-two',
  templateUrl: './view-two.component.html',
  styleUrls: ['./view-two.component.css']
})
export class ViewTwoComponent implements OnInit, OnDestroy{

  queryParam$!: Subscription
  formData!: FormData

  constructor(private activatedRoute:ActivatedRoute, private httpClient:HttpClient){}

  ngOnInit(): void {
    this.queryParam$ = this.activatedRoute.queryParams.subscribe(
      async (queryParams) => {
        this.formData = queryParams['formData']
        // await this.upload()
      }
    )
  }
  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }

  // upload(){

  //   const headers = new HttpHeaders().set('Accept','application/json').set('Content-Type', 'multipart/form-data')

  //   this.httpClient.post<any>(`/upload`,this.formData, { headers })
  //   .subscribe({
  //     next: v => {
  //       // this.payload = v as PostResponse
  //       console.log('posted to server')
  //     }
  //   })
    
  // }

}
