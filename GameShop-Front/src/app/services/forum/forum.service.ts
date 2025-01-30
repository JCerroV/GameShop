import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import baseURL from '../helper';
import { Thread } from 'src/app/models/Forum';

@Injectable({
  providedIn: 'root'
})
export class ForumService {

  constructor(private http:HttpClient) { }

  getThreads(page:number,order:string,filterChain:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/forum?`+`page=${page}&order=${order}&filter=${filterChain}`)
  }
  //REvisar
  getThread(id:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/forum/${id}`)
  }

  createThread(title:string, content:string):Observable<any>{
    return this.http.post<any>(`${baseURL}/forum/new`,{title,content})
  }

  updateThread(thread:Thread):Observable<any>{
    return this.http.put<any>(`${baseURL}/forum/${thread.id}/update`,thread)
  }

  deleteThread(id:number):Observable<any>{
    return this.http.delete<any>(`${baseURL}/forum/${id}/delete`)
  }

  getPosts(id:string,page:number):Observable<any>{
    return this.http.get<any>(`${baseURL}/forum/${id}/posts?`+`page=${page}`)
  }

  createPost(id:number,content:string):Observable<any>{
    return this.http.post<any>(`${baseURL}/forum/${id}/newPost`,content)
  }

  updatePost(postId:number,content:string):Observable<any>{
    return this.http.put<any>(`${baseURL}/forum/post/${postId}/update`,content)
  }

  deletePost(postId:number):Observable<any>{
    return this.http.delete<any>(`${baseURL}/forum/post/${postId}/delete`)
  }
}
