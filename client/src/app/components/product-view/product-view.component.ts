import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

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


  constructor(private http: HttpClient) {
    this.http.get(this.urlCatalog).subscribe((data: any) => {
      console.log(data);
      this.products = JSON.parse(JSON.stringify(data)); //Es war doch ein ".stringify" in einem ".parse" (genau wie in GFE)
      //this.image = JSON.stringify(data.image);
      //this.image = this.image.substring(1, this.image.length - 1);
      console.log(this.products[0].name); //Das gibt jetzt "Camera" aus, weil "Camera" das erste Produkt ist!

    });
  }

  ngOnInit(): void {
  }


  addToCart(id: number) {

    //Proukt mit id finden in "this.products"
    var product = null;
    for (var i = 0; i < this.products.length; i++) {
      if (this.products[i].id == id) {
        product = this.products[i];
      }
    }

    if (product != null) {
      const headers = { 'Content-Type': 'application/json' }
      var jcode = this.products[0];
      //const body = { id:99, price:199.99, name:"Teekanne", imag:null }; //Wenn man das JSON "hardcodet"
      //const body = JSON.parse(JSON.stringify('{"id":80,"price":149.98,"name":"Camera","image":null}')); //Wenn das JSON aus einem String erzeugt wird
      //const body = this.products[0]; //Wenn das JSON aus dem Catalog-Array kommt
      this.http.post(this.urlCart, product, { headers }).subscribe(data => { //Statt "product" kann auch "body" übergeben werden
      console.log("Product was added to cart");
      });
    }

  }


  /* //Wenn man die gleiche Funktion auch mit einem JSON Objekt erstellen würde (funktioniert durch html aber nicht)
  addToCart(product: JSON) {
    if (product != null) {
      const headers = { 'Content-Type': 'application/json' }
      //const body = { id:99, price:199.99, name:"Teekanne", imag:null }; //Wenn man das JSON "hardcodet"
      //const body = JSON.parse(JSON.stringify('{"id":80,"price":149.98,"name":"Camera","image":null}')); //Wenn das JSON aus einem String erzeugt wird
      //const body = this.products[0]; //Wenn das JSON aus dem Catalog-Array kommt
      this.http.post(this.urlCart, product, { headers }).subscribe(data => { //Statt "product" kann auch "body" übergeben werden
        console.log("Product was added to cart");
      });
    }
  }
  */
}
