import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/services/auth/LoginRequest';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm!:FormGroup;
  errorLog:boolean=false;

    constructor( private formBuilder:FormBuilder,private router:Router, private authService:AuthService ){
      this.loginForm =this.formBuilder.group({
        username:['',[Validators.required]],
        password:['',Validators.required],
        keepSession:['']})
    }

  ngOnInit(): void {
    
  }

  

  get Username(){
    return this.loginForm.controls
  }

  get Password(){
    return this.loginForm.controls
  }

  login(){
    if(this.loginForm.valid){
      this.authService.login(this.loginForm.value as LoginRequest).subscribe({
        next: (userData) => {
        },
        error: (errorData) => {
          this.errorLog=true;
        },
        complete: () => {
          console.info("Login completo");
          this.router.navigateByUrl('')
        }
      })
    }else{
      this.loginForm.markAllAsTouched();
    }
  }
}
