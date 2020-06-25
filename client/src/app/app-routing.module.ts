import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductViewComponent } from './components/product-view/product-view.component';
import { CartComponent } from './components/cart/cart.component';
import { OrderConfirmationComponent } from './components/order-confirmation/order-confirmation.component';


const routes: Routes = [
  {path: '', component:ProductViewComponent},
  {path: 'cart', component:CartComponent},
  {path: 'order-confirmation', component:OrderConfirmationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
