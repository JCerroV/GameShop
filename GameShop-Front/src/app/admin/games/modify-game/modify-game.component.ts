import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Game } from 'src/app/models/game';
import { GameService } from 'src/app/services/game/game.service';
import swal from 'sweetalert2'

@Component({
  selector: 'app-modify-game',
  templateUrl: './modify-game.component.html',
  styleUrls: ['./modify-game.component.css']
})
export class ModifyGameComponent implements OnInit {
  actualGame:Game={};
  updatedGame:Game={};
  categories?: Category[];
  gameForm!:FormGroup;
  constructor(private route:Router,private gameService:GameService,private activatedRoute:ActivatedRoute,private fb:FormBuilder) {

    
  }

  initGameForm(){
    this.gameForm= this.fb.group({
      title:[this.actualGame.title,Validators.required],
      author:[this.actualGame.author,Validators.required],
      description:[this.actualGame.description],
      price:[this.actualGame.price,Validators.required],
      photo:[this.actualGame.image],
      selCategories:[''],
      delCategories:[''],

    })
  }
  ngOnInit(): void {
    this.loadGame();
    this.loadCategories();
    this.initGameForm();
    
  }

  onFileSelected(event: any): void {
  }

  deleteImage(){
    this.updatedGame.image!=null;
    
  }

  addCategory(){
    const selCategories = this.gameForm.get('selCategories')?.value;
    if (selCategories.length>0) {
      for (let category of selCategories) {
        if(!this.updatedGame?.categories?.includes(category)){
             this.updatedGame?.categories?.push(category);
        }
      }
    }  
  }

  deleteCategory(){
    if(this.gameForm.get('delCategories')?.value.length>0 && this.gameForm.get('delCategories')?.value ){
      for (let category of this.gameForm.get('delCategories')?.value) {
        let i= this.updatedGame!.categories!.indexOf(category)
        this.updatedGame!.categories!.splice(i,1)       
      }
    }
  }

  resetCategories(){
    this.updatedGame!.categories=JSON.parse(JSON.stringify(this.actualGame?.categories));

    console.log(this.actualGame?.categories);
  }

  reset(){
    this.gameForm.reset();
    this.updatedGame=JSON.parse(JSON.stringify(this.actualGame));
  }

  update(): void {
    if(this.gameForm.valid){
      swal.fire({
        title: "¿Deseas actualizar este juego?",
        showCancelButton: true,
        confirmButtonText: "Actualizar",
      }).then((result) => {
        if (result.isConfirmed) {
          this.updatedGame.title=this.gameForm.get('title')?.value
          this.updatedGame.author=this.gameForm.get('author')?.value
          this.updatedGame.description=this.gameForm.get('description')?.value
          this.updatedGame.price=this.gameForm.get('price')?.value
          this.updatedGame.image=this.gameForm.get('photo')?.value
          this.gameService.updateGame(this.actualGame.id!,this.updatedGame).subscribe(
            () => {
              swal.fire("Actualizado correctamente!", "", "success");
              this.route.navigateByUrl("/admin/games")            
            },
            error => {
              swal.fire("Ha ocurrido un error!", "", "error");
             console.error('Error al guardar el juego:', error);
            });
        }
      });
    }
    else{
      this.gameForm.markAllAsTouched();
    }
  }

  loadCategories(){
    this.gameService.getAllCategories().subscribe(
      data => {
        this.categories = data;
      },
      error => {
        console.log('Error fetching categories:', error);
      }
    );
  }

  loadGame(){
    this.activatedRoute.params.subscribe(
      id=>{this.gameService.getGame(id['id']).subscribe(
        data=>{
          this.actualGame=data;
          this.updatedGame=JSON.parse(JSON.stringify(this.actualGame));
          this.initGameForm();

        console.log('Es?:',data)
      })}
    )
    
  }
}
