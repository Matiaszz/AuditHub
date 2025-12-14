import { Routes } from '@angular/router';
import { Auth } from './components/auth/auth';
import { Dashboard } from './components/dashboard/dashboard';
import { Toast } from './components/ui/toast/toast';

export const routes: Routes = [
  {
    path: 'auth',
    component: Auth,
  },
  {
    path: 'painel',
    component: Dashboard,
  },
  {
    path: 'toast',
    component: Toast,
  },
];
