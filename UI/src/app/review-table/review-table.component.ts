import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HotelService} from "../services/hotel.service";
import {UserService} from "../services/user.service";
import {FormBuilder} from "@angular/forms";
import {ReviewService} from "../services/review.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-review-table',
  templateUrl: './review-table.component.html',
  styleUrls: ['./review-table.component.scss']
})
export class ReviewTableComponent implements OnInit {

  @Input()
  reviews : any = {};

  @Input()
  isMyReview: boolean = false;

  currEditReviewId: any;

  @Input()
  reviewsLikedByUser:any = [];

  p: any = 1;
  reviewData = {
    title: '',
    desc: '',
    rating:'',
    reviewId:0,
    hotelId:0,
    userId:0
  };

  userInfo: any = {};
  @Input()
  totalReviews;
  likeBtnText: any = "Like";

  @Output()
  likeClick: EventEmitter<String> = new EventEmitter<String>();

  @Output()
  reviewChanged: EventEmitter<String> = new EventEmitter<String>();


  ReviewForm: any;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private reviewService: ReviewService,
              private formBuilder: FormBuilder,
              private userService: UserService) { }

  ngOnInit() {
    this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
  }

  Liked(reviewId:any) {
    this.reviewService.Liked(this.userInfo.userId,reviewId).subscribe(res => {
      if(res){
        this.likeClick.emit();
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

  Unlike(reviewId:any){
    this.reviewService.Unlike(this.userInfo.userId,reviewId).subscribe(res => {
      if(res){
        this.likeClick.emit();
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

  isAlreadyLikedByUser(reviewId:any) {
    if(this.reviewsLikedByUser.indexOf(reviewId) !== -1)
      return true;
    return false;
  }

  saveReview(reviewID){
    this.reviewData.reviewId = reviewID;
    const titlebox= "ReviewTitle_"+reviewID;
    const rating = "Rating_"+reviewID;
    const desc = "ReviewText_"+reviewID;
    this.reviewData.title = (<HTMLInputElement>document.getElementById(titlebox)).value;
    this.reviewData.rating=(<HTMLInputElement>document.getElementById(rating)).value;
    this.reviewData.desc = (<HTMLInputElement>document.getElementById(desc)).value;
    const params = new HttpParams({
      fromObject: {
        rating: this.reviewData.rating,
        title: this.reviewData.title,
        reviewText: this.reviewData.desc,
        id: reviewID
      }
    });
    this.userService.updateUserReview(params).subscribe(res => {
      if(res){
        this.reviewChanged.emit();
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

  deleteReview(id: any) {
    const params = new HttpParams({
      fromObject: {
        id: id
      }
    });
    this.userService.deleteUserReview(params).subscribe(res => {
      if(res){
        this.reviewChanged.emit();
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
