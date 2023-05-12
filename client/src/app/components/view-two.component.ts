import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Bundle } from '../models';

@Component({
  selector: 'app-view-two',
  templateUrl: './view-two.component.html',
  styleUrls: ['./view-two.component.css']
})
export class ViewTwoComponent implements OnInit, OnDestroy{

  param$!: Subscription
  bundleId!: string
  result!: Bundle

  constructor(private activatedRoute:ActivatedRoute, private httpClient:HttpClient){}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.bundleId = params['bundleId']
        this.getBundle()
      }
    )
  }
  
  ngOnDestroy(): void {
    if(this.param$){
      this.param$.unsubscribe()
    }
  }
  
  getBundle() {
    
    const headers = new HttpHeaders().set('Accept','application/json')

    this.httpClient.get<any>(`/bundle/${this.bundleId}`,{headers}).subscribe({
      next: v=>{
        this.result = v as Bundle
        this.result.date = v['date']['$date']
        // console.log(v['date'])
      },
      error: (err) => {
        console.error(err)
        alert(err.error.error)
      },
      // complete:
    })
  }
  
}
