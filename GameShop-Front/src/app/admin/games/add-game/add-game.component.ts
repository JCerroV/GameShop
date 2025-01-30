import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Category } from 'src/app/models/category';
import { Game } from 'src/app/models/game';
import swal from 'sweetalert2'
import { GameService } from 'src/app/services/game/game.service';
import { Router } from '@angular/router';
import { debounceTime } from 'rxjs';

@Component({
  selector: 'app-add-game',
  templateUrl: './add-game.component.html',
  styleUrls: ['./add-game.component.css']
})
export class AddGameComponent implements OnInit {

  gameForm!:FormGroup;
  titleExist:boolean=false;
  categories?: Category[];
  selectedCategories:Category[];
  game:Game={};
  multiGame:boolean=false;
  selectedFile?: File;
  imageUrl: string | ArrayBuffer | null = null;

  constructor(private gameService:GameService, private fb:FormBuilder,private route:Router) {
    this.gameForm= this.fb.group({
      title:['',Validators.required],
      author:['',Validators.required],
      description:[''],
      price:['',Validators.required],
      photo:[''],
      selCategories:[''],
      delCategories:[''],

    });
    this.gameForm.get('title')?.valueChanges.pipe(
      debounceTime(600)
    ).subscribe(
      () => { 
        if(this.gameForm.get('title')?.value!=''){
        this.verifytitle();
      }
      
    });
    this.selectedCategories=[]
  }

  ngOnInit(): void {
    this.gameService.getAllCategories().subscribe(
      data => {
        this.categories = data;
      },
      error => {
        console.log('Error fetching categories:', error);
      }
    );
  }

  addCategory(){
    const selCategories = this.gameForm.get('selCategories')?.value;
    if (selCategories.length>0) {
      for (let category of selCategories) {
        if(!this.selectedCategories.includes(category)){
             this.selectedCategories.push(category);
        }
      }
    }  
  }

  deleteCategory(){
    if(this.gameForm.get('delCategories')?.value.length>0){
      console.log('Juego.');
      for (let category of this.gameForm.get('delCategories')?.value) {
        let i= this.selectedCategories.indexOf(category)
        this.selectedCategories.splice(i,1)       
      }
    }
  }

  resetCategories(){
    this.selectedCategories=[];
  }

  clean(){
    this.gameForm.reset();
    this.selectedCategories=[];
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    if(this.selectedFile){
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imageUrl = e.target.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
    
  }

  deleteImage() {
    this.imageUrl=null;
    this.gameForm.get('photo')?.setValue(null);
    }

  save(): void {
    if(this.gameForm.valid && !this.titleExist){
      swal.fire({
        title: "¿Deseas guardar este juego?",
        showCancelButton: true,
        confirmButtonText: "Guardar",
      }).then((result) => {
        if (result.isConfirmed) {
          const formData = new FormData();
          formData.append('title', this.gameForm.get('title')?.value);
          formData.append('author', this.gameForm.get('author')?.value);
          formData.append('description', this.gameForm.get('description')?.value);
          formData.append('price', this.gameForm.get('price')?.value.toString());
          if(this.selectedCategories.length>0 || this.selectedCategories){
          formData.append('categories', JSON.stringify(this.selectedCategories));
          }
          if (this.selectedFile) {
            formData.append('image', this.selectedFile);
          }
          this.gameService.addGame(formData).subscribe(
          () => {
            swal.fire("Guardado correctamente!", "", "success");
            if(!this.multiGame){
              this.route.navigateByUrl("/admin/games")
            }
            },
            error => {
              swal.fire("Ha ocurrido un error!", "", "error");
             console.error('Error al guardar el juego:', error);
            }
            );

        }});

      
    }else{
      this.gameForm.markAllAsTouched();
      swal.fire("Error al registrar, revisa los campos nuevamente");
      console.error("Algo salio mal")
    }
    
  }
  multiAdd(){
    this.multiGame=!this.multiGame
  }

  verifytitle(){
    this.gameService.verifyGame(this.gameForm.get('title')?.value).subscribe(
      data=> {this.titleExist=data},
      error=> console.error("Ha ocurrido un error")
    )
  }
}
