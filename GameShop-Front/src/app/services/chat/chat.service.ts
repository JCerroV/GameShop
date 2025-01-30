import { Injectable } from '@angular/core';
import baseURL from '../helper';
import { Stomp } from '@stomp/stompjs';

import * as SockJS from 'sockjs-client';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { ChatMessage } from 'src/app/models/chat-message';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private stompClient:any;
  private messageSubject:BehaviorSubject<ChatMessage[]> = new BehaviorSubject<ChatMessage[]>([]);

  constructor(private http:HttpClient) { 
    this.connect()
  }

  connect(){
    const socket = new SockJS('//localhost:8080/chat-socket');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      console.log('Conectado');
    }, (error: any) => {
      console.error('Error de conexión', error);
    });
  }
   
  joinRoom(roomId:string){
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.subscribe(`/topic/${roomId}`, (mensaje: any) => {
        const contenidoMensaje = JSON.parse(mensaje.body);
        this.messageSubject.next(contenidoMensaje);
      });
    } else {
      console.error('Cliente STOMP no conectado');
      this.joinRoom(roomId)
    }
  }

  sendMessage(roomId:string,message:ChatMessage){
    this.stompClient.send(`/app/chat/${roomId}`,{} ,JSON.stringify(message))
  }

  getMessageSubject():Observable<ChatMessage[]>{
    return this.messageSubject.asObservable();
  }

  readmessages(id:number):Observable<any>{
    return this.http.put<any>(`${baseURL}/read-messages`,id)
  }
}
