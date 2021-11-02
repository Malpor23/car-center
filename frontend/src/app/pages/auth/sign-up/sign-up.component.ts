import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';


@Component({
  templateUrl: './sign-up.component.html'
})

export class SignUpComponent {

  newForm: FormGroup;
  tipoDoc: any;
  data: any;

  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private toastr: ToastrService,
    private router: Router,
  ) {
    this.tipoDoc = [];
  }

  ngOnInit(): void {
    this.validateForm(null);
    this.http.get(environment.apiURL + 'tipo_documento').subscribe(
      resp => {
        this.tipoDoc = resp;
      },
      err => {
        this.toastr.warning(err.mensaje, 'Error!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
  }

  validateForm(data: any) {
    this.newForm = this.formBuilder.group({
      primerNombre: [(data ? data.primerNombre : ''), Validators.required],
      segundoNombre: [(data ? data.segundoNombre : '')],
      primerApellido: [(data ? data.primerApellido : ''), Validators.required],
      segundoApellido: [(data ? data.segundoApellido : ''), Validators.required],
      tipoDocumento: [(data ? data.tipoDocumento.id : ''), Validators.required],
      n_documento: [(data ? data.n_documento : ''), Validators.required],
      celular: [(data ? data.celular : ''), Validators.required],
      direccion: [(data ? data.direccion : ''), Validators.required],
      email: [(data ? data.email: ''), [Validators.required, Validators.email]],
      username: [(data ? data.username : ''), Validators.required],
      password: [(data ? data.password : ''), Validators.required]
    });
  }

  save(form: FormGroup) {
    const newData: any = form.value;
    this.http.post(environment.apiURL + 'registrar', JSON.parse(JSON.stringify(newData))).subscribe(
      resp => {
        this.data = resp;
        if (this.data.success === 1) {
          this.toastr.success(this.data.mensaje, 'Exito!', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
        }
        this.router.navigate(['login']);
      },
      err => {
        this.toastr.error(err.mensaje, 'Error!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
    this.newForm.reset();
  }

}