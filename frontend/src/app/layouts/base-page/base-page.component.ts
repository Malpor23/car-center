import { Component, OnDestroy, OnInit } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { Observable } from "rxjs";
import { HttpService } from "src/app/shared/services/http/http.service";


@Component({
  selector: 'base-page',
  templateUrl: './base-page.component.html'
})
export class BasePageComponent implements OnInit, OnDestroy {

  constructor(
    public httpSv: HttpService,
    public toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
  }

  ngOnDestroy() {
  }

  getList(url: string, dataName: string, callbackFnName?: string): Observable<any> {
    this.httpSv.getList(url).subscribe(
      data => {
        this[dataName] = data;
      },
      err => {
        this.toastr.error(err.mensaje ? err.mensaje : 'Se ha perdido la conexión con la base de datos.\n comuniquese con el administrador.',
          err.error ? err.error : 'Conexión Fallida!', {
          timeOut: 3000, positionClass: 'toast-top-center',
        });
      },
      () => {
        (callbackFnName && typeof this[callbackFnName] === 'function') ? this[callbackFnName](this[dataName]) : null;
      }
    );
    return;
  }

  postSave(url: string, data: string, callbackFnName?: string): Observable<any> {
    this.httpSv.postSave(url, data).subscribe(
      response => {
        this.toastr.success(response.mensaje, 'Exito!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      },
      err => {
        this.toastr.error(err.mensaje ? err.mensaje : 'Se ha perdido la conexión con la base de datos.\n comuniquese con el administrador.',
          err.error ? err.error : 'Conexión Fallida!', {
          timeOut: 3000, positionClass: 'toast-top-center',
        });
      },
      () => {
        // tslint:disable-next-line:no-unused-expression
        (callbackFnName && typeof this[callbackFnName] === 'function') ? this[callbackFnName](this[data]) : null;
      }
    );
    return;
  }

  putUpdate(url: string, data: string, callbackFnName?: string): Observable<any> {
    this.httpSv.putUpdate(url, data).subscribe(
      response => {
        this.toastr.success(response.mensaje, 'Exito!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      },
      err => {
        this.toastr.error(err.mensaje ? err.mensaje : 'Se ha perdido la conexión con la base de datos.\n comuniquese con el administrador.',
          err.error ? err.error : 'Conexión Fallida!', {
          timeOut: 3000, positionClass: 'toast-top-center',
        });
      },
      () => {
        // tslint:disable-next-line:no-unused-expression
        (callbackFnName && typeof this[callbackFnName] === 'function') ? this[callbackFnName](this[data]) : null;
      }
    );
    return;
  }

  dataDelete(url: string, data: string, callbackFnName?: string): Observable<any> {
    this.httpSv.dataDelete(url).subscribe(
      response => {
        this.toastr.success(response.mensaje, 'Exito!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      },
      err => {
        this.toastr.error(err.mensaje ? err.mensaje : 'Se ha perdido la conexión con la base de datos.\n comuniquese con el administrador.',
          err.error ? err.error : 'Conexión Fallida!', {
          timeOut: 3000, positionClass: 'toast-top-center',
        });
      },
      () => {
        (callbackFnName && typeof this[callbackFnName] === 'function') ? this[callbackFnName](this[data]) : null;
      }
    );
    return;
  }

}