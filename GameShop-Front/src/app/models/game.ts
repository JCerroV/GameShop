import { Category } from "./category";

export class Game{
    id?:number;
    title?:string;
    author?:string;
    description?:string;
    price?:number;
    image?:string;
    categories?:Category[];
    status?:boolean
    valuate?:number
    createdAt?:string;
    formatCreateAt?:string;
    updatedAt?:string;
    formatUpdatedAt?:string;

      constructor(){
        this.categories = [];
      }

      
}
export interface GameComment{
  id:number;
	username:string;
	text:string;
	createdAt:string;
	updatedAt:string;

}
export interface Rate{
  id:number,
  rate:number
}