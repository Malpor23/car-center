import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { IUser } from '../../interfaces/user';
import { IAppState } from '../../interfaces/app-state';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  private user: IUser;

  constructor(
    private store: Store<IAppState>,
    private auth: AuthService,
    private http: HttpClient,
    private toastr: ToastrService,
    private router: Router,
  ) {

    this.store.select('user').subscribe(user => this.user = user);
  }

  logout() {
    this.http.get(environment.apiURL + `logout/${this.user.id}`).subscribe(async (data: any) => {
      if (data == 'OK') {
        this.toastr.success('Se ha cerrado la sesión', 'Car Center', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.auth._token = null as any;
        localStorage.removeItem('token');
        await this.router.navigate(['login']).then(() => {
          window.location.reload();
        });
      }
    },
      err => err);
  }
}
