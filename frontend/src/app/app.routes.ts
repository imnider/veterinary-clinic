import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { guestGuard } from './core/guards/guest.guard';
import { MainLayout } from './core/layouts/main-layout/main-layout';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'splash',
  },
  {
    path: 'splash',
    canActivate: [guestGuard],
    loadComponent: () => import('./features/pages/splash/splash').then((m) => m.Splash),
  },
  {
    path: 'login',
    canActivate: [guestGuard],
    loadComponent: () => import('./features/pages/login/login').then((m) => m.Login),
  },
  {
    path: '',
    component: MainLayout,
    canActivate: [authGuard],
    children: [
      {
        path: 'home',
        loadComponent: () => import('./features/pages/home/home').then((m) => m.Home),
      },
      {
        path: 'pets',
        loadComponent: () =>
          import('./features/pages/pet/pet-list/pet-list').then((m) => m.PetListComponent),
      },
      {
        path: 'pets/:id',
        loadComponent: () =>
          import('./features/pages/pet/pet-detail/pet-detail').then((m) => m.PetDetailComponent),
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'splash',
  },
];
