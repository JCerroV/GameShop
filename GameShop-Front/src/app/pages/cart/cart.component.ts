import { Component, OnInit } from '@angular/core';
import { ShoppingCart, User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CartService } from 'src/app/services/cart/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  user?:User
  
  total:number=0.0;

  constructor(private cartService:CartService, private authService:AuthService){}


  ngOnInit(): void {
    this.authService.getCurrentUserData().subscribe(data=>{this.user=data,this.getTotal()}
    
    )
   
  }

  removeFromCart(id:number){
    this.cartService.removeFromCart(id).subscribe(
      ()=>this.loadCart()
    );
  }

  loadCart(){
    this.cartService.getCartFromUser().subscribe(
      data=>
      {this.user!.shoppingCart = data,
        this.authService.currentUser.next(this.user!)
        this.getTotal();
    })
    
  }
  getTotal(){
    this.total=0
    if(this.user?.shoppingCart){
      for(let item of this.user.shoppingCart){
        this.total=this.total+item.game.price!
      }
    }
    
  }


}
