import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatMessage } from 'src/app/models/chat-message';
import { Relation, User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ChatService } from 'src/app/services/chat/chat.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit{
  @ViewChild('messageContainer') private messageContainer!: ElementRef;
  relation?:Relation;
  messageInput='';
  messages:ChatMessage[]=[];

  constructor(private chatService:ChatService,private route:ActivatedRoute,private userService:UserService){


  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const username = params['username'];
      this.loadChat(username);
    });
    
    
    
  }
  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
    } catch (err) {
      console.error(err);
    }
  }

  loadChat(username:string){
    this.userService.getFriend(username).subscribe(data=>{this.relation=data
      this.chatService.joinRoom(this.relation!.id.toString())
      this.listenerMessages();
      this.messages=data.messages
      this.scrollToBottom();
    })

  }

  sendMessage(event:Event){
    const chatMessage= {
      text: this.messageInput,
      username: this.relation?.user.username,
      

    }as ChatMessage
    if(this.messageInput.trim().length>0){
      this.chatService.sendMessage(this.relation!.id.toString(), chatMessage);
      this.messageInput=''
      this.autoExpand(event)
    }
    
    
    
  }

  listenerMessages(){
    this.chatService.getMessageSubject().subscribe(data=>{this.messages=data
      this.chatService.readmessages(this.relation?.id!).subscribe(()=>{
        this.userService.updateUnreadMessages();
      });
      
      
    })
  }
  autoExpand(event: Event) {
    const textarea = event.target as HTMLTextAreaElement;
    textarea.style.height = 'auto';
    textarea.style.height = `${textarea.scrollHeight}px`;
  }
}
