import { Component, OnInit } from '@angular/core';
import { fakeAsync } from '@angular/core/testing';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  relation=''
  myProfile:boolean=false
  edit:boolean=false;
  privacy:string=''
  activeTab: string = 'profile';
  editDetailsForm!:FormGroup
  changePasswordForm!: FormGroup;
  
  currentUser?:User
  selectedFile?: File;

  user?:User;

  constructor( private authService:AuthService ,private fb:FormBuilder,private router:ActivatedRoute,private userService:UserService){
      this.changePasswordForm=fb.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required,Validators.maxLength(20),Validators.minLength(5)]],
      confirmPassword: ['', [Validators.required]]
     },
     { validators: this.passwordConfirm });
    
     
  }

  ngOnInit(): void {
    this.router.params.subscribe(param =>{
      if(param['username']){
        this.myProfile=false
        this.userService.getRelationStatus(param['username']).subscribe(data=>{this.relation=data
          console.log(data)
          console.log(this.relation)})
        this.userService.getUser(param['username']).subscribe(data=>{this.user=data,
          
          this.authService.getCurrentUserData().subscribe(data=>{this.currentUser=data
          })
        })
      }
      else{
        this.myProfile=true
        this.loadProfile()
      }
    })
  }

  updateProfile(){
    if(this.editDetailsForm.valid){
      console.log(this.user)
      this.user!.profile.name=this.editDetailsForm.get('name')?.value;
      this.user!.profile.surnames=this.editDetailsForm.get('surnames')?.value;
      this.user!.profile.location=this.editDetailsForm.get('location')?.value;
      this.user!.profile.phone=this.editDetailsForm.get('phone')?.value;
      this.user!.profile.biografy=this.editDetailsForm.get('biografy')?.value;
      console.log(this.user)
      this.authService.updateProfile(this.user).subscribe(
        ()=>{this.loadProfile(),this.edit=false}
      )
    }
    else{
      this.editDetailsForm.markAllAsTouched;
      console.error("Algo no va")
    }
  }

  passwordConfirm(form:FormGroup) {
    const password = form.get('newPassword')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    if (password!==confirmPassword) {
      form.get('confirmPassword')?.setErrors({passwordError:true});
    } else {
      form.get('confirmPassword')?.setErrors(null);
    }
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  
  }

  changeUserImage(change:boolean){
    if(!change){
      this.selectedFile=undefined
    }
    const formData= new FormData();
    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }
    this.userService.updateUserImage(formData).subscribe(()=>this.loadProfile())
  }

  changePassword(){
    if(this.changePasswordForm.valid){
      if(this.changePasswordForm.get('currentPassword')?.value!=this.changePasswordForm.get('newPassword')?.value){
        this.authService.changePassword(this.user?.username!,this.changePasswordForm.get('currentPassword')?.value,this.changePasswordForm.get('newPassword')?.value).subscribe(
          data=>{
            if(data){
            Swal.fire("Contraseña actualizada")}
            else{
              Swal.fire("¡La contraseña actual no es correcta!")
            }
          });
      }
      else{
        Swal.fire('Las contraseñas coinciden!!')
      }
      
        
      
    }
    this.loadProfile()
  }

  changePrivacy(event: Event){
    const selectedValue = (event.target as HTMLSelectElement).value;
    this.authService.changePrivacy(this.user?.username!,selectedValue).subscribe(
      ()=>console.log('Cambiado correctamente')
    )
    this.loadProfile()
  }

  loadProfile(){
    this.authService.getCurrentUser().subscribe(
      data=>{this.user=data,
      this.initForm()}
    )
  }

  initForm(){
    this.editDetailsForm=this.fb.group({
      name: [this.user?.profile?.name,Validators.required],
      surnames: [this.user?.profile?.surnames,Validators.required],
      location: [this.user?.profile?.location],
      phone: [this.user?.profile?.phone],
      biografy: [this.user?.profile?.biografy]
    })
  }

  removeFriend() {
    this.userService.deleteRelation(this.user?.username!).subscribe()
    this.relation='NO'
  }
  
  addFriend() {
    this.userService.addFriend(this.user?.username!).subscribe()
    this.relation="PENDING2"
  }

  acceptFriend(){
    this.userService.acceptFriend(this.user?.username!).subscribe(()=>this.relation='ACCEPT');
  }

}
