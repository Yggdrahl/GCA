import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OrderConfirmationComponent} from "../order-confirmation/order-confirmation.component";
import {Router} from "@angular/router";

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
  urlOrder = 'http://localhost:8083/checkout';
  cartContent;
  totalPrice = 0;
  shippingCost = 0;

  mail: string;
  street: string;
  city: string;
  state: string;
  country: string;
  month: string;
  cvv: string;
  year: number;
  zip: number;
  creditcard: number;
  order:any;
  orderNumber;

  constructor(private http: HttpClient, private router: Router) {
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

  sendOrder() {
    this.order = {
      mail: this.mail,
      street: this.street,
      city: this.city,
      state: this.state,
      country: this.country,
      month: this.month,
      cvv: this.cvv,
      year: this.year,
      zip: this.zip,
      creditcard: this.creditcard
    };
    if (this.order != null) {
      const headers = {'Content-Type': 'application/json'};
      this.http.post(this.urlOrder, this.order, {headers}).subscribe((res) => {
        this.orderNumber = res;
        OrderConfirmationComponent.prototype.setOrderNumber(this.orderNumber);
        this.router.navigate(["/order-confirmation"]);
      });
    }

  }
}
