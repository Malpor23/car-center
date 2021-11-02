import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError as observableThrowError } from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(
    private http: HttpClient, 
  ) { }

  getList(source: string): Observable<any> {
    return this.http.get(source, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      })
    }).pipe(
      tap((res: any) => res),
      catchError(this.handleError)
    );
  }

  postSave(source: string, data: string): Observable<any> {
    return this.http.post(source, data, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      })
    }).pipe(
      tap((res: any) => res),
      catchError(this.handleError)
    );
  }

  putUpdate(source: string, data: string): Observable<any> {
    return this.http.put(source, data, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      })
    }).pipe(
      tap((res: any) => res),
      catchError(this.handleError)
    );
  }

  dataDelete(source: string): Observable<any> {
    return this.http.delete(source, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      })
    }).pipe(
      tap((res: any) => res),
      catchError(this.handleError)
    );
  }

  getUserInfo() {
    return this.http.get(environment.apiURL + 'usuario/info', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      })
    }).pipe(
      tap((user => user))
    )
  }

  private handleError(error: any) {
    return observableThrowError(error.error || 'Server error');
  }

}
