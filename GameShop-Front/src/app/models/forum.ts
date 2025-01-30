import { User } from "./user"

export interface Thread{
    id:number,
    title:string,
    user:User,
    createdAt:string,
    updatedAt:string,
    status:boolean,
    visits:number,
    posts:number

}

export interface ThreadPost{
    id:number,
    content:string,
    userDTO:User,
    createdAt:string,
    updatedAt:string,
    editBy:User,
    editMode:boolean

}