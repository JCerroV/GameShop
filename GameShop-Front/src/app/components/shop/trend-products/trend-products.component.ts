import { Component, OnInit } from '@angular/core';
import { Game } from 'src/app/models/game';
import { GameService } from 'src/app/services/game/game.service';

@Component({
  selector: 'app-trend-products',
  templateUrl: './trend-products.component.html',
  styleUrls: ['./trend-products.component.css']
})
export class TrendProductsComponent implements OnInit {


  games:Game[]=[];
  productosPorPagina?: Game[][]

  constructor(private gameService:GameService) {

  }
  ngOnInit(): void {
    this.loadGames();
  }

  loadGames() {
    this.gameService.games1(0,16,'valuate',true,"").subscribe(
      data=>{
        this.games=data.content,
        this.productosPorPagina = this.chunkArray(this.games, 4);
      },
      error=>{ console.error("Error al recibir los datos de los juegos:",error)});
  }

  // Función para dividir una lista en grupos de tamaño específico
  chunkArray(array: any[], size: number): any[][] {
    const chunkedArray = [];
    for (let i = 0; i < array.length; i += size) {
      chunkedArray.push(array.slice(i, i + size));
    }
    return chunkedArray;
  }
}

