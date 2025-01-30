import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { SignupComponent } from './pages/auth/signup/signup.component';
import { ShopComponent } from './pages/games/shop/shop.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { FooterComponent } from './components/footer/footer.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OfertProductComponent } from './components/shop/ofert-product/ofert-product.component';
import { TrendProductsComponent } from './components/shop/trend-products/trend-products.component';
import { GameComponent } from './pages/games/game/game.component';
import { UserListComponent } from './admin/users/user-list/user-list.component';
import { ModifyUserComponent } from './admin/users/modify-user/modify-user.component';
import { ModifyGameComponent } from './admin/games/modify-game/modify-game.component';
import { GameListComponent } from './admin/games/game-list/game-list.component';
import { AddGameComponent } from './admin/games/add-game/add-game.component';
import { TicketListComponent } from './admin/support/ticket-list/ticket-list.component';
import { ShowTicketComponent } from './admin/support/show-ticket/show-ticket.component';
import { NewTicketComponent } from './admin/support/new-ticket/new-ticket.component';
import { DatePipe } from '@angular/common';
import { JwtInterceptorService } from './services/auth/jwt-interceptor.service';
import { CartComponent } from './pages/cart/cart.component';
import { OrderDetailComponent } from './pages/order-detail/order-detail.component';
import { OrderHistoryComponent } from './pages/profile/components/order-history/order-history.component';
import { LibraryComponent } from './pages/library/library.component';
import { DepositComponent } from './components/deposit/deposit.component';
import { FriendsComponent } from './pages/social/friends/friends.component';
import { ChatsComponent } from './pages/social/chats/chats.component';
import { ChatComponent } from './pages/social/chat/chat.component';
import { ForumComponent } from './pages/social/forum/forum.component';
import { NewForumPostComponent } from './pages/social/forum/new-forum-post/new-forum-post.component';
import { ForumPostComponent } from './pages/social/forum/forum-post/forum-post.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    SignupComponent,
    ShopComponent,
    ProfileComponent,
    FooterComponent,
    OfertProductComponent,
    TrendProductsComponent,
    GameComponent,
    UserListComponent,
    ModifyUserComponent,
    ModifyGameComponent,
    GameListComponent,
    AddGameComponent,
    TicketListComponent,
    ShowTicketComponent,
    NewTicketComponent,
    CartComponent,
    OrderDetailComponent,
    OrderHistoryComponent,
    FriendsComponent,
    LibraryComponent,
    DepositComponent,
    ChatsComponent,
    ChatComponent,
    ForumComponent,
    NewForumPostComponent,
    ForumPostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [DatePipe ,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
  
})
export class AppModule { }
