import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-one',
  templateUrl: './view-one.component.html',
  styleUrls: ['./view-one.component.css']
})
export class ViewOneComponent implements OnInit, OnDestroy{

  form!:FormGroup

  @ViewChild('file')
  archive! : ElementRef

  bundleId!: string

  constructor(private fb:FormBuilder, private router:Router, private httpClient:HttpClient){}


  ngOnInit(): void {
    this.form = this.createForm()
  }
  ngOnDestroy(): void {
    this.form.reset()
  }

  createForm():FormGroup{
    return this.fb.group({
      name:this.fb.control<string>('',[Validators.required]),
      title:this.fb.control<string>('',[Validators.required]),
      comments:this.fb.control<string>(' '),
      archive:this.fb.control('',[Validators.required]),

    })
  }


  processForm(){

    const formData = new FormData();
    formData.set('name', this.form.value['name']);
    formData.set('title', this.form.value['title'])
    formData.set('comments', this.form.value['comments'])
    formData.set('archive', this.archive.nativeElement.files[0])

    // this.router.navigate(['/view2'],{ queryParams: formData })

    const headers = new HttpHeaders().set('Accept','application/json')

    this.httpClient.post<any>(`/upload`,formData, { headers })
    .subscribe({
      next: v => {
        this.bundleId = v['bundleId']
        console.log('posted to server, bundleId: ' , this.bundleId)

      },
      error: (err) => {
        console.error(err)
        alert(err.error.error)
      },
      complete: () => this.router.navigate(['/view2', this.bundleId])
    })

    
  }

}
