import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseURL from '../helper';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { UnreadMessages } from 'src/app/models/chat-message';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUnreadMessages:BehaviorSubject<UnreadMessages[]>=new BehaviorSubject<UnreadMessages[]>([])

  constructor(private http:HttpClient) { 
  }

  getUsers(page:number,size:number,order:string,asc:boolean,filterChain:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/admin/users?`+`page=${page}&size=${size}&order=${order}&asc=${asc}&filter=${filterChain}`)
  }

  updateRol(id:number,role:string):Observable<any>{
    return this.http.put<any>(`${baseURL}/admin/users/${id}/changeRole`,role)
  }

  updateStatus(id:number):Observable<any>{
    return this.http.get<any>(`${baseURL}/admin/users/${id}/changeStatus`)
  }

  getUser(username:string):Observable<any>{

    return this.http.get<any>(`${baseURL}/users/${username}`)
  }

  updateUserImage(formData: FormData):Observable<any>{
    return this.http.put<any>(`${baseURL}/profile/updateImage`,formData)
  }


  getUsers1(filter:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/users?`+`filter=${filter}`)
  }


  ///////////////FRIENDS/////////////////

  addFriend(username:string):Observable<any>{
    return this.http.post<any>(`${baseURL}/friends/add`,username)
  }

  getFriends():Observable<any>{
    return this.http.get<any>(`${baseURL}/friends`)
  }

  getFriend(username:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/friend/${username}`)
  }

  getReceivedRequest():Observable<any>{
    return this.http.get<any>(`${baseURL}/friends/received-request`)
  }

  getSendRequest():Observable<any>{
    return this.http.get<any>(`${baseURL}/friends/send-request`)
  }

  acceptFriend(username:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/friends/${username}/accept`)
  }

  deleteRelation(username:string):Observable<any>{
    return this.http.delete<any>(`${baseURL}/friends/${username}/delete`)
  }

  getRelationStatus(username:string):Observable<string>{
    return this.http.get(`${baseURL}/friends/${username}/status`, { responseType: 'text' })
  }

  getUnreadMessages():Observable<UnreadMessages[]>{
    return this.http.get<UnreadMessages[]>(`${baseURL}/unread-messages`).pipe(
      tap(data=>{this.currentUnreadMessages.next(data),
        console.log(data)
      }))
  }

  updateUnreadMessages(): void {
    this.getUnreadMessages().subscribe();
  }

  getCurrentUnreadMessages():Observable<UnreadMessages[]>{
    return this.currentUnreadMessages.asObservable();
  }

}
