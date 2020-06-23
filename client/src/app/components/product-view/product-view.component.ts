import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-product-view',
  templateUrl: './product-view.component.html',
  styleUrls: ['./product-view.component.css']
})
export class ProductViewComponent implements OnInit {
  postData = {
    test: 'my content',
  };
  url = 'http://localhost:8081/product/7';
  json;
  constructor(private http: HttpClient) {
    this.http.post(this.url, this.postData).toPromise().then((data:any) => {
      console.log(data);
      this.json = JSON.stringify(data.json);
    });
  }

  ngOnInit(): void {
  }

}
