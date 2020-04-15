import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  constructor(private httpService: HttpService,
              private router: Router) { }

  searchHotel(postData:any){
    const queryParam = "city=" + postData.city + "&hotelName=" + postData.hotelName;
    const url = 'hotel/getHotelInfo?';
    return this.httpService.get(url,queryParam);
  }

  getCities(){
    return this.httpService.getWithoutParams('hotel/getHotelCity');
  }

  getHotelInfoById(hotelId){
    const queryParam = "hotelid=" + hotelId;
    const url = 'hotel/getHotelInfoById?';
    return this.httpService.get(url,queryParam);
  }

  getHotelReviewsById(hotelId){
    const queryParam = "hotelid=" + hotelId;
    const url = 'review/getReviewList?';
    return this.httpService.get(url,queryParam);
  }

  fetchAttraction(hotelId: any, radius: any){
    const queryParam = "hotelid=" + hotelId + "&radius=" + radius;
    const url = 'hotel/getAttractions?';
    return this.httpService.get(url,queryParam);
  }
}
