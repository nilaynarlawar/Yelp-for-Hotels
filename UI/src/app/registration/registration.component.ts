import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {HttpParams} from "@angular/common/http";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  userInfo: any;
  regForm: any;

  public postData = {
    userName: '',
    password: '',
    address: '',
    city: '',
    country: '',
    zipCode: '',
    emailId: '',
    lastLogin: '',
    isAdmin: '',
    creationTime:'',
    salt:''
  };
  constructor(private router: Router,
              private authService: AuthService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder) {
    this.regForm = this.formBuilder.group({
      userName:['', Validators.required],
      password: ['', [Validators.required, Validators.pattern("^(?=.*[\\w])(?=.*[\\d])(?=.*[\\W])[\\w\\W]{5,10}$")]],
      address: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      zipCode: ['', Validators.required],
      emailId: ['', [Validators.required, Validators.email]]
    });
  }

  get frmCntrl(){
    return this.regForm.controls;
  }

  ngOnInit() {
  }


  registerUser() {
    if(this.regForm.invalid) {
      alert("Cannot Register since input is invalid!!");
      return;
    }
    const params = new HttpParams({
      fromObject: {
        userName: this.regForm.value.userName,
        password: this.regForm.value.password,
        address: this.regForm.value.address,
        city: this.regForm.value.city,
        country: this.regForm.value.country,
        zipCode: this.regForm.value.zipCode,
        emailId: this.regForm.value.emailId,
        lastLogin: '',
        isAdmin: '',
        creationTime: '',
        salt: ''
      }
    });

    this.authService.registerUser(params).subscribe(res => {
          this.router.navigate(['/'], {relativeTo: this.route});
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
