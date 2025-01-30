import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Thread, ThreadPost } from 'src/app/models/Forum';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ForumService } from 'src/app/services/forum/forum.service';

@Component({
  selector: 'app-forum-post',
  templateUrl: './forum-post.component.html',
  styleUrls: ['./forum-post.component.css']
})
export class ForumPostComponent implements OnInit{
  editTitle=false
  isLogged=false;
  inputPost:string=''
  currentUser?:User

  thread?:Thread
  posts:ThreadPost[]=[]
  editPost?:ThreadPost;

  page=0;
  isFirst?:boolean;
  isLast?:boolean;
  totalPages:number=0;

  constructor(private authService:AuthService,private forumService:ForumService,private router:ActivatedRoute,private route:Router ){

  }

  ngOnInit(): void {
    this.authService.currentUserLog.subscribe(data=>{this.isLogged=data
      if(this.isLogged){
        this.authService.getCurrentUserData().subscribe(data=>this.currentUser=data)
      }
    })
    this.forumService.getThread(this.router.snapshot.paramMap.get('id')! ).subscribe(data=>this.thread=data,
    
    
      
    )
    this.loadPosts();
  }

  loadPosts(){
    this.forumService.getPosts(this.router.snapshot.paramMap.get('id')! ,this.page ).subscribe(data=>{
      this.posts=data.content,
      console.log(this.posts)
      this.isFirst=data.first,
      this.isLast=data.last,
      this.totalPages=data.totalPages
    })

  }

  updateThread(status:boolean){
    if(status){
      this.thread!.status = !this.thread?.status
    }
    
    this.forumService.updateThread(this.thread!).subscribe(data=>this.thread=data)
    this.editTitle=false
  }

  deleteThread(){
    this.forumService.deleteThread(this.thread?.id!).subscribe(()=>
    this.route.navigateByUrl('/forum'))

  }

  publishPost(){
    console.log(this.inputPost);
    if(this.inputPost.length >=4 && this.inputPost.length < 1000 ){
      this.forumService.createPost(this.thread?.id!, this.inputPost).subscribe(()=>{this.loadPosts(),
        this.inputPost=''
      })
    }
    
  }

  updatePost(id:number,content:string){
    if(content.length>=4 && content.length<1000){
      console.log(content)
        this.forumService.updatePost(id,content).subscribe(()=>{this.loadPosts()
        })
    }
    

  }
  deletePost(id:number){
    this.forumService.deletePost(id).subscribe(()=>this.loadPosts())

  }

  changePage(page:number){
    this.page=page
    this.loadPosts();
    window.scrollTo({ top: 0, behavior: 'smooth' });

  }
}
