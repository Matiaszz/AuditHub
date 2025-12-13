import { Injectable } from '@angular/core';
import { FormType, ToastParams, ToastTypes } from '../../@types/types';

@Injectable({
  providedIn: 'root',
})
export class Utils {
  validateAuth(formType: FormType, form: AuthForm): ToastParams | null {
    const errorTitle = formType === 'register' ? 'Error during register' : 'Error during login';

    if (formType === 'register') {
      if (!form.firstName?.trim()) {
        return {
          title: errorTitle,
          type: ToastTypes.WARN,
          content: 'Please, fill "first name" field.',
        };
      }

      if (!form.lastName?.trim()) {
        return {
          title: errorTitle,
          type: ToastTypes.WARN,
          content: 'Please, fill "last name" field.',
        };
      }
    }

    if (!form.email?.trim()) {
      return {
        title: errorTitle,
        type: ToastTypes.WARN,
        content: 'Please, fill "email" field.',
      };
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(form.email)) {
      return {
        title: errorTitle,
        type: ToastTypes.WARN,
        content: 'Please, enter a valid email address.',
      };
    }

    if (!form.password?.trim()) {
      return {
        title: errorTitle,
        type: ToastTypes.WARN,
        content: 'Please, fill "password" field.',
      };
    }

    return null;
  }
}
