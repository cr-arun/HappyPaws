import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'app-myorders',
  templateUrl: './myorders.component.html',
  styleUrls: ['./myorders.component.css']
})
export class MyordersComponent implements OnInit {
  orders:any=[];
  public ordersItems:any=[];
  address:any;
  constructor(private orderService:OrderService,private router:Router) { }

  ngOnInit(): void {
   this.orderService.getOrderList()
   .subscribe(res=>{
     console.log(res)
     this.orders=res;
     

   },
   error=>{
     console.log(error)
   })
  
  }
  viewOrder(id:number){
    this.router.navigate(['vieworder',id])
  }
  reloadData(){
    setTimeout(function(){
      location.reload();},800);
  }
}
