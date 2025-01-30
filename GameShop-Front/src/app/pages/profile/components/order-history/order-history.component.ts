import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Purchase } from 'src/app/models/user';
import { OrderService } from 'src/app/services/order/order.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  purchase:Purchase[]=[]
  total:number=0.0;
  isAdmin=false

  constructor(private orderService:OrderService, private router: Router,private route: ActivatedRoute ){}


  ngOnInit(): void {
    if(this.router.url.startsWith('/admin/users/')){
      this.isAdmin=true
      this.loadOrders();
      
      
    }else{
      this.orderService.getOrdersFromUser().subscribe(data=>{this.purchase=data
    })
    }
    
   
  }

  loadOrders(){
    this.route.paramMap.subscribe(params => {
      this.orderService.getOrdersFromUserAdmin(params.get('username')!).subscribe(data=>{this.purchase=data
      })
    })
  }

  deleteOrder(id:number){
    this.orderService.deleteOrder(id).subscribe(()=>this.loadOrders())
  }
}
