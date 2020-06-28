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
  authPW :String = 'ng';
  orderButtonBool : Boolean = true;

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
    const headers = {'Authorization':""+this.authPW};
    this.http.get(this.urlCart, { headers }).subscribe((data: any) => {
      this.cartContent = JSON.parse(JSON.stringify(data));
      this.sumPrice(this.cartContent);
    });
    this.getShippingCost();
  }


  deleteCart() {
    const headers = {'Authorization':""+this.authPW};
    this.http.delete(this.urlEmptyCart, { headers }).subscribe(data => {
      this.getCart();
      this.sumPrice(this.cartContent);
    });
  }

  getShippingCost() {
    const headers = {'Authorization':""+this.authPW};
    this.http.get((this.urlShipping + this.totalPrice), { headers }).subscribe((data: any) => {
      this.shippingCost = data;
    });
  }

  checkFormContent() {
    var activateButton : Boolean =
    this.mail != "" && this.mail != null &&
    this.street != "" && this.street != null &&
    this.city != "" && this.city != null &&
    this.state != "" && this.state != null &&
    this.country != "" && this.country != null &&
    this.month != "" && this.month != null &&
    this.cvv != "" && this.cvv != null &&
    this.year != null &&
    this.zip != null &&
    this.creditcard != null;

    this.orderButtonBool = !activateButton;
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
      const headers = {'Content-Type': 'application/json', 'Authorization': ""+this.authPW};
      this.http.post(this.urlOrder, this.order, {headers}).subscribe((res) => {
        this.orderNumber = res;
        //OrderConfirmationComponent.prototype.setOrderNumber(this.orderNumber);
        //this.router.navigate(["/order-confirmation"]);
        if(res != -1) {
          this.router.navigate(["/order-confirmation"], { queryParams: { ordnum: res } });
        }
        
      });
    }

  }
}
