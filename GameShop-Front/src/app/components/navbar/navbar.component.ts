import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { UnreadMessages } from 'src/app/models/chat-message';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CartService } from 'src/app/services/cart/cart.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  unreadMessages:UnreadMessages[]=[];
  user?:User;
  userLogIn:boolean=false;
  total:number=0;
  petitions:User[]=[]

  constructor(private authService:AuthService, private cartService:CartService,private userService:UserService){
  }
  
  ngOnInit(): void {
    this.authService.currentUserLog.subscribe({
      next:(userLogIn) => {
        this.userLogIn=userLogIn;
        if(this.userLogIn){
          this.authService.getCurrentUserData().subscribe(data=>{this.user=data,this.getTotal()
          this.userService.getReceivedRequest().subscribe(data=>this.petitions=data),
          this.userService.updateUnreadMessages();
          this.userService.getCurrentUnreadMessages().subscribe(data=>{this.unreadMessages=data,
            console.log(this.unreadMessages)}
          )
          }
        )}
        
      }
    });
  }

  removeFromCart(id:number){
    this.cartService.removeFromCart(id).subscribe(
      ()=>this.loadCart()
    );
  }

  public logout(){
      this.authService.logout();
      window.location.reload;
  }

  loadCart(){
    this.cartService.getCartFromUser().subscribe(data=>{this.user!.shoppingCart = data,
      this.authService.currentUser.next(this.user!),
      this.getTotal()
    })
  }

  getTotal(){
    this.total=0
    if(this.user?.shoppingCart){
      for(let item of this.user?.shoppingCart!){
        this.total=this.total+item.game.price!
      }
    }
  }
}
