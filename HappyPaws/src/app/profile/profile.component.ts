import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../entity/user';
import { UserService } from '../service/user.service';
import { UserauthService } from '../service/userauth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User;
  user1:any;
  id:number;
  constructor(private userService:UserService,private router:Router,private userAuth:UserauthService) { }
  temp:any;
  ngOnInit(): void {
    this.temp=this.userAuth.getUserData();
    this.id=this.temp.id
    this.userService.getOneUser(this.id).subscribe(
      data=>{
        this.user1=data;
        this.user=this.user1;
      },
      error=>{
        console.log(error);
      }
    );
    console.log(this.user1);
  }
  back(){
    this.router.navigate(['home']);
  }
  



}
