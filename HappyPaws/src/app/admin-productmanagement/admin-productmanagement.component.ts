import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../entity/product';
import { ProductDto } from '../entity/product-dto';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-admin-productmanagement',
  templateUrl: './admin-productmanagement.component.html',
  styleUrls: ['./admin-productmanagement.component.css']
})
export class AdminProductmanagementComponent implements OnInit {
  public page=1;
  public pageSize=3;
  pid:number;
  public length:number;
  products:Array<Product>;
  isShown:boolean=false;
  product=new Product();
  product1=new ProductDto();
  pro:Product;
  constructor(private productService:ProductService,private router:Router) { }

  ngOnInit(): void {
    this.productService.getProductList().subscribe(
      data=>{
        this.products=data;
        this.length=this.products.length;
        console.log(this.length)
      },
      error=>console.log(error)
    )
  }

  reloadData(){
    setTimeout(function(){
      location.reload();},800);
  }

  listProducts(){
    this.productService.getProductList().subscribe(
      data=>{
        Object.assign(this.products,data);
      },
      error=>console.log(error)
    );
   }

   show(pid:number){
    this.pid=pid;
    this.isShown=true;
    this.productService.getProductById(this.pid).subscribe(
      data=>{
       Object.assign(this.product,data);
        console.log(this.product);
       
      },
      error=>
        console.log(error)
      );
     
   }
   back(){
    this.isShown=false;
  }

  newProduct(){
    this.productService.addnewProduct(this.product1).subscribe(
      data=>{
        console.log("Done");
       
        this.product1=new ProductDto();
        this.reloadData();
        
      },
      error=>{
        console.log("Not Done")
        
      }
    )
  }

  editProductLocal(){
    
    this.productService.editProduct(this.pid,this.product).subscribe(
      data=>{
        console.log(data);
        this.product=new Product();
       
        this.reloadData();
      },
      error => {
        console.log(error)
       
      }
    );
  }
  deleteProduct(){
    this.productService.deleteProductById(this.pid).subscribe(
      data=>{
        console.log('deleted');
       
        this.reloadData();
      },
      error=> {console.log(error);
       
      }
    )
  }

}
