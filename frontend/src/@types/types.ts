export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  isEnabled: boolean;
  role: 'COMMON' | 'ADMIN';
  createdAt: Date;
  updatedAt: Date;
}

export interface ToastParams {
  title: string;
  content: string;
  type: ToastTypes;
}

export enum ToastTypes {
  SUCCESS = 'success',
  ERROR = 'error',
  INFO = 'info',
  WARN = 'warn',
}

export enum FormType {
  REGISTER = 'register',
  LOGIN = 'login',
}
