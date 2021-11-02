import { Routes, RouterModule } from '@angular/router';

export const FullLayout_ROUTES: Routes = [
  {
    path: '',
    loadChildren: () => import('../../pages/auth/auth.module').then(m => m.AuthModule)
  }
];