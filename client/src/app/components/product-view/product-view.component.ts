import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-product-view',
  templateUrl: './product-view.component.html',
  styleUrls: ['./product-view.component.css']
})
export class ProductViewComponent implements OnInit {
  image;
  data;
  requestOptions;
  headerDict;

  urlCatalog = 'http://localhost:8081/products';
  urlCart = 'http://localhost:8084/cart';
  products;



  addToCart(id: number) {
    this.headerDict = {
    'Content-Type': 'application/json',
  };
    this.requestOptions = {
      headers: new Headers(this.headerDict)
    };
    this.data = JSON.stringify('{"id":1,"price":149.98,"name":"Camera","image":null}');
    this.http.post(this.urlCart, this.data, this.requestOptions);
    console.log('hallo');
  }


  constructor(private http: HttpClient) {
    this.http.get(this.urlCatalog).subscribe((data: any) => {
      console.log(data);
      this.products = JSON.stringify(data);
      this.image = JSON.stringify(data.image);
      this.image = this.image.substring(1, this.image.length - 1);
      console.log(this.products[0].name);
    });
  }

  ngOnInit(): void {
  }

}
