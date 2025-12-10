import { Routes } from '@angular/router';
import { Auth } from './components/auth/auth';
import { Painel } from './components/painel/painel';

export const routes: Routes = [
  {
    path: 'auth',
    component: Auth,
  },
  {
    path: 'painel',
    component: Painel,
  },
];
