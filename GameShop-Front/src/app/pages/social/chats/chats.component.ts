import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-chats',
  templateUrl: './chats.component.html',
  styleUrls: ['./chats.component.css']
})
export class ChatsComponent implements OnInit{
  friends:User[]=[];
  friendFilter=''

  constructor(private userService:UserService,private authService:AuthService){

  }
  ngOnInit(): void {
    this.loadFriends()
  }

  loadFriends(){
    this.userService.getFriends().subscribe(data=>this.friends=data)
      
  }

  deleteRelation(username:string){
    this.userService.deleteRelation(username).subscribe(()=>this.loadFriends())
  }

  searchFriend(){
    if(this.friendFilter.length>2){
      this.userService.getUsers1(this.friendFilter).subscribe(data=>this.friends=data
      )
    }
  }

}
