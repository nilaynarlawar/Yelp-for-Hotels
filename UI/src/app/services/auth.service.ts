import {Injectable} from '@angular/core';
import {HttpService} from './http.service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpParams} from "@angular/common/http";
import {query} from "@angular/animations";


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private httpService: HttpService,
              private router: Router) {
  }

  login(loginData: any){
    const params = new HttpParams({
      fromObject: {
        userName: loginData.value.userName,
        password: loginData.value.password,
      }
    });
    return this.httpService.post('user/validateUser', params);
  }

  registerUser(postData: any){
    return this.httpService.post('user/create', postData);
  }


}
