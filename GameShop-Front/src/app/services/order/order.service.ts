import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import baseURL from '../helper';
import { Game } from 'src/app/models/game';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http:HttpClient) { }

  buyNow(games:Game[]):Observable<any>{
    return this.http.post<any>(`${baseURL}/order/new`,games)
  }

  getOrdersFromUser():Observable<any>{
    return this.http.get<any>(`${baseURL}/order/history`)
  }

  getOrdersFromUserAdmin(username:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/admin/order/${username}/history`)
  }

  deleteOrder(id:number):Observable<any>{
    return this.http.delete<any>(`${baseURL}/admin/order/${id}/delete`)
  }
}
