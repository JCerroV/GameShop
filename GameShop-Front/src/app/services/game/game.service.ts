import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Category } from 'src/app/models/category';
import baseURL from '../helper';
import { Game } from 'src/app/models/game';
import { Params } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http:HttpClient) { }

  getAllCategories():Observable<Category[]> {
    return this.http.get<Category[]>(`${baseURL}/games/categories`);
  }

  addGame(formData: FormData):Observable<any>{
    return this.http.post<any>(`${baseURL}/games/new`,formData).pipe(
      catchError(this.handleError)
    );

    
  }
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Error desconocido';
    if (error.error instanceof ErrorEvent) {
      // Error del cliente
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // El servidor devolvió un código de error
      errorMessage = `Código: ${error.status}, Mensaje: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }

  getGame(id:number):Observable<Game>{
    return this.http.get<Game>(`${baseURL}/admin/games/${id}`)
  }

  verifyGame(title:string):Observable<boolean>{
    return this.http.post<boolean>(`${baseURL}/admin/games/new/verify`,title)
  }


  //Para usuarios normales
  games1(page:number,size:number,order:string,asc:boolean,filterChain:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/games?`+`page=${page}&size=${size}&order=${order}&asc=${asc}&filter=${filterChain}`);
  }

  games(page:number,size:number,order:string,asc:boolean,filterChain:string):Observable<any>{
    return this.http.get<any>(`${baseURL}/admin/games?`+`page=${page}&size=${size}&order=${order}&asc=${asc}&filter=${filterChain}`);
  }

  getGamesByCategories(params:HttpParams):Observable<any>{
    return this.http.get<any>(`${baseURL}/games/categoryfilter`, { params })
  }

  updateGame(id:number,game:Game){
    return this.http.put(`${baseURL}/admin/games/update/${id}`,game);
  }

  deleteGame(id:number){
    return this.http.delete(`${baseURL}/admin/games/delete/${id}`);
  }

  ////////////////COMMENTS///////////////

  addNewComment(id:number,text:string):Observable<any>{
    return this.http.post<any>(`${baseURL}/games/${id}/comments/new`,text)
  }

  getCommentFromGame(id:number):Observable<any>{
    return this.http.get<any>(`${baseURL}/games/${id}/comments`)
  }

  updateComment(idComment:number,text:string):Observable<any>{
    return this.http.put<any>(`${baseURL}/games/comments/${idComment}/update`,text)
  }

  deleteComment(idComment:number):Observable<any>{
    return this.http.delete<any>(`${baseURL}/games/comments/${idComment}/delete`)
  }

  /////////////Valuate///////////////////////////

  getMyRate(id:number):Observable<any>{
    return this.http.get<any>(`${baseURL}/games/${id}/myRate`);
  }

  addMyRate(id:number,rate:number):Observable<any>{
    return this.http.post<any>(`${baseURL}/games/${id}/rate/new`,rate);
  }

  updateMyRate(id:number,rate:number):Observable<any>{
    return this.http.put<any>(`${baseURL}/games/rates/${id}/update`,rate);
  }

  deleteMyRate(id:number):Observable<any>{
    return this.http.delete<any>(`${baseURL}/games/rates/${id}/delete`);
  }



}
