import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
   loginForm: any;
   userInfo: any = {};

  constructor(private router: Router,
              private authService: AuthService,
              private formBuilder: FormBuilder) {
   this.loginForm = this.formBuilder.group({
      userName:['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  get frmCntrl(){
    return this.loginForm.controls;
  }

  ngOnInit() {
  }

  registrationPage() {
    this.router.navigateByUrl('registration').then(r =>{});
  }

  login() {
    if(this.loginForm.invalid) {
       alert("Cannot login since input is invalid!!");
       return;
    }
    this.authService.login(this.loginForm).subscribe((response) => {
      this.userInfo = response;
       if(this.userInfo.userId > 0){
         sessionStorage.setItem("USER_INFO", JSON.stringify(this.userInfo));
         this.router.navigateByUrl('/home/hotelSearch').then(r =>{});
       }
    });

  }
}
