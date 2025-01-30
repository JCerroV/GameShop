import { Game } from "./game";

export interface User{
    id?:number;
    username:string;
    email:string;
    role:string;
    balance:number;
    status:number;
    profile:Profile;
    shoppingCart:ShoppingCart[];
    library:Game[];
    friends:User[];
}

export interface Profile{
    name:string;
    surnames:string;
    location:string;
    phone:string;
    biografy:string;
    privacy:string;
    photo:String;
    
    

}

export interface ShoppingCart{
    id:number;
    game:Game;
    addedDate:Date;

}

export interface Purchase{
    game:Game;
    id:number;
    createdAt:Date;

}

export interface Relation{
    id:number;
    user:User;
    friend:User;
    
}

