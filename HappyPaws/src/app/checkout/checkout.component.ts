import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { stringify } from 'querystring';
import { Address } from '../entity/address';
import { Cart } from '../entity/cart';
import { PaymentDto } from '../entity/payment';
import { CartService } from '../service/cart.service';
import { OrderService } from '../service/order.service';
import { ProductService } from '../service/product.service';
import { UserauthService } from '../service/userauth.service';
import { WindowRefService } from '../service/window-ref.service';
declare let alertify: any;
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  temp: any;
  id: number;
  val: any;
  public pay: any;
  public address = new Address();
  public cart = new Cart();
  public grandTotal: number;
  public products: any = [];
  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private router: Router,
    private orderService: OrderService,
    private winRef: WindowRefService,
    private userAuth: UserauthService
  ) {}

  ngOnInit(): void {
    this.cartService.getProductsByUser().subscribe((res) => {
      console.log(res);
      this.temp = res;
      this.cart = this.temp;
      this.products = this.cart.items;
      console.log(this.products);
      this.grandTotal = this.cart.total;
      //this.grandTotal = this.cartService.getTotalPrice();
    });
    this.orderService.payment().subscribe((data) => {
      console.log(data);
      let temp = data;
      JSON.stringify(temp);
      console.log(temp);
      console.log(document.getElementById('paymentId'));
      //Object.assign(this.pay, temp);
      //Object.assign(this.pay, data);
      console.log(this.pay);
    });
  }
  public onSubmit() {
    this.payWithRazor(this.val);
  }

  payWithRazor(val) {
    const options: any = {
      key: 'rzp_test_vJKtyvgehORRYu',
      amount: this.grandTotal * 100, // amount should be in paise format to display Rs 1255 without decimal point
      currency: 'INR',
      name: 'Happy Paws', // company name or product name
      description: '', // product description
      image: './assets/logo.png', // company logo or product image
      order_id: val, // order_id created by you in backend
      modal: {
        // We should prevent closing of the form when esc key is pressed.
        escape: false,
      },
      notes: {
        // include notes if any
      },
      theme: {
        color: '#0c238a',
      },
    };
    options.handler = (response, error) => {
      options.response = response;
      console.log(response);

      console.log(options);
      if (response != null) {
        this.orderService.placeOrder(this.address).subscribe(
          (data) => {
            console.log(data);
            //alertify.success('Order Placed');
            this.router.navigate(['myorders']);
            this.userAuth.reloadData();
            alertify.success('Order Placed');
          },
          (error) => {
            console.log(error);
            alertify.error('Order not Placed');
          }
        );
      }
      // call your backend api to verify payment signature & capture transaction
    };
    options.modal.ondismiss = () => {
      // handle the case when user closes the form while transaction is in progress
      console.log('Transaction cancelled.');
    };
    const rzp = new this.winRef.nativeWindow.Razorpay(options);
    rzp.open();
  }
}
