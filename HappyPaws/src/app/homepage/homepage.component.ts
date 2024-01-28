import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from '../entity/category';
import { CategoryService } from '../service/category.service';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  categorys: any=[];
  
  categoryId:number;
  constructor(private categoryService:CategoryService,private router:Router) { }

  ngOnInit(): void {
    this.listCategories();
  }
  listCategories(){
    this.categoryService.getCategoryList().subscribe(
      data=>{
        this.categorys=data;
      },error=>{
        console.log(error);
      }
    )
  }
  toProduct(temp:number){
    this.categoryId=temp;
    this.router.navigate(['product',this.categoryId]);
  }

}
