import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { GetResponse } from '../models';
import { map } from 'rxjs';

@Component({
  selector: 'app-view-zero',
  templateUrl: './view-zero.component.html',
  styleUrls: ['./view-zero.component.css']
})
export class ViewZeroComponent implements OnInit{

  temp!: GetResponse
  result!: GetResponse[]
  arr!: any[]

  constructor(private httpClient:HttpClient){}

  ngOnInit(): void {
    
    const headers = new HttpHeaders().set('Accept','application/json')
    this.httpClient.get('/bundles',{headers})
    .subscribe({
      next: v => {
        // console.log(v)
        this.arr = v as GetResponse[]
        console.log(this.arr)
        this.arr.map( g => {
          this.temp = g as GetResponse
          return this.temp
          
        })
        this.result.push(this.temp)
        
      }

    })
  }

}
