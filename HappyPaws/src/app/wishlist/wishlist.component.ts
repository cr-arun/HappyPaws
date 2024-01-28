import { Component, OnInit } from '@angular/core';
import { CartService } from '../service/cart.service';
import { WishlistService } from '../service/wishlist.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css'],
})
export class WishlistComponent implements OnInit {
  constructor(
    private wishlistService: WishlistService,
    private cartService: CartService
  ) {}
  wish: any;
  products: any;
  ngOnInit(): void {
    this.wishlistService.getwishlist().subscribe(
      (data) => {
        console.log(data.items);
        this.products = data.items;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  toCart(temp: number) {
    let productid = temp;
    let quantity = 1;
    console.log(productid, quantity);
    this.cartService.addToCart(productid, quantity);
  }
  removeItem(wishItemid: number) {
    this.wishlistService.deleteItemWishList(wishItemid).subscribe((data) => {
      console.log(data);
      this.ngOnInit();
    });
  }
}
