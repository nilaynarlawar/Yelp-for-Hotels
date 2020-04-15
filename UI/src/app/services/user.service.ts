import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {Router} from "@angular/router";
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpService: HttpService,
              private router: Router) { }

  updateUserHistory(hotelId:any, userId: any){
    const params = new HttpParams({
      fromObject: {
        userId: userId,
        hotelId: hotelId,
      }
    });
    return this.httpService.post('user/updateUserHistory', params);
  }

  updateSaveHotel(hotelId: any, userId:any){
    const params = new HttpParams({
      fromObject: {
        userId: userId,
        hotelId: hotelId,
      }
    });
    return this.httpService.post('user/updateSaveHotel', params);
  }
  getUserHistory(userId: any){
    const queryParam = "userId=" + userId;
    const url = 'user/getHistory?';
    return this.httpService.get(url,queryParam);
  }

  getUserSavedHotel(userId: any){
    const queryParam = "userId=" + userId;
    const url = 'user/getSavedHotel?';
    return this.httpService.get(url,queryParam);
  }

  clearExpediaHistory(userId:any){
    const params = new HttpParams({
      fromObject: {
        userId: userId
      }
    });
    return this.httpService.post('user/deleteUserHistory', params);
  }

  clearHotelHistory(userId: any){
    const params = new HttpParams({
      fromObject: {
        userId: userId
      }
    });
    return this.httpService.post('user/deleteSaveHotel', params);
  }

  logOut(userId: any) {
    const params = new HttpParams({
      fromObject: {
        userId: userId
      }
    });
    const url = 'user/deleteSession';
    return this.httpService.post(url,params);
  }

  getHotelReviewsByUserId(userId:any , hotelid: any){
    const queryParam = "userid=" + userId + "&hotelid=" + hotelid;
    const url = 'review/getReviewsForUser?';
    return this.httpService.get(url,queryParam);
  }

  getHotelListByUserId(userId: any){
    const queryParam = "userid=" + userId;
    const url = 'review/getHotelListByUserId?';
    return this.httpService.get(url,queryParam);
  }

  getReviewLikedByUserId(userId: any) {
    const queryParam = "userid=" + userId;
    const url = 'review/getUserLikedReviews?';
    return this.httpService.get(url,queryParam);
  }

  updateUserReview(review){
    const url = 'review/updateReview';
    return this.httpService.post(url,review);
  }

  deleteUserReview(params: HttpParams) {
    const url = 'review/deleteReview';
    return this.httpService.post(url,params);
  }
}
