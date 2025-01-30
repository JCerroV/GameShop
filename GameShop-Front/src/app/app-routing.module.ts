import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopComponent } from './pages/games/shop/shop.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { SignupComponent } from './pages/auth/signup/signup.component';
import { GameComponent } from './pages/games/game/game.component';
import { GameListComponent } from './admin/games/game-list/game-list.component';
import { UserListComponent } from './admin/users/user-list/user-list.component';
import { TicketListComponent } from './admin/support/ticket-list/ticket-list.component';
import { ModifyGameComponent } from './admin/games/modify-game/modify-game.component';
import { AddGameComponent } from './admin/games/add-game/add-game.component';
import { ModifyUserComponent } from './admin/users/modify-user/modify-user.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { CartComponent } from './pages/cart/cart.component';
import { OrderDetailComponent } from './pages/order-detail/order-detail.component';
import { LibraryComponent } from './pages/library/library.component';
import { DepositComponent } from './components/deposit/deposit.component';
import { FriendsComponent } from './pages/social/friends/friends.component';
import { ChatsComponent } from './pages/social/chats/chats.component';
import { ForumComponent } from './pages/social/forum/forum.component';
import { ForumPostComponent } from './pages/social/forum/forum-post/forum-post.component';
import { NewForumPostComponent } from './pages/social/forum/new-forum-post/new-forum-post.component';
import { OrderHistoryComponent } from './pages/profile/components/order-history/order-history.component';

const routes: Routes = [
  {
    path:'',
    component:ShopComponent,
    pathMatch:'full'
  },
  {
    path:'login',
    component:LoginComponent,
    pathMatch:'full'
  },
  {
    path:'signup',
    component:SignupComponent,
    pathMatch:'full'
  },
  {
    path:'shop',
    component:ShopComponent,
    pathMatch:'full'
  },
  {
    path:'games/:id',
    component:GameComponent,
    pathMatch:'full'
  },
  {
    path:'admin/games',
    component:GameListComponent,
    pathMatch:'full'
  },
  {
    path:'admin/games/new',
    component:AddGameComponent,
    pathMatch:'full'
  },
  {
    path:'admin/games/update/:id',
    component:ModifyGameComponent,
    pathMatch:'full'
  },
  {
    path:'admin/users',
    component:UserListComponent,
    pathMatch:'full'
  },
  {
    path:'admin/users/:id/update',
    component:ModifyUserComponent,
  },
  {
    path:'users/:username',
    component:ProfileComponent
  },
  {
    path:'profile',
    component:ProfileComponent
  },
  {
    path:':username/cart',
    component:CartComponent
  },
  {
    path:'buy',
    component:OrderDetailComponent
  },
  {
    path:'buy/:id',
    component:OrderDetailComponent
  },
  {
    path:'myLibrary',
    component:LibraryComponent,
    
  },
  {
    path:'deposit',
    component:DepositComponent,
  },
  {
    path:'friends',
    component:FriendsComponent,
  },
  {
    path:'forum',
    component:ForumComponent,
  },
  {
    path:'forum/new',
    component:NewForumPostComponent,
  },
  {
    path:'forum/:id',
    component:ForumPostComponent,
  },
  {
    path:'chats',
    component:ChatsComponent,
  },
  {
    path:'chats/:username',
    component:ChatsComponent,
  },
  {
    path:'admin/tickets',
    component:TicketListComponent,
    pathMatch:'full'
  },
  {
    path:'admin/users/:username/orders',
    component:OrderHistoryComponent,
    pathMatch:'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
