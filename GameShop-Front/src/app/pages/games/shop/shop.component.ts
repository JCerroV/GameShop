import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Game } from 'src/app/models/game';
import { GameService } from 'src/app/services/game/game.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  selectedCategories:number[]=[]
  categories?: Category[];
  inputValue: string = '';
  game:Game={};
  games:Game[]=[];
  isFirst?:boolean;
  isLast?:boolean;
  page:number=0;
  totalPages:number=0;
  filterChain:string='';

  constructor(private activeRoute:ActivatedRoute,private gameService:GameService){}

  ngOnInit(): void {
    this.loadGames();
    this.loadCategories()
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
  
  loadGames() {
    this.gameService.games1(this.page,15,'id',true,this.inputValue).subscribe(
      data=>{
        this.games=data.content,
        this.isFirst=data.first,
        this.isLast=data.last,
        
        this.totalPages=data.totalPages
      },
      error=>{ console.error("Error al recibir los datos de los juegos:",error)});
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
  setPage(page:number):void{
    this.page=page-1;
    this.loadGames();
  }

  filtrarJuegos() {
    this.page=0;
   this.loadGames(); 
  }

  filterByCategories(){
    let params=new HttpParams();
    if(this.selectedCategories.length>0){
      
      params=params.append('categoryIds',this.selectedCategories.join(','))
      console.log(params)
      this.gameService.getGamesByCategories(params).subscribe(data=>this.games=data.content)
    }
  }

  changeSelectedCategories(event:any){
    const categoryId = event.target.value;
    if (event.target.checked){
      this.selectedCategories.push(categoryId)
    }else{
      this.selectedCategories= this.selectedCategories.filter(id => id != categoryId)
    }
    if(this.selectedCategories.length>0){
      this.filterByCategories();
    }else{
      this.loadGames();
    }
  }
}
