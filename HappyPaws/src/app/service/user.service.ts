import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { apiUrl } from 'src/environments/environment';
import { User } from '../entity/user';
import { Userdto } from '../entity/userdto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }


  requestHeader=new HttpHeaders(
    {"No-Auth":"True"}
  )
  listallUsers() :Observable<any>{
    return this.http.get(apiUrl+"allusers");
  }
   getOneUser(id:number){
    return this.http.get(apiUrl+"getuser/"+id);
   }
  public loginUser(user:Userdto):Observable<any>{
    return this.http.post(apiUrl+"login",user,{headers:this.requestHeader})
  }
  public signupUser(user:User):Observable<any>{
    return this.http.post(apiUrl +"signup",user,{headers:this.requestHeader})
  }
  deleteUserId(id: any) {
    return this.http.get(apiUrl+"deleteuser/"+id);
  }
  editUser(user1: User, id: any) {
   return this.http.post(apiUrl+"edituser/"+id,user1);
  }
}
