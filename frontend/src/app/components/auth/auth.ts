import { Component, NgZone } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Button } from '../ui/button/button';
import { AuthService } from '../../services/auth-service';
import { FormType, ToastParams, ToastTypes, User } from '../../../@types/types';
import { Router } from '@angular/router';
import { Toast } from '../ui/toast/toast';
import { Utils } from '../../services/utils';

@Component({
  selector: 'app-auth',
  imports: [FormsModule, Button, Toast],
  templateUrl: './auth.html',
  styleUrl: './auth.scss',
})
export class Auth {
  constructor(
    private authService: AuthService,
    private router: Router,
    private ngZone: NgZone,
    private utils: Utils
  ) {}

  user: User | null = null;
  showToast: boolean = false;

  formType: FormType = FormType.REGISTER;
  defaultFormValue: AuthForm = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  form: AuthForm = this.defaultFormValue;

  toastParams: ToastParams = {
    title: '',
    content: '',
    type: ToastTypes.INFO,
  };

  switchFormType() {
    this.formType = this.formType === FormType.REGISTER ? FormType.LOGIN : FormType.LOGIN;
    this.form = this.defaultFormValue;
  }

  showToastMessage(params: ToastParams) {
    this.ngZone.run(() => {
      this.toastParams = params;
      this.showToast = true;

      setTimeout(() => {
        this.showToast = false;
      }, 5000);
    });
  }

  onSubmit() {
    const hasErrors = this.utils.validateAuth(this.formType, this.form);
    if (hasErrors) {
      return this.showToastMessage(hasErrors);
    }
    this.authService.postAuth(this.form, this.formType).subscribe({
      next: (res) => {
        if (res.status === 204) {
          this.authService.loadUser();
          this.router.navigate(['/painel']);
          return;
        }
      },
      error: (res) => {
        if (res.status === 403) {
          this.showToastMessage({
            title: 'Error during login',
            type: ToastTypes.ERROR,
            content: 'Invalid credentials.',
          });
          return;
        }

        if (res.status === 409) {
          this.showToastMessage({
            title: 'Error during registering',
            type: ToastTypes.ERROR,
            content: 'This email already have an account.',
          });
          return;
        }

        this.showToastMessage({
          title: 'Unexpected error',
          type: ToastTypes.ERROR,
          content: 'Something went wrong.',
        });

        console.error('Error during auth request: ', res, res.status);
      },
    });
  }
}
