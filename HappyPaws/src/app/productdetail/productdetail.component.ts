import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../entity/product';
import { Wishlistdto } from '../entity/wishlistdto';
import { ReviewComponent } from '../review/review.component';
import { CartService } from '../service/cart.service';
import { ProductService } from '../service/product.service';
import { UserauthService } from '../service/userauth.service';
import { WishlistService } from '../service/wishlist.service';

@Component({
  selector: 'app-productdetail',
  templateUrl: './productdetail.component.html',
  styleUrls: ['./productdetail.component.css'],
})
export class ProductdetailComponent implements OnInit {
  product: any;
  wishproducts: any;
  productid: number;
  quantity = 1;
  wishDto: Wishlistdto = new Wishlistdto();
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private router: Router,
    private wishlistService: WishlistService,
    private userAuth: UserauthService
  ) {}

  ngOnInit(): void {
    this.productid = this.route.snapshot.params['productid'];
    this.getProduct();
  }
  getProduct() {
    this.wishlistService.getwishlist().subscribe((data) => {
      this.wishproducts = data.items;
    });
    this.productService.getProductById(this.productid).subscribe(
      (data) => {
        this.product = data;
        //console.log(this.product);
        for (let i = 0; i < this.wishproducts.length; i++) {
          if (
            this.product.productid === this.wishproducts[i].product.productid
          ) {
            this.product.wishlist = true;
          }
        }
        this.ngOnInit();
      },
      (error) => {
        console.log(error);
      }
    );
  }
  getProductId() {
    return this.productid;
  }
  add() {
    this.quantity += 1;
  }
  minus() {
    if (this.quantity != 1) {
      this.quantity -= 1;
    }
  }
  toCart() {
    this.cartService.addToCart(this.productid, this.quantity);
  }
  buynow() {
    this.cartService.addToCart(this.productid, this.quantity);
    this.router.navigate(['cart']);
  }
  wishToggle(productid: number) {
    let tempuser = this.userAuth.getUserData();
    this.product.wishlist = !this.product.wishlist;
    this.wishDto.productId = productid;
    this.wishDto.userid = tempuser.id;
    this.wishlistService.addToWishList(this.wishDto).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
  show(productid: number) {
    console.log(productid);
    this.wishlistService.removeWishListItem(productid).subscribe((data) => {
      this.ngOnInit();
    });
  }
}
