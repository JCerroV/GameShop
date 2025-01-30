import { Component, OnInit } from '@angular/core';
import {  User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
  
  users:User[]=[];
  friends:User[]=[];
  sendRequests:User[]=[];
  receivedRequest:User[]=[]
  currentUser?:User;
  userBrowser='';

  constructor(private authService:AuthService,private userService:UserService){
    
  }

  ngOnInit(): void {
    if(this.authService.currentUserLog){
       this.authService.getCurrentUserData().subscribe(data=>this.currentUser=data)
    }
    this.loadFriends();
  }

  searchFriend(){
    if(this.userBrowser.length>2){
      this.userService.getUsers1(this.userBrowser).subscribe(data=>this.users=data
      )
    }
  }

  addFriend(username:string){
    this.userService.addFriend(username).subscribe(()=>this.loadFriends())
  }

  loadFriends(){
    this.userService.getFriends().subscribe(data=>{this.friends=data,
      this.userService.getReceivedRequest().subscribe(data=>{this.receivedRequest=data,
        this.userService.getSendRequest().subscribe(data=>this.sendRequests=data)
    })
      
      
    })
  }
  acceptPetition(username:string){
    this.userService.acceptFriend(username).subscribe(()=>this.loadFriends())
  }

  deleteRelation(username:string){
    this.userService.deleteRelation(username).subscribe(()=>this.loadFriends())
  }
}
