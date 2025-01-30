import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Game } from 'src/app/models/game';
import { GameService } from 'src/app/services/game/game.service';
import swal from 'sweetalert2'

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit{
  games:Game[];
  deleteGames?:number[];
  isFirst?:boolean;
  isLast?:boolean;
  totalpages:number=0;
  page:number=0
  order:string='id'
  asc:boolean=true
  size:number=5
  filterChain:string=""
  juegosFiltrados?: Game[];

  selectedGames:number[]=[]
  

  constructor(private gameService:GameService, private datePipe:DatePipe){
    this.games=[]
  }
  ngOnInit(): void {
    this.loadGames();
  }

  gameSelection(event: any, gameId: number): void {
    if (event.target.checked ) {
      this.selectedGames.push(gameId);
    } else {
      const index = this.selectedGames.indexOf(gameId);
      if (index !== -1) {
        this.selectedGames.splice(index, 1);
      }
    }
  }

  formatDate(date: any): string {
    return this.datePipe.transform(date, 'HH:mm dd-MM-yyyy') || '';
  }


  updateVisibility(game:Game): void {
    game.status=!game.status;
    this.gameService.updateGame(game.id!,game).subscribe(
      ()=>{
        this.loadGames();
      })
    
  }
  
  deleteSelected(): void {
    if(this.selectedGames.length>0){
      swal.fire({
        title: "Deseas eliminar los juegos seleccionados?",
        text: "No podras recuperarlos",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si,eliminar"
      }).then((result) => {
        if (result.isConfirmed) {
          this.selectedGames.forEach(gameID=>{
            this.gameService.deleteGame(gameID).subscribe(()=>{this.loadGames()},
            error=>{console.error('Error al borrar',error)
            swal.fire({
              title: "Error!",
              text: "Ha ocurrido un error",
              icon: "error"
            });
          });
        });
          swal.fire({
              title: "Eliminado!",
              text: "Eliminado correctamente.",
              icon: "success"
            });
        }
      });
    }
  }


  rewind():void{
    if(!this.isFirst){
      this.page--;
      this.loadGames();
    }
  }
  next():void{
    if(!this.isLast){
      this.page++;
      this.loadGames();
    }
  }
  loadGames() {
    this.gameService.games(this.page,this.size,this.order,this.asc,this.filterChain).subscribe(
      data=>{console.log(data),
        this.games=data.content,
        this.games.map(game=>{
          game.formatCreateAt = this.formatDate(game.createdAt);
          game.formatUpdatedAt = this.formatDate(game.updatedAt);
          return game;
        });
      
        this.isFirst=data.first,
        this.isLast=data.last,
        this.totalpages=data.totalPages
        this.selectedGames=[]
        console.log(this.games)
      },
      error=>{ console.error("Error al recibir los datos de los juegos:",error)});
  }

  ordenar():void{
    this.asc=!this.asc;
    this.loadGames();
  }

  setPage(page:number):void{
    this.page=page-1;
    this.loadGames();
  }

  public sentOrder(order:string): void{
    this.order=order;
    this.loadGames();
  }
  deleteGame(id:number|undefined):void{
    if(id!=undefined){
      swal.fire({
        title: "Deseas eliminar el juego",
        text: "No podras recuperarlo",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si,eliminar"
      }).then((result) => {
        if (result.isConfirmed) {
          this.gameService.deleteGame(id).subscribe(
            sucess=>{console.log('Borrado correctamente')
            swal.fire({
              title: "Eliminado!",
              text: "Eliminado correctamente.",
              icon: "success"
            });
            this.loadGames();
            },
            error=>{console.error('Error al borrar',error)
            swal.fire({
              title: "Error!",
              text: "Ha ocurrido un error",
              icon: "error"
            });
            });
          
        }
      });
    }
  }

  cleanFilter():void{
    this.filterChain='';
    this.filtrarJuegos();
  }

  filtrarJuegos() {
    this.page=0;
   this.loadGames(); 
  }
}
