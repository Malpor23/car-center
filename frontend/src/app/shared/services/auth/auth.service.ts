import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import jwtDecode from 'jwt-decode';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { IAppState } from '../../interfaces/app-state';
import { IUser } from '../../interfaces/user';
import { setUser } from '../../store/actions/user.actions';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public _token: string;

  constructor(
    private http: HttpClient,
    private router: Router,
    private toastr: ToastrService,
    private store: Store<IAppState>,
  ) { }

  public get token(): string {
    if (this._token != null) {
      return this._token;
    } else if (this._token == null && localStorage.getItem('token') != null) {
      this._token = localStorage.getItem('token') as any;
      const decodedToken: any = jwtDecode(this._token);

      if (decodedToken.exp * 1000 < Date.now()) {
        localStorage.removeItem('token');
        this._token = null as any
      }

      return this._token;
    }
    return null as any;
  }

  login(username: string, password: string) {
    this.http.post(environment.apiURL + 'auth', { username, password }).subscribe((data: any) => {
      const user: IUser = {
        id: data['id'],
        nombre: data['nombre'],
        apellido: data['apellido'],
        username: data['username'],
        accessToken: data['accessToken'],
        terceroId: data['terceroId'],
        role: data['role'][0]
      };
      this.store.dispatch(setUser({user}));
      this.guardarToken(data.accessToken);

      this.router.navigate(['dashboard']);

      this.toastr.success(data.nombre + ' ' + data.apellido + ' ha iniciado sesión', 'Bienvenido a Car Center', {
        timeOut: 3000, positionClass: 'toast-top-center'
      });
    },
    err => {
      if (err.error.status == 401) {
        this.toastr.error('Usuario y/o Contraseña incorrecto', 'Error', {
          timeOut: 3000, positionClass: 'toast-top-center',
        });
      }
    });
  }

  guardarToken(accessToken: string): void {
    this._token = accessToken;
    localStorage.setItem('token', accessToken);
  }

  obtenerDatosToken(accessToken: string): any {
    if (accessToken != null) {
      return JSON.parse(atob(accessToken.split('.')[1]));
    }
    return null;
  }

  isAuthenticated(): boolean {
    let payload = this.obtenerDatosToken(this.token);
    if (payload != null) {
      return true;
    }
    return false;
  }
  
}
