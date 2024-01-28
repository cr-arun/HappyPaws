import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminComponent } from './admin/admin.component';
import { AdminCategorymanagementComponent } from './admin-categorymanagement/admin-categorymanagement.component';
import { AdminProductmanagementComponent } from './admin-productmanagement/admin-productmanagement.component';
import { AdminUsermanagementComponent } from './admin-usermanagement/admin-usermanagement.component';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { FooterComponent } from './footer/footer.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { MyordersComponent } from './myorders/myorders.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProductComponent } from './product/product.component';
import { ProductdetailComponent } from './productdetail/productdetail.component';
import { ProfileComponent } from './profile/profile.component';
import { ReviewComponent } from './review/review.component';
import { SignupComponent } from './signup/signup.component';
import { VieworderComponent } from './vieworder/vieworder.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthGuard } from './auth/auth.guard';
import { AuthInterceptor } from './auth/auth.interceptor';
import { UserService } from './service/user.service';
import { ProductService } from './service/product.service';
import { CategoryService } from './service/category.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { WishlistComponent } from './wishlist/wishlist.component';
import { AdminOrdermanagementComponent } from './admin-ordermanagement/admin-ordermanagement.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    AdminCategorymanagementComponent,
    AdminProductmanagementComponent,
    AdminUsermanagementComponent,
    CartComponent,
    CheckoutComponent,
    FooterComponent,
    HomepageComponent,
    LoginComponent,
    MyordersComponent,
    NavbarComponent,
    ProductComponent,
    ProductdetailComponent,
    ProfileComponent,
    ReviewComponent,
    SignupComponent,
    VieworderComponent,
    WishlistComponent,
    AdminOrdermanagementComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    UserService,
    ProductService,
    CategoryService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
