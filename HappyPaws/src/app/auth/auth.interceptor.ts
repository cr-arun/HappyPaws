import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs/internal/Observable";
import { UserauthService } from "../service/userauth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private userAuthService: UserauthService,
      private router:Router) {}
  
    intercept( req: HttpRequest<any>,  next: HttpHandler): Observable<HttpEvent<any>> {
      if (req.headers.get('No-Auth') === 'True') {
        return next.handle(req.clone());
      }
  
      const token = this.userAuthService.getToken();
  
      req = this.addToken(req, token);
  
      return next.handle(req);
    }
  
  
    private addToken(request:HttpRequest<any>, token:string) {
        return request.clone(
            {
                setHeaders: {
                    Authorization : `Bearer ${token}`
                }
            }
        );
    }
  }