import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment as env } from '../../environments/environment';
import { User } from '../../@types/types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user: User | null = null;

  constructor(private http: HttpClient) {}

  private getUserRequest() {
    return this.http.get<User>(`${env.apiUrl}/user/me`, {
      withCredentials: true,
    });
  }

  postAuth(body: AuthForm, type: 'login' | 'register'): Observable<HttpResponse<User | void>> {
    if (type === 'register') {
      return this.http.post<User>(`${env.apiUrl}/user/register`, body, {
        withCredentials: true,
        observe: 'response',
      });
    }

    return this.http.post<void>(`${env.apiUrl}/user/login`, body, {
      withCredentials: true,
      observe: 'response',
    });
  }

  loadUser() {
    return this.getUserRequest().subscribe({
      next: (res: User) => {
        if (!res.isEnabled) {
          console.warn('User disabled, not added to context.');
          return;
        }
        this.user = res;
      },
      error: (err) => {
        console.error('Error on loading user:', err);
      },
    });
  }

  getUser() {
    return this.user;
  }
}
