import { Component, OnInit } from '@angular/core';
import { Game } from 'src/app/models/game';
import { Purchase } from 'src/app/models/user';
import { OrderService } from 'src/app/services/order/order.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {
  games:Game[]=[]
  purchases:Purchase[]=[]

  constructor(private orderService:OrderService){

  }

  ngOnInit(): void {
    this.orderService.getOrdersFromUser().subscribe(data=>{this.purchases=data,
      this.purchases.forEach(purchase=>this.games.push(purchase.game))
    }
    )
  }

}
