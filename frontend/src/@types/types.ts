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
