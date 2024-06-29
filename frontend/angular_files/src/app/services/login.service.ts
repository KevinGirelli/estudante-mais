import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  login(login: any): Observable<any>{
    const httpOptions = {headers: new HttpHeaders({
      "Content-type": "application/json"
    })}
    return this.httpClient.post("http://localhost:8080/auth/login", JSON.stringify(login), httpOptions);
  }
}
