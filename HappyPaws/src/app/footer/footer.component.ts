import { Component, OnInit } from '@angular/core';
import { UserauthService } from '../service/userauth.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  isCheckedIn:boolean
  constructor(private userAuth:UserauthService) { }

  ngOnInit(): void {
    this.isCheckedIn=this.userAuth.isLoggedIn();
  }

}
