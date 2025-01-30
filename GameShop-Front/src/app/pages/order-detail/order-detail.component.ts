import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs';
import { Game } from 'src/app/models/game';
import { ShoppingCart, User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CartService } from 'src/app/services/cart/cart.service';
import { GameService } from 'src/app/services/game/game.service';
import { OrderService } from 'src/app/services/order/order.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit{
  user?:User
  games:Game[]=[]
  total:number=0.0
  shoppingCart?:ShoppingCart[]

  constructor(private activeRoute:ActivatedRoute,private authService:AuthService,private cartService:CartService,private orderService:OrderService,private gameService:GameService,private route:Router){}

  ngOnInit(): void {
    this.authService.getCurrentUserData().subscribe(data=>{
      this.user=data,
      this.activeRoute.params.pipe(take(1)).subscribe(params => {
        if (params['id']) {
          this.gameService.getGame(params['id']).subscribe(
            data=>{this.games.splice(0,1,data),
            this.getTotal()
            });
        } else {
          this.cartService.getCartFromUser().subscribe(data=>{this.shoppingCart=data,
            this.games=this.shoppingCart?.map(cart=>cart.game)!
            this.getTotal()
          });
        }
      });
    })
  }
  getTotal(){
    this.total=0
    if(this.games){
      for(let item of this.games){
        this.total=this.total+item.price!
      }
    }
    
  }

  buyNow(){
    Swal.fire({
      title: "¿Deseas realizar esta compra?",
      text: "",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Si, realizar compra"
    }).then((result) => {
      if (result.isConfirmed) {
        if((this.user?.balance!-this.total)>=0.00){
          this.orderService.buyNow(this.games).subscribe(
            () => {this.route.navigateByUrl(`/`)
              Swal.fire({
              title: "Compra realizada!",
              text: "Muchas gracias por su compra",
              icon: "success"
            })},
            error=>Swal.fire('Ha ocurrido un error',error))
        }else {Swal.fire('No tienes suficiente efectivo')}
      }
    });
    
  }


}
