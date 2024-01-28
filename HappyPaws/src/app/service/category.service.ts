import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { apiUrl } from 'src/environments/environment';
import { Category } from '../entity/category';
@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  category:Array<Category>
  constructor(private httpClient:HttpClient) { }
  getCategoryList() :Observable<any>{
    return this.httpClient.get(apiUrl+'allcategory');
  }
  getCategoryById(categoryId: number):Observable<Object> {
    return this.httpClient.get(apiUrl+'getcategory/'+categoryId);
  }
  deleteCategoryById(categoryId: number) {
   return this.httpClient.get(apiUrl+'deletecategory/'+categoryId);
  }
  editCategory(categoryId: number, category: Category) {
    return this.httpClient.post(apiUrl+'editcategory/'+categoryId,category)
  }
  addnewCategory(category1: Category) {
    return this.httpClient.post(apiUrl+'addcategory',category1);
  }
  
}
