import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { IAppState } from './shared/interfaces/app-state';
import { IUser } from './shared/interfaces/user';
import { AuthService } from './shared/services/auth/auth.service';
import { LogoutService } from './shared/services/auth/logout.service';
import { HttpService } from './shared/services/http/http.service';
import { setUser } from './shared/store/actions/user.actions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  constructor(
    private router: Router,
    private httpSv: HttpService,
    private store: Store<IAppState>,
    private auth: AuthService,
    private logoutSv: LogoutService,
  ) {
  }

  ngOnInit(): void {
    if (this.auth.isAuthenticated()) {
      this.getUserData();
    }
  }

  getUserData() {
    this.httpSv.getUserInfo().subscribe((data: any) => {
      const user: IUser = {
        id: data['id'],
        nombre: data['tercero']['primerNombre'],
        apellido: data['tercero']['primerApellido'],
        username: data['username'],
        accessToken: data['accessToken'],
        terceroId: data['tercero']['id'],
        role: data['role']['nombre']
      };

      this.store.dispatch(setUser({ user }));
    }, error => {
      this.logoutSv.logout();
      this.router.navigate(['login']);
      console.log(error);
    });
  }

}
