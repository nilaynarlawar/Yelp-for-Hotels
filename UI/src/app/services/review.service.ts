import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {Router} from "@angular/router";
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private httpService: HttpService,
              private router: Router) { }
  Liked(userId: any, reviewId: any){
    const params = new HttpParams({
      fromObject: {
        userId: userId,
        reviewId: reviewId,
      }
    });
    return this.httpService.post('review/reviewLike', params);
  }

  Unlike(userId: any, reviewId:any){
    const params = new HttpParams({
      fromObject: {
        userId: userId,
        reviewId: reviewId,
      }
    });
    return this.httpService.post('review/reviewUnlike', params);
  }

  saveReview(review: any){
    return this.httpService.post('review/createReview', review);
  }
}
