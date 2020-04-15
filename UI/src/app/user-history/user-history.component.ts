import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-user-history',
  templateUrl: './user-history.component.html',
  styleUrls: ['./user-history.component.scss']
})
export class UserHistoryComponent implements OnInit {

  userInfo: any = {};
  userExpediaHistory: any = {};
  userSavedHotelHistory: any = {};
  constructor(private router: Router,
              private route: ActivatedRoute,
              private userService: UserService) { }

  ngOnInit() {
    this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
    this.userService.getUserHistory(this.userInfo.userId).subscribe(res => {
      this.userExpediaHistory = res;
    },
    (error) => {
      if(error.status == 401){
        this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
        sessionStorage.removeItem("USER_INFO");
        this.router.navigateByUrl('/').then(r =>{});
      }
    });
    this.userService.getUserSavedHotel(this.userInfo.userId).subscribe(res =>{
      this.userSavedHotelHistory = res;
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
  }

  goToLink(expedia_link: any) {
    window.open(expedia_link);
  }

  clearExpediaHistory() {
    this.userService.clearExpediaHistory(this.userInfo.userId).subscribe(res => {
      if(res){
        this.ngOnInit();
      }
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
  }

  clearHotelHistory() {
    this.userService.clearHotelHistory(this.userInfo.userId).subscribe(res => {
      if(res){
        this.ngOnInit();
      }
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
  }
}
