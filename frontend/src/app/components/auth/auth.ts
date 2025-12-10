import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Button } from '../ui/button/button';
import { AuthService } from '../../services/auth-service';
import { User } from '../../../@types/types';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  imports: [FormsModule, Button],
  templateUrl: './auth.html',
  styleUrl: './auth.scss',
})
export class Auth {
  constructor(private authService: AuthService, private router: Router) {}

  user: User | null = null;
  notifyToDoLogin: boolean = false;

  formType: 'login' | 'register' = 'register';
  defaultFormValue: AuthForm = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  form: AuthForm = this.defaultFormValue;

  switchFormType() {
    this.formType = this.formType === 'register' ? 'login' : 'register';
    this.form = this.defaultFormValue;
  }

  onSubmit() {
    console.log(this.form, this.formType);
    this.authService.postAuth(this.form, this.formType).subscribe({
      next: (res) => {
        console.log(res);
        if (res.status === 204) {
          this.authService.loadUser();
          this.router.navigate(['/painel']);
          // TODO: First Success Message
          return;
        }

        if (res.status === 403) {
          // TODO: Bad credentials message
          return;
        }

        if (res.status === 409) {
          // TODO: Account already exist message
          return;
        }
        this.tempNotification();
      },
      error(err) {
        console.error('Error during auth request: ', err, err.status);
      },
    });
  }

  tempNotification() {
    this.notifyToDoLogin = true;
    console.warn('notification is active');

    setTimeout(() => {
      this.notifyToDoLogin = false;
    }, 3500);

    console.warn('notification is inactive');
  }
}
