import { Component, OnInit } from '@angular/core';
import { Thread } from 'src/app/models/Forum';

import { AuthService } from 'src/app/services/auth/auth.service';
import { ForumService } from 'src/app/services/forum/forum.service';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {

  filterChain:string=''
  page=0
  order='id'
  isLogged=false
  threads:Thread[]=[]
  isFirst?:boolean;
  isLast?:boolean;
  totalPages:number=0;

  constructor(private forumService:ForumService,private authService:AuthService){

  }

  ngOnInit(): void {
    this.loadThreads();
    this.authService.currentUserLog.subscribe(data=> this.isLogged = data)
    
    
  }
  
  loadThreads(){
    this.forumService.getThreads(this.page,this.order,this.filterChain).subscribe(data=>{
      this.threads=data.content
      this.isFirst=data.first,
      this.isLast=data.last,
      this.totalPages=data.totalPages
    })
  }

  changePage(page:number){
    this.page=page;
    this.loadThreads();
  }

  filterPage(){
    this.page=0
    this.loadThreads();
  }

}
