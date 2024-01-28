import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiUrl } from 'src/environments/environment';
import { Wishlistdto } from '../entity/wishlistdto';
import { UserauthService } from './userauth.service';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  id: number;
  constructor(private http: HttpClient, private userAuth: UserauthService) {}
  getwishlist(): Observable<any> {
    let temp = this.userAuth.getUserData();
    let id = temp.id;
    return this.http.get(apiUrl + 'getwishlist/' + id);
  }
  addToWishList(wish: Wishlistdto) {
    return this.http.post(apiUrl + 'addtowish', wish);
  }
  deleteItemWishList(wishlistItemId: number) {
    console.log(wishlistItemId);
    let temp = this.userAuth.getUserData();
    this.id = temp.id;
    console.log(this.id);
    return this.http.get(
      apiUrl + 'deletewishlistitem/' + wishlistItemId + '/' + this.id
    );
  }
  removeWishListItem(productid: number) {
    let temp = this.userAuth.getUserData();
    this.id = temp.id;
    console.log(this.id);
    return this.http.get(
      apiUrl + 'removewishlistitem/' + productid + '/' + this.id
    );
  }
}
