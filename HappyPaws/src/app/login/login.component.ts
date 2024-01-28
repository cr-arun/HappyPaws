import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Userdto } from '../entity/userdto';
import { UserService } from '../service/user.service';
import { UserauthService } from '../service/userauth.service';
declare let alertify: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  log: LoginComponent;
  user = new Userdto();
  userlocal: any;
  local: any;
  decoded: any;
  constructor(
    private service: UserService,
    private router: Router,
    private userAuthService: UserauthService
  ) {}

  ngOnInit(): void {}
  login() {
    this.service.loginUser(this.user).subscribe(
      (data) => {
        console.log(data);
        this.userAuthService.setUserData(data.user);
        this.userAuthService.setToken(data.token);
        const userdata = this.userAuthService.getUserData();
        if (userdata.role === 'Admin') {
          this.router.navigate(['/admin']);

          //this.userAuthService.reloadData();
          alertify.success('Welcome Admin');
        } else if (userdata.role === 'User') {
          this.router.navigate(['/home']);

          //this.userAuthService.reloadData();
          alertify.success('Welcome User');
        }
      },
      (error) => {
        console.log('not received');
        alertify.error('Login Unsuccessful');
      }
    );
  }
}
