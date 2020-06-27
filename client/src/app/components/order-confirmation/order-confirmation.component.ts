import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrls: ['./order-confirmation.component.css']
})
export class OrderConfirmationComponent implements OnInit {


  orderNumber;
  order;
  urlOrder = 'http://localhost:8083/orders';
  authPW :String = 'ng';

  constructor(private http: HttpClient) {
    if(this.orderNumber != null){
      this.getOrder();
    }
  }


  ngOnInit(): void {
  }

  setOrderNumber(responseNumber){
    this.orderNumber = responseNumber;
  }

  getOrder() {
    const headers = {'Authorization':""+this.authPW};
      this.urlOrder = this.urlOrder + "/" + this.orderNumber;
      this.http.get(this.urlOrder, { headers }).subscribe((order) => {
        this.order = JSON.parse(JSON.stringify(order));
      });
  }
}
