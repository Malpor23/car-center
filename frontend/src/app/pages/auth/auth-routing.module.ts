import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login';
import { SignUpComponent } from './sign-up';

import { Error1Component } from './error-1/error-1.component';
import { Error2Component } from './error-2/error-2.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Acceder'
    }
  },
  {
    path: 'registrar',
    component: SignUpComponent,
    data: {
      title: 'Registrar'
    }
  },
  {
    path: 'error-1',
    component: Error1Component,
    data: {
      title: 'Error 1'
    }
  },
  {
    path: 'error-2',
    component: Error2Component,
    data: {
      title: 'Error 2'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule { }
