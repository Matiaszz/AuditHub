import { Injectable } from '@angular/core';
import { FormType, ToastParams, ToastTypes } from '../../@types/types';

@Injectable({
  providedIn: 'root',
})
export class Utils {
  validateAuth(formType: FormType, form: AuthForm): ToastParams | null {
    const errorTitle =
      formType === FormType.REGISTER ? 'Error during register' : 'Error during login';

    if (formType === FormType.REGISTER) {
      if (!form.firstName?.trim()) {
        return this.warn(errorTitle, 'Please, fill "first name" field.');
      }

      if (!form.lastName?.trim()) {
        return this.warn(errorTitle, 'Please, fill "last name" field.');
      }
    }

    if (!form.email?.trim()) {
      return this.warn(errorTitle, 'Please, fill "email" field.');
    }

    if (!this.isValidEmail(form.email)) {
      return this.warn(errorTitle, 'Please, enter a valid email address.');
    }

    if (!form.password?.trim()) {
      return this.warn(errorTitle, 'Please, fill "password" field.');
    }

    return null;
  }

  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  private warn(title: string, content: string): ToastParams {
    return {
      title,
      content,
      type: ToastTypes.WARN,
    };
  }
}
