import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {HotelService} from "../services/hotel.service";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-hotel-search',
  templateUrl: './hotel-search.component.html',
  styleUrls: ['./hotel-search.component.scss']
})
export class HotelSearchComponent implements OnInit {
  isSearchResult: boolean = false;
  isShowOnMap: boolean = false;
  public cities: any;
  public postData = {
    hotelName: '',
    city:''
  };
  public hotels : any;
  public totalHotels = 0;
  p = 1;
  zoom: number = 12;
  mapType = 'roadmap';
  markers: any = [];
  lat: any;
  lng: any;
  userInfo : any ={};

  constructor(private router: Router,
              private hotelService: HotelService,
              private userService: UserService) { }

  ngOnInit() {
    this.hotelService.getCities().subscribe(res => {
      this.cities = res;
      this.postData.city = res[0];
    },
      (error) => {
        if(error.status == 401){
          this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
          sessionStorage.removeItem("USER_INFO");
          this.router.navigateByUrl('/').then(r =>{});
        }
      })
  }

  searchHotel() {
    this.isSearchResult = true;
    this.isShowOnMap = false;
    this.hotelService.searchHotel(this.postData).subscribe(res => {
      this.hotels = res;
        this.markers = [];
        this.totalHotels = this.hotels.length;
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
    this.isShowOnMap = true;
    this.isSearchResult = false;
    this.hotelService.searchHotel(this.postData).subscribe(res => {
      this.hotels = res;
      this.totalHotels = this.hotels.length;
      this.lat = this.hotels[0].ll.lat;
      this.lng = this.hotels[0].ll.lng;
      this.markers = [];
      this.hotels.forEach(hotel => {
        this.markers.push(hotel['ll']);
      })
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
