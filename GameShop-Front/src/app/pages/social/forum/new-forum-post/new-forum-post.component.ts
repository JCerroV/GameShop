import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { ForumService } from 'src/app/services/forum/forum.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-new-forum-post',
  templateUrl: './new-forum-post.component.html',
  styleUrls: ['./new-forum-post.component.css']
})
export class NewForumPostComponent implements OnInit {

  threadForm:FormGroup;


  constructor(private forumService:ForumService,private fb:FormBuilder,private route:Router){
    this.threadForm=this.fb.group({
    title:['',[Validators.required,Validators.minLength(5),Validators.maxLength(50)]],
    content:['',[Validators.required,Validators.minLength(4),Validators.maxLength(1200)]]
    })

  }

  ngOnInit(): void {
  }

  createThread(){
    if(this.threadForm.valid){
      Swal.fire({
        title: "¿Deseas crear este hilo?",
        text: "No podrás modificarlo una vez creado",
        icon: "info",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, deseo crearlo"
      }).then((result) => {
        if (result.isConfirmed) {
          this.forumService.createThread(this.threadForm.get('title')?.value,this.threadForm.get('content')?.value).subscribe(data=>{
            console.log(data)
            this.route.navigate(['/forum', data]);
            
            Swal.fire({
              title: "Creado correctamente!",
              text: "Tu hilo ha sido creado.",
              icon: "success"
            });
          },
          error=>Swal.fire('Ha ocurrido un error')
          )
        }
      });
      
    }else{
      this.threadForm.markAllAsTouched()
    }
    
    

  }

}
