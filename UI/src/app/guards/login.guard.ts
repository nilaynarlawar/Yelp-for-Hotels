import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  private userInfo: any = {};
  constructor(private router: Router) {}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot) {
    this.userInfo = JSON.parse(sessionStorage.getItem("USER_INFO"));
    if(this.userInfo) {
      return (this.userInfo.userId > 0);
    } else {
      this.router.navigateByUrl('/').then(r =>{});
      return false;
    }
  }

}
