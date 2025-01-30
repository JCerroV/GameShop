import { User } from "./user";

export interface ChatMessage{
    text:string;
    username:string;
    sendAt?:string
}

export interface UnreadMessages{
    userDTO:User;
    unreadMessageCount:number;
}