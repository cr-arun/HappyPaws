import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
declare let alertify:any;
import pdfMake from "pdfmake/build/pdfmake";  
import pdfFonts from "pdfmake/build/vfs_fonts";  
import { Order } from '../entity/order';
import { OrderService } from '../service/order.service';
import { UserauthService } from '../service/userauth.service';
pdfMake.vfs = pdfFonts.pdfMake.vfs;
@Component({
  selector: 'app-vieworder',
  templateUrl: './vieworder.component.html',
  styleUrls: ['./vieworder.component.css']
})
export class VieworderComponent implements OnInit {

  id:number;
  temp:any;
  order:Order;
  grandtotal:number;
  user:any;
  constructor(private route:ActivatedRoute,private orderService:OrderService,private router:Router,private userAuth:UserauthService) { }

  ngOnInit(): void {
    this.user=this.userAuth.getUserData();
    this.id=this.route.snapshot.params['id']
    this.getOrder();
  }
  getOrder()
  {
    this.orderService.getOneOrder(this.id).subscribe(
      data=>{
      
        this.temp=data;
        this.order=this.temp;
        this.grandtotal=this.order.totalPrice;
        
      },
      error=>{
        console.log(error)
      }
    )
  }
  deleteOrder(){
    this.orderService.cancelOrder(this.id).subscribe(
      data=>{
        alertify.error('Order Cancelled')
       
        this.router.navigate(['myorders'])
        
      }
    )
  }
  generatePDF() { 
    let docDefinition = {  
    content: [  
      {  
        text: 'Happy Paws',  
        fontSize: 16,  
        alignment: 'center',  
        color: '#047886'  
      },  
      {  
        text: 'INVOICE',  
        fontSize: 20,  
        bold: true,  
        alignment: 'center',  
        decoration: 'underline',  
        color: 'skyblue' 
         
      } ,
      [  
       
        {  
            text: 'Customer Details',  
            bold: true,  
            decoration: 'underline',  
            fontSize: 14,  
             margin: [0, 15, 0, 15]  
        }  
    ],  [  
     
      {  
          columns: [  
              [  
                  {  
                      text: `Customer name: ${this.user.username}`,  
                      bold: true  
                  },  
                  { text: `Address: ${this.order.address}` },
        
                  { text:`Email: ${this.user.email}` },  
                  { text: `Contact: ${this.user.phoneNumber}`}  
              ],  
              [  
                  {  
                      text: `Date: ${this.order.createdDate}`,  
                      alignment: 'right'  
                  },  
                  {  
                      text: `Bill No : ${this.order.id}`,  
                      alignment: 'right'  
                  }  
              ]  
          ]  
      },  
  ],
  [  
    
    {  
        text: 'Order Details',  
        bold: true,  
        decoration: 'underline',  
        fontSize: 14,  
         margin: [0, 15, 0, 15]  
    },  
    {  
      table: {  
          headerRows: 1,  
          widths: ['*', 'auto', 'auto', 'auto'],  
          body: [  
              ['Product', 'Price', 'Quantity', 'Amount'],  
              ...this.order.orderItems.map(p=>([p.product.productName,p.product.price,p.quantity,p.quantity*p.product.price])),
              [{ text: 'Total Amount', colSpan: 3 }, {}, {},this.order.totalPrice]   
              
          ]  
      }  
  }  
], [  
 
  {  
      ul: [  
        'Order can be return in max 10 days.', 
        'This is system generated invoice.',  
      ], 
      margin: [0, 15, 0, 15]   
  }  
]   
    
    ]
  
}
     
   
   
    pdfMake.createPdf(docDefinition).open();  
  }  

}
