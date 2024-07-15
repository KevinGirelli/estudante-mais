import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  login(login: any): Observable<HttpResponse<any>>{
    const httpOptions = {headers: new HttpHeaders({
      "Content-type": "application/json"
    }),
    observe: 'response' as 'response'}
    return this.httpClient.post("http://localhost:8080/auth/login", JSON.stringify(login), httpOptions);
  }
}
