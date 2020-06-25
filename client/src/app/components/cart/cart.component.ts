import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {stringify} from "querystring";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  data;
  date: number = new Date().getFullYear();
  urlCart = 'http://localhost:8084/cart';
  urlEmptyCart = 'http://localhost:8084/emptyCart';
  urlShipping = 'http://localhost:8082/getShippingCosts/?costs=';
  cartContent;
  totalPrice = 0;
  shippingCost = 0;

  constructor(private http: HttpClient) {

    this.getCart();
  }

  ngOnInit(): void {
    this.getShippingCost();
  }

  sumPrice(cart: any) {
    this.totalPrice = 0;
    for (var i = 0; i < cart.length; i++) {
      this.totalPrice += cart[i].price;
    }

  }

  getCart() {
    this.http.get(this.urlCart).subscribe((data: any) => {
      this.cartContent = JSON.parse(JSON.stringify(data));
      this.sumPrice(this.cartContent);
    });
    this.getShippingCost();
  }


  deleteCart() {
    const headers = {'Content-Type': 'application/json'};
    this.http.delete(this.urlEmptyCart).subscribe(data => {
      this.getCart();
      this.sumPrice(this.cartContent);
    });
  }

  getShippingCost() {
    this.http.get(this.urlShipping + this.totalPrice).subscribe((data: any) => {
      this.shippingCost = data;
    });
  }
}
