import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { User } from '../user';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthenticationService {

  private authUrl = 'http://localhost:8089/api/v1/auth';
  constructor(public http: HttpClient) {

  }

  isUserAuthenticated(token): Promise<boolean> {
    return this.http.post(this.authUrl + '/isAuthenticated', {}, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`)
    }).map((response) => response['isAuthenticated'])
      .toPromise();
  }

  public authenticateUser(user): Observable<any> {
    return this.http.post<any>(`${this.authUrl}/login`, user);
  }

  public createUser(user): Observable<User> {
    return this.http.post<any>(`${this.authUrl}/register`, user);
  }

  public setBearerToken(token) {
    localStorage.setItem('bearerToken', token);
  }

  public getBearerToken() {
    return localStorage.getItem('bearerToken');
  }

  public setUserId(userId: string) {
    localStorage.setItem('userId', userId);
  }
  public getUserId() {
    return localStorage.getItem('userId');
  }
  public clearLocaStorage() {
    localStorage.clear();
  }
}
