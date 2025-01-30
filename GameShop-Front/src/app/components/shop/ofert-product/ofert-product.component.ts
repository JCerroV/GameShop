import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Game } from 'src/app/models/game';
import { GameService } from 'src/app/services/game/game.service';

@Component({
  selector: 'app-ofert-product',
  templateUrl: './ofert-product.component.html',
  styleUrls: ['./ofert-product.component.css']
})
export class OfertProductComponent {
  games:Game[]=[];
  productosPorPagina: Game[][];

  constructor(private gameService:GameService){
    this.productosPorPagina = this.chunkArray(this.games, 3);

  }
  ngOnInit(): void {
    this.loadGames();
  }
  loadGames() {
    this.gameService.games1(0,15,'price',true,"").subscribe(
      data=>{
        this.games=data.content,
        this.productosPorPagina = this.chunkArray(this.games, 3);
      },
      error=>{ console.error("Error al recibir los datos de los juegos:",error)});
  }

  

  // Función para dividir una lista en grupos de tamaño específico
  chunkArray(array: Game[], size: number): Game[][] {
    const chunkedArray = [];
    for (let i = 0; i < array.length; i += size) {
      chunkedArray.push(array.slice(i, i + size));
    }
    return chunkedArray;
  }
}
