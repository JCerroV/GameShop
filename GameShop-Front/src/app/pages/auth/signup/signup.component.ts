import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Observable, catchError, debounceTime, distinctUntilChanged, map, of } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  registerForm! : FormGroup;
  existe:boolean=true;
  userExist:boolean=false;
  mailExist=false;

  constructor(private fb:FormBuilder,private authService:AuthService,private route:Router){
    this.registerForm=this.fb.group({
      name: ['',[Validators.required, Validators.maxLength(20)]],
      lastname: ['',[Validators.required,Validators.maxLength(25)]],
      username: ['',[Validators.required,Validators.minLength(5),Validators.maxLength(15)]],
      email: ['',[Validators.required,Validators.email]],
      phone: ['',[Validators.pattern("^\\+[0-9]{1,3}[0-9]{6,14}$")]],
      country: [''],
      password: ['',[Validators.required,Validators.minLength(6),Validators.maxLength(16)]],
      password2: ['',[Validators.required]],
      policy: ['',Validators.requiredTrue]
    },
    { validators: this.passwordConfirm });
  }

  ngOnInit(): void {
    
  }

  register():void{
    if(this.registerForm.valid && !this.userExist && !this.mailExist ){
      this.authService.register(this.registerForm.value).subscribe(
        ()=>{
          console.log('Registrado correctamente');
          this.route.navigateByUrl('#');
        },
        error=>console.error('Error al guardar el usuario')
      )
        
    }    
  }
  
  verifyUser(){
    const username=this.registerForm.get('username')?.value;
    debounceTime(3000)
    this.authService.checkUser(username).subscribe(data => {
      this.userExist=data;
  });
  }

  verifyEmail(){
    const email=this.registerForm.get('email')?.value;
    this.authService.checkEmail(email).subscribe(data=>this.mailExist=data);
  }

  passwordConfirm(form:FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('password2')?.value;
    if (password!==confirmPassword) {
      form.get('password2')?.setErrors({passwordError:true});
    } else {
      form.get('password2')?.setErrors(null);
    }
  }
}