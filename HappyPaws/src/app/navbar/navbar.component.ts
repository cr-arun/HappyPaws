import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserauthService } from '../service/userauth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  checkuser: any;
  temp: any;
  username: String;
  public totalItem: number;

  constructor(private router: Router, private userAuth: UserauthService) {}

  ngOnInit(): void {
    this.checkuser = this.userAuth.getUserData();
    console.log(this.checkuser);
    this.username = this.checkuser.name;
    console.log(this.username);
  }
  doSearch(value: string) {
    console.log(`value=${value}`);
    this.router.navigateByUrl(`/search/${value}`);
    this.userAuth.reloadData();
  }

  logout() {
    this.userAuth.clear();

    this.router.navigate(['']);
  }
}
