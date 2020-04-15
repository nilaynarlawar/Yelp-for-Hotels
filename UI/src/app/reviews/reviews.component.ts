import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HotelService} from "../services/hotel.service";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {

  userInfo:any = {};
  hotelId :  any;
  hotels: any;
  hotelreviews: any = {};
  userReviews: any = {};
  hotelName: any = "";


  reviewsLikedByUser: any = [];

  hotelList: Array<any> = [];

  @Input()
  isMyReview : boolean = false;
  totalReviews: any = 0;
  isLoading: boolean = false;
  selectedHotel: any;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              private hotelService: HotelService) {
    route.params.forEach(params => {
      this.init(params['id']);
    });
  }

  init(param: any){
    this.ngOnInit();
  }

  ngOnInit() {
    this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
    this.hotelId = this.route.snapshot.paramMap.get("id");
    this.hotelList = [];
    this.hotelreviews={};
    this.userService.getReviewLikedByUserId(this.userInfo.userId).subscribe(res =>{
      this.reviewsLikedByUser = res;
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
    if(this.hotelId == 0){
      this.isMyReview=true;
      this.hotelreviews = {};
      this.loadUserReviews();
      return;
    }
    this.isMyReview=false;
    this.hotelService.getHotelReviewsById(this.hotelId).subscribe(res => {
        this.hotelreviews = res;
        this.totalReviews = this.hotelreviews.length;
        this.hotelName= this.hotelreviews[0].hotelName;
      },
      (error) => {
        if (error.status == 401) {
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r => {
          });
        }
      });
  }


  LoadReview() {
    this.isLoading = true;
    this.userService.getHotelReviewsByUserId(this.userInfo.userId, this.selectedHotel).subscribe(res => {
        this.hotelreviews = res;
        this.totalReviews = this.hotelreviews.length;
        this.isLoading = false;
      },
      (error) => {
        if (error.status == 401) {
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r => {
          });
        }
      });
  }

  private loadUserReviews() {
    this.userService.getHotelListByUserId(this.userInfo.userId).subscribe((res) =>{
      this.hotelList = [];
      Object.keys(res).forEach(key => {
      let val= res[key];
      this.hotelList.push({key: parseInt(key), value:val});
      });
      this.selectedHotel = this.hotelList[0].key;
    },(error) => {
      if (error.status == 401) {
        this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
        sessionStorage.removeItem("USER_INFO");
        this.router.navigateByUrl('/').then(r => {
        });
      }
    });
  }

  likeClicked() {
    this.init("id");
  }

  reviewUpdated() {
    this.init("id");
    this.LoadReview();
  }
}
