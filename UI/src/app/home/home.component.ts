import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userInfo: any = {};
  menu: string = "hotel";
  constructor(private router: Router,
              private route: ActivatedRoute,
              private userService: UserService) {

  }

  ngOnInit() {
    this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
  }

  routeToHistory() {
    this.router.navigate(['userHistory'],{relativeTo: this.route});
    this.menu='history';
  }

  routeToHotelSearch() {
    this.router.navigate(['hotelSearch'],{relativeTo: this.route});
    this.menu = 'hotel';
  }

  routeToReviews() {
    this.router.navigate(['review',0], {relativeTo: this.route});
    this.menu='review';
  }

  LogOut() {
    this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
    this.userService.logOut(this.userInfo.userId).subscribe((res) => {
        sessionStorage.removeItem("USER_INFO");
        this.router.navigateByUrl('/').then(r =>{});
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
