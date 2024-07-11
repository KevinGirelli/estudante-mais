import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private httpClient: HttpClient) { }

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-type': 'application/json'
    })
  };

  registerStudent(student: any): Observable<any> {
    return this.httpClient.post('http://localhost:8080/admin/registerStudent', JSON.stringify(student), this.httpOptions);
  }

  registerTeacher(teacher: any): Observable<any> {
    return this.httpClient.post('http://localhost:8080/admin/registerTeacher', JSON.stringify(teacher), this.httpOptions);
  }

  registerClass(classData: any): Observable<any> {
    return this.httpClient.post('http://localhost:8080/admin/registerClass', JSON.stringify(classData), this.httpOptions);
  }
}
