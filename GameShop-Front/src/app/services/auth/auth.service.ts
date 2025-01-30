import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, of, tap, throwError } from 'rxjs';
import { registerRequest } from 'src/app/models/registerRequest';
import baseURL from '../helper';
import { LoginRequest } from './LoginRequest';
import * as jwt_decode from 'jwt-decode';
import { ShoppingCart, User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user?:User

  currentUser:BehaviorSubject<User>=new BehaviorSubject<User>(this.user!);
  currentUserLog: BehaviorSubject<boolean>=new BehaviorSubject<boolean>(false);

  constructor(private http:HttpClient) { 

    this.currentUserLog=new  BehaviorSubject<boolean>(localStorage.getItem("token")!=null || sessionStorage.getItem('token')!=null);
    this.currentUserLog.subscribe(data=>{const login=data
      if(login){
      
        this.getCurrentUser().subscribe(data=>this.currentUser.next(data))
      }
    })
    
    
    

  }

//REGISTER
////////////////////////////////////////////////////////////////////////////////////////////////

  public register(request:registerRequest): Observable<registerRequest>{
    return this.http.post<any>(`${baseURL}/auth/register`,request).pipe(
      tap( (userData)=> {
        localStorage.setItem('token',userData.token);
        this.currentUserLog.next(true);
        this.currentUser.next(userData)
      }),
      map((userData)=>userData.token),
    );
  }

  public checkUser(username: string): Observable<boolean> {
    return this.http.get<boolean>(`${baseURL}/auth/check-username?username=${username}`);
    
  }

  public checkEmail(email:String):Observable<boolean>{
    return this.http.get<boolean>(`${baseURL}/auth/check-email?email=${email}`)
  }
//LOGIN
////////////////////////////////////////////////////////////////////////////////////////////////

public login(credentials:LoginRequest):Observable<any>{
  return this.http.post<any>(`${baseURL}/auth/login`,credentials).pipe(
    tap( (userData) => {
      if(credentials.keepSession){
        localStorage.setItem('token',userData.token);
      }else{
        sessionStorage.setItem('token',userData.token);
      }
      this.currentUserLog.next(true);
      
      this.getCurrentUser().subscribe(data=>this.currentUser.next(data))
      
    }),
    map((userData)=>userData.token),
    catchError(this.handlerError))
}

//LOGOUT
////////////////////////////////////////////////////////////////////////////////////////////////

  public logout(){
    localStorage.removeItem('token');
    sessionStorage.removeItem('token')
    this.currentUserLog.next(false);
  }

////////////////////////////////////////////////////////////////////////////////////////////////

   public getCurrentUser():Observable<any>{
     return this.http.get<any>(`${baseURL}/actual`);
   }

   getCurrentUserData():Observable<any>{
    return this.currentUser.asObservable();
  
   }

  private handlerError(error:HttpErrorResponse){
    if(error.status==0){
      console.error('Se ha producido un error', error.error);
    }
    else{
      console.error('Backend retorno el codigo de estado ',error.status,error.error);

    }
    return throwError(()=> new Error("Algo falló,intentalo nuevamente"))
  }

  //////////////////////////////MODIFICAR PERFIL DE USUARIO////////////////////////

  public updateProfile(user?:User):Observable<any>{
    return this.http.put<any>(`${baseURL}/profile/${user?.username}/updateData`,user)
  }

  public changePassword(username:string,currentPassword:string,newPassword:string):Observable<any>{
    return this.http.put<any>(`${baseURL}/profile/${username}/updatePassword`,{currentPassword,newPassword})
  }

  public changePrivacy(username:string,privacy:string):Observable<any>{
    return this.http.put<any>(`${baseURL}/profile/${username}/updatePrivacy`,privacy)
  }

  ////////CHECKPASSWORD/////////77
  public checkPassword(password:string):Observable<any>{
    return this.http.post<any>(`${baseURL}/auth/check-password`,password)
  }

  public addMoney(deposit:number):Observable<any>{
    return this.http.post<any>(`${baseURL}/deposit-money`,deposit)
  }

}
