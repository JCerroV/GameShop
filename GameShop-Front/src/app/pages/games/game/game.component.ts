import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Game, GameComment, Rate } from 'src/app/models/game';
import { Purchase, ShoppingCart, User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CartService } from 'src/app/services/cart/cart.service';
import { GameService } from 'src/app/services/game/game.service';
import { OrderService } from 'src/app/services/order/order.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent {
  userLogIn?:boolean;
  isValue?:boolean=false;
  rate?:Rate;
  buy?:boolean;
  liked?:boolean;
  editComment:boolean=false;
  commentText:string=''
  commentNow:boolean=false;
  comments:GameComment[]=[]
  game:Game={};
  orders:Purchase[]=[]
  user?:User;
  highlightedStars = 0;

  constructor(private route: ActivatedRoute, private gameService:GameService,private cartService:CartService,
    private authService:AuthService, private orderService:OrderService  ) {
  }

  ngOnInit() {
    this.authService.currentUserLog.subscribe(data=>this.userLogIn=data)
    this.loadGame()
    if (this.userLogIn){
      if(this.user?.shoppingCart.some(item=>item.game.id=this.game.id)){
        this.liked=true;
      }
      else{this.liked=false}
     
      this.loadOrder();
    }
      
    
    
     
     
  }

  loadUser(){
    this.authService.getCurrentUser().subscribe(data=>{this.user=data
      this.getMyRate()
          if(this.rate){this.isValue=true}
          if (this.user?.shoppingCart.some(item => item.game.id==this.game.id)) {
            this.liked = true;
          }
          else{
            this.liked=false
          }
          if(this.orders.some(item=>item.game.id==this.game.id)){
            this.buy=true
            console.log(this.buy)
          }else{this.buy=false}

      }
    )}

  loadGame(){
    this.route.params.subscribe(
      id=>{this.gameService.getGame(id['id']).subscribe(
        data=>{
          this.game=data;
          console.log(data)
          console.log(this.game)
          this.loadComents();
          if(this.userLogIn){this.loadUser()}
          
      })}
    )
  }

  loadCart(){
    this.cartService.getCartFromUser().subscribe(data=>{
      this.user!.shoppingCart = data
      this.authService.currentUser.next(this.user!)
    })
  }

  loadOrder(){this.orderService.getOrdersFromUser().subscribe(data=>{
    this.orders=data
  })
}

  addToCart(game:number){
    if(!this.user?.shoppingCart.some(item=>item.game == game)){
      this.cartService.addToCart(game).subscribe(() => {
      this.loadCart(),
      this.liked=true
      });
    }
  }
      
  removeFromCart(id:number){
      const foundItem = this.user?.shoppingCart.find(item => item.game.id === id);
      if(foundItem){
        this.cartService.removeFromCart(foundItem.id).subscribe(()=>{
          this.loadCart(),
          this.liked=false}
      );
      }
  }

  showCommentButton(){
    if(this.commentText == ''){
      this.commentNow=false;
    }else{
       this.commentNow=true
    }
  }

  addNewComment(){
    if(this.commentText!=='' && this.commentText.length>=10){
      this.gameService.addNewComment(this.game.id!,this.commentText).subscribe(()=>{Swal.fire('Comentario añadido'),
      this.commentText=''
      this.commentNow=false;
      console.log('jaja si')
        this.loadComents();
      })
    }
  }
  loadComents() {
    this.gameService.getCommentFromGame(this.game.id!).subscribe(data=>this.comments=data)
  }

  deleteComment(id:number){
    this.gameService.deleteComment(id).subscribe(()=>this.loadComents());
  }

  updateComment(comment:GameComment){
    this.gameService.updateComment(comment.id,comment.text).subscribe(()=>{this.loadComents(),
      this.editComment=false
    }
    )
  }


  getMyRate(){
    this.gameService.getMyRate(this.game.id!).subscribe(data=>{this.rate=data
      console.log(data)
      if(this.rate?.id){this.isValue=true;}
      }
    );
  }

  addMyRate(rate:number){
    this.gameService.addMyRate(this.game.id!,rate).subscribe(()=>{this.isValue=true,
      this.getMyRate()
    })
  }
  deleteMyRate(){
    this.gameService.deleteMyRate(this.rate?.id!).subscribe(()=>this.isValue=false)

  } 
  updateMyRate(rate:number){
    this.gameService.updateMyRate(this.rate?.id!,rate).subscribe(()=>this.getMyRate())

  } 

  highlightStars( count: number) {
    this.highlightedStars = count;

  }

  resetStars(){
    this.highlightedStars = 0;
  }
}
 

