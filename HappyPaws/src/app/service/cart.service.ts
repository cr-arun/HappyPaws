import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { apiUrl } from 'src/environments/environment';
import { AddToCart } from '../entity/addtocart';
import { Cart } from '../entity/cart';
import { UserauthService } from './userauth.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  public cartItemList : any =[]
  public productList = new BehaviorSubject<any>([]);
  id:number;
  cartItemid:number;
  temp:any;
  cart=new Cart();
  add=new AddToCart();
  constructor(private httpClient:HttpClient,private userAuth:UserauthService) { 
    
  }
  getProducts(){
    return this.productList.asObservable();
  }
  getProductsByUser(){
    
    let temo=this.userAuth.getUserData();
    this.id=temo.id;
    console.log(this.id);
    
    return this.httpClient.get(apiUrl+"listcart/"+this.id)
  }
  addToCart(pid:number,quantity:number){
    let temo=this.userAuth.getUserData();
    this.id=temo.id;
    console.log(this.id);
    this.add.productId=pid;
    this.add.quantity=quantity;
    console.log(this.id,this.add);
    this.httpClient.post(apiUrl+"cartadd/"+this.id,this.add).subscribe(
      data=>{
        this.temp=data;
        this.cart=this.temp;
        this.cartItemList=this.cart.items;
       
      },error=>{
        console.log(error);
      }
    );
   
  }

 updateCart(item:any){
  return this.httpClient.post(apiUrl+"editcart/"+this.id,item);
 }

  removeAllCart(){
    this.cartItemList = []
    this.productList.next(this.cartItemList);
  }
  removeCartItem(cartItemid:number){
    let temo=this.userAuth.getUserData();
    this.id=temo.id;
    this.cartItemid=cartItemid;
    return this.httpClient.get(apiUrl+"deletecartitem/"+this.cartItemid+"/"+this.id);
    
  }
}
