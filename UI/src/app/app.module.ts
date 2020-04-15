import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {RouterModule} from "@angular/router";
import { RegistrationComponent } from './registration/registration.component';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { UserHistoryComponent } from './user-history/user-history.component';
import { HotelSearchComponent } from './hotel-search/hotel-search.component';
import { ReviewsComponent } from './reviews/reviews.component';
import {
  MatExpansionModule,
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import {CommonModule} from "@angular/common";
import {PortalModule} from "@angular/cdk/portal";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { ReviewTableComponent } from './review-table/review-table.component';
import { ViewHotelComponent } from './view-hotel/view-hotel.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxPaginationModule} from "ngx-pagination";
import {AgmCoreModule} from "@agm/core";
import {LoginGuard} from "./guards/login.guard";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    AdminComponent,
    HomeComponent,
    UserHistoryComponent,
    HotelSearchComponent,
    ReviewsComponent,
    ReviewTableComponent,
    ViewHotelComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CommonModule,
    AppRoutingModule,
    MatExpansionModule,
    PortalModule,
    BrowserAnimationsModule,
    FormsModule,
    AgmCoreModule,
    NgxPaginationModule,
    ReactiveFormsModule,
    AgmCoreModule.forRoot( {
      apiKey: 'AIzaSyD2nDLzx7U7u_VhOjlcIDyOdZJsJSXFeXc',
      libraries:['places','geometry','imagery']
    }),
    RouterModule.forRoot([
      {
        path: '',
        component: LoginComponent
      },
      {
        path: 'registration',
        component: RegistrationComponent
      },
      {
        path: 'admin',
        component: AdminComponent,
        canActivate: [LoginGuard]
      },
      {
        path: 'home',
        component: HomeComponent,
        canActivate: [LoginGuard],
        children: [
          {
            path: 'userHistory',
            component: UserHistoryComponent,
            canActivate: [LoginGuard]
          },
          {
            path: 'hotelSearch',
            component: HotelSearchComponent,
            canActivate: [LoginGuard]
          },
          {
            path: 'viewHotel/:id',
            component: ViewHotelComponent,
            canActivate: [LoginGuard]
          },
          {
            path: 'review/:id',
            component: ReviewsComponent,
            canActivate: [LoginGuard]
          }
        ]
      }
    ]),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [HttpClientModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
