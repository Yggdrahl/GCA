import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductViewComponent } from './components/product-view/product-view.component';
import { CartComponent } from './components/cart/cart.component';
import { OrderConfirmationComponent } from './components/order-confirmation/order-confirmation.component';


const routes: Routes = [
  {path: 'Product View', component:ProductViewComponent},
  {path: 'Cart', component:CartComponent},
  {path: 'orderConfirmation', component:OrderConfirmationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
