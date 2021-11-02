import { Routes } from '@angular/router';

export const CommonLayout_ROUTES: Routes = [
  {
    path: 'dashboard',
    loadChildren: () => import('../../pages/dashboard/dashboard.module').then(m => m.DashboardModule),
  },
  {
    path: '',
    loadChildren: () => import('../../pages/pages.module').then(m => m.PagesModule),
  }
];