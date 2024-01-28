import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../entity/product';
import { Wishlistdto } from '../entity/wishlistdto';
import { CartService } from '../service/cart.service';
import { ProductService } from '../service/product.service';
import { UserauthService } from '../service/userauth.service';
import { WishlistService } from '../service/wishlist.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  categoryId: number;
  products: Product[];
  tempproducts: Product[];
  wishproducts: any;
  productid: number;
  wishDto: Wishlistdto = new Wishlistdto();
  wishListed: boolean = false;
  searchMode: boolean = false;
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private cartService: CartService,
    private wishlistService: WishlistService,
    private userAuth: UserauthService
  ) {}

  ngOnInit(): void {
    this.categoryId = this.route.snapshot.params['categoryId'];

    this.listProducts();
  }
  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');
    this.wishlistService.getwishlist().subscribe((data) => {
      this.wishproducts = data.items;
      console.log(this.wishproducts);
    });
    if (this.searchMode) {
      console.log(this.searchMode);
      this.handleSearchProducts();
      this.searchMode = false;
    } else this.showProducts();
  }

  showProducts() {
    this.productService.toProducts(this.categoryId).subscribe(
      (data) => {
        this.products = data;
        //console.log(this.products);
        // this.wish();

        console.log(this.products);
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
  showDetails(tempid: number) {
    this.productid = tempid;
    this.router.navigate(['productdetail', this.productid]);
  }
  handleSearchProducts() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword');
    console.log(theKeyword);
    this.productService.searchProducts(theKeyword).subscribe((data) => {
      this.products = data;
    });
  }
  wishToggle(productid: number, temp: Product) {
    let tempuser = this.userAuth.getUserData();

    temp.wishlist = !temp.wishlist;

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
  wishRemoveToggle(productid: number) {
    this.wishlistService.removeWishListItem(productid).subscribe((data) => {
      console.log(data);
    });
    this.ngOnInit();
  }
  show(productid: number, temp: Product) {
    console.log(productid);
    this.wishlistService.removeWishListItem(productid).subscribe((data) => {
      console.log(data);
      this.ngOnInit();
    });
  }
}
