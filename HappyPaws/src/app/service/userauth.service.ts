import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserauthService {
  constructor() {}
  public setUserData(userdata) {
    localStorage.setItem('userdata', JSON.stringify(userdata));
  }
  public getUserData(): any {
    return JSON.parse(localStorage.getItem('userdata'));
  }
  public setToken(token) {
    localStorage.setItem('token', JSON.stringify(token));
  }
  public getToken(): any {
    return JSON.parse(localStorage.getItem('token'));
  }

  public clear() {
    localStorage.clear();
    this.reloadData();
  }
  public isLoggedIn() {
    return this.getUserData() && this.getToken();
  }
  public reloadData() {
    setTimeout(function () {
      location.reload();
    }, 30);
  }
  public getUserId() {
    let temp: any;
    temp = this.getUserData;
    return temp.id;
  }
  public getUserName() {
    let temp = this.getUserData();
    return temp.name;
  }
}
