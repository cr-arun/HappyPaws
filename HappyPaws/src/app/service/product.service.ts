import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiUrl } from 'src/environments/environment';
import { Product } from '../entity/product';
import { ProductDto } from '../entity/product-dto';
import { UserauthService } from './userauth.service';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  id: number;
  constructor(
    private httpClient: HttpClient,
    private userAuth: UserauthService
  ) {}
  public toProducts(categoryId: number): Observable<any> {
    let temo = this.userAuth.getUserData();
    this.id = temo.id;
    return this.httpClient.get(
      apiUrl + 'allprod/' + categoryId + '/' + this.id
    );
  }
  addnewProduct(product1: ProductDto) {
    return this.httpClient.post(apiUrl + 'addproduct', product1);
  }
  getProductList(): Observable<any> {
    return this.httpClient.get(apiUrl + 'allproducts');
  }
  getProductById(productid: number) {
    return this.httpClient.get(apiUrl + 'getproduct/' + productid);
  }
  editProduct(pid: number, product: Product) {
    return this.httpClient.post(apiUrl + 'editproduct/' + pid, product);
  }
  deleteProductById(pid: number) {
    return this.httpClient.get(apiUrl + 'deleteproduct/' + pid);
  }
  public searchProducts(theKeyword: string): Observable<any> {
    return this.httpClient.get(apiUrl + 'search/' + theKeyword);
  }
  saveWish(product: Product) {
    return this.httpClient.post(apiUrl + 'savewish', product);
  }
}
