import { Component, OnInit } from '@angular/core';
import { Order } from '../entity/order';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'app-admin-ordermanagement',
  templateUrl: './admin-ordermanagement.component.html',
  styleUrls: ['./admin-ordermanagement.component.css'],
})
export class AdminOrdermanagementComponent implements OnInit {
  public page = 1;
  public pageSize = 5;
  temp: any = [];
  orders: Array<Order>;
  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getAllOrders().subscribe(
      (res) => {
        console.log(res);
        this.temp = res;
        this.orders = this.temp;
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
