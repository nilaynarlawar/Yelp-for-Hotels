import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HotelService} from "../services/hotel.service";
import {UserService} from "../services/user.service";
import {FormBuilder, Validators} from "@angular/forms";
import {HttpParams} from "@angular/common/http";
import {ReviewService} from "../services/review.service";

@Component({
  selector: 'app-view-hotel',
  templateUrl: './view-hotel.component.html',
  styleUrls: ['./view-hotel.component.scss']
})
export class ViewHotelComponent implements OnInit {
  isAddReview: boolean = false;
  isAttraction: boolean = false;
  isImage: boolean = false;
  reviewForm: any;
  hotelId :  any;
  hotelInfo: any = {};
  geoCord: any = {};
  lat: any;
  lng: any;
  zoom: number = 10;
  isShowOnMap: boolean = false;
  mapType = 'roadmap';
  userInfo: any ={};
  radius: any;
  attractions:any;
  noOfAttractions: number = 0;
  currentActiveImage:number = 1;
  isActive1: any = 'active';
  isActive2: any;
  isActive3: any;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private hotelService: HotelService,
              private userService: UserService,
              private reviewService: ReviewService,
              private formBuilder: FormBuilder) {

    this.reviewForm = this.formBuilder.group({
      reviewTitle:['', Validators.required],
      rating: ['', Validators.required],
      reviewDate: [new Date(), Validators.required],
      desc: ['', Validators.required],
      userId: [''],
      hotelId:['']
    });
  }


  get frmCntrl(){
    return this.reviewForm.controls;
  }
  ngOnInit() {
    this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
    this.hotelId = this.route.snapshot.paramMap.get("id");
    this.hotelService.getHotelInfoById(this.hotelId).subscribe(res => {
      this.hotelInfo = res;
      this.geoCord = this.hotelInfo["ll"];
      this.lat = this.geoCord.lat;
      this.lng = this.geoCord.lng;
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
  }

  addReview(){
    this.isShowOnMap = false;
    this.isAddReview = true;
    this.isAttraction = false;
    this.isImage = false;
    this.noOfAttractions = 0;
  }

  viewReviews() {
    this.router.navigate(['../../review',this.hotelId], {relativeTo: this.route});
  }

  goToLink(expedia_link: any) {
    window.open(expedia_link);
    this.userService.updateUserHistory(this.hotelId, this.userInfo.userId).subscribe(res => {
      console.log(res);
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
  }

  SaveHotel() {
    this.userService.updateSaveHotel(this.hotelId, this.userInfo.userId).subscribe(res => {
      console.log(res);
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
  }

  showHotelOnMap() {
     this.isImage = false;
     this.isAddReview = false;
     this.isAttraction = false;
     this.isShowOnMap = true;
  }

  showHotelImages() {
    this.isShowOnMap = false;
    this.isAddReview = false;
    this.isAttraction = false;
    this.isImage = true;
    this.noOfAttractions = 0;
  }

  saveReview() {
    const params = new HttpParams({
      fromObject: {
        hotelId: this.hotelId,
        rating: this.reviewForm.value.rating,
        reviewText: this.reviewForm.value.desc,
        title: this.reviewForm.value.reviewTitle,
        user: this.userInfo.userName,
        userid: this.userInfo.userId,
        reviewPostDate: this.reviewForm.value.reviewDate,
      }
    });
    this.reviewService.saveReview(params).subscribe(res => {
      this.router.navigate(['../../review', this.hotelId], {relativeTo: this.route});
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      });
  }

  fetchAttraction() {
    if(this.radius == undefined || this.radius == "" || this.radius == 0){
      alert("please enter radius");
      return;
    }
    this.isShowOnMap = false;
    this.isAddReview = false;
    this.attractions = false;
    this.isImage = false;
    this.isAttraction = true;

    this.hotelService.fetchAttraction(this.hotelId,this.radius).subscribe((res) =>{
      let results:any  = {};
      results = res;
      this.attractions = results.results;
      this.noOfAttractions = this.attractions.size;
    },(error) => {
      if(error.status == 401){
        this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
        sessionStorage.removeItem("USER_INFO");
        this.router.navigateByUrl('/').then(r =>{});
      }
    });
  }

  nextImage() {
    this.currentActiveImage +=1;
    if (this.currentActiveImage > 3) {
      this.currentActiveImage = 1;
    }
    this.displayImages();
  }

  showPreviousImage() {
    this.currentActiveImage -= 1;
    if(this.currentActiveImage == 0 || this.currentActiveImage == 3){
      this.currentActiveImage =3;
    }
    this.displayImages();
  }

  private displayImages(){
    if (this.currentActiveImage == 1) {
      this.isActive1 = 'active';
      this.isActive2 = '';
      this.isActive3 = '';
    } else if (this.currentActiveImage == 2) {
      this.isActive3 = '';
      this.isActive2 = 'active';
      this.isActive1 = '';
    } else if (this.currentActiveImage == 3) {
      this.isActive3 = 'active';
      this.isActive2 = '';
      this.isActive1 = '';
    }
  }
}
