import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user/user.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit{

  isFirst?:boolean;
  isLast?:boolean;
  totalpages:number=0;
  page:number=0
  order:string='id'
  asc:boolean=true
  size:number=5
  filterChain:string=""
  users:User[]=[]
  
  constructor(private userService:UserService){
  }
  ngOnInit(): void {
    this.loadUsers();
  }
  
  rewind():void{
    if(!this.isFirst){
      this.page--;
      this.loadUsers();
    }
  }
  next():void{
    if(!this.isLast){
      this.page++;
      this.loadUsers();
    }
  }
  loadUsers() {
    this.userService.getUsers(this.page,this.size,this.order,this.asc,this.filterChain).subscribe(
      data=>{console.log(data),
        this.users=data.content,    
        this.isFirst=data.first,
        this.isLast=data.last,
        this.totalpages=data.totalPages
      },
      error=>{ console.error("Error al recibir los datos de los juegos:",error)});
  }

  ordenar():void{
    this.asc=!this.asc;
    this.loadUsers();
  }

  setPage(page:number):void{
    this.page=page-1;
    this.loadUsers();
  }

  sentOrder(order:string): void{
    this.order=order;
    this.loadUsers();
  }

  cleanFilter():void{
    this.filterChain='';
    this.filterUsers();
  }

  filterUsers() {
    this.page=0;
   this.loadUsers(); 
  }

  updateStatus(id:number){
    swal.fire({
      
      title: "Quieres bloquear/desbloquear a este usuario?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Confirmar!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.updateStatus(id).subscribe(()=>{
          swal.fire({
            title: "Usuario Bloqueado!",
            text: "El usuario se ha bloqueado/desbloqueado correctamente",
            icon: "success"
          });
          this.loadUsers()})
      }
    });
  }

  updateRole(id:number,role:string){
    swal.fire({
      title: "Quieres cambiar los privilegios de este usuario?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Confirmar!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.updateRol(id,role).subscribe(()=>{
          swal.fire({
            title: "Se han cambiado los privilegios!",
            text: "Privilegios actualizados correctamente",
            icon: "success"
          });
          this.loadUsers()})

      }
  });
}
}
