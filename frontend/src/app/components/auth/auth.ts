import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { Button } from '../ui/button/button';

import { AuthService } from '../../services/auth-service';
import { Utils } from '../../services/utils';
import { ToastService } from '../../services/toast-service';

import { FormType, ToastTypes, User } from '../../../@types/types';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule, Button],
  templateUrl: './auth.html',
  styleUrl: './auth.scss',
})
export class Auth {
  constructor(
    private authService: AuthService,
    private router: Router,
    private utils: Utils,
    private toastService: ToastService
  ) {}

  user: User | null = null;

  formType: FormType = FormType.REGISTER;

  readonly defaultFormValue: AuthForm = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  form: AuthForm = { ...this.defaultFormValue };

  switchFormType() {
    this.formType = this.formType === FormType.REGISTER ? FormType.LOGIN : FormType.REGISTER;

    this.form = { ...this.defaultFormValue };
  }

  onSubmit() {
    const error = this.utils.validateAuth(this.formType, this.form);

    if (error) {
      this.toastService.show(error);
      return;
    }

    this.authService.postAuth(this.form, this.formType).subscribe({
      next: (res) => {
        if (res.status === 204) {
          this.authService.loadUser();
          this.router.navigate(['/painel']);
        }
      },
      error: (res) => this.handleAuthError(res),
    });
  }

  private handleAuthError(res: any) {
    if (res.status === 403) {
      this.toastService.show({
        title: 'Error during login',
        type: ToastTypes.ERROR,
        content: 'Invalid credentials.',
      });
      return;
    }

    if (res.status === 409) {
      this.toastService.show({
        title: 'Error during registering',
        type: ToastTypes.ERROR,
        content: 'This email already has an account.',
      });
      return;
    }

    this.toastService.show({
      title: 'Unexpected error',
      type: ToastTypes.ERROR,
      content: 'Something went wrong.',
    });

    console.error('Error during auth request:', res);
  }
}
