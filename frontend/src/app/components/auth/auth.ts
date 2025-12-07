import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Button } from '../ui/button/button';

interface AuthForm {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

@Component({
  selector: 'app-auth',
  imports: [FormsModule, Button],
  templateUrl: './auth.html',
  styleUrl: './auth.scss',
})
export class Auth {
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
    if (this.formType === 'login') {
      console.log('Login payload:', this.form);
    } else {
      console.log('Register payload:', this.form);
    }
  }
}
