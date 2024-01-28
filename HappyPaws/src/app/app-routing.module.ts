import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminCategorymanagementComponent } from './admin-categorymanagement/admin-categorymanagement.component';
import { AdminOrdermanagementComponent } from './admin-ordermanagement/admin-ordermanagement.component';
import { AdminProductmanagementComponent } from './admin-productmanagement/admin-productmanagement.component';
import { AdminUsermanagementComponent } from './admin-usermanagement/admin-usermanagement.component';
import { AdminComponent } from './admin/admin.component';
import { AuthGuard } from './auth/auth.guard';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { MyordersComponent } from './myorders/myorders.component';
import { ProductComponent } from './product/product.component';
import { ProductdetailComponent } from './productdetail/productdetail.component';
import { ProfileComponent } from './profile/profile.component';
import { SignupComponent } from './signup/signup.component';
import { VieworderComponent } from './vieworder/vieworder.component';
import { WishlistComponent } from './wishlist/wishlist.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'home', component: HomepageComponent, canActivate: [AuthGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  {
    path: 'product/:categoryId',
    component: ProductComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'productdetail/:productid',
    component: ProductdetailComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'admincategory',
    component: AdminCategorymanagementComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'adminproduct',
    component: AdminProductmanagementComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'adminuser',
    component: AdminUsermanagementComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'adminorder',
    component: AdminOrdermanagementComponent,
    canActivate: [AuthGuard],
  },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'myorders', component: MyordersComponent, canActivate: [AuthGuard] },
  { path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard] },
  {
    path: 'search/:keyword',
    component: ProductComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'vieworder/:id',
    component: VieworderComponent,
    canActivate: [AuthGuard],
  },
  { path: 'wishlist', component: WishlistComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
