import { Injectable } from '@angular/core';
import baseURL from '../helper';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http:HttpClient) { 

  }

  public getCartFromUser():Observable<any>{
    return this.http.get<any>(`${baseURL}/cart`)
  }

  public addToCart(gameId:number):Observable<any>{
    return this.http.post<any>(`${baseURL}/cart/add`,gameId)
  }

  public removeFromCart(id:number):Observable<any>{
    return this.http.delete<any>(`${baseURL}/cart/remove/${id}`)
  }
}
