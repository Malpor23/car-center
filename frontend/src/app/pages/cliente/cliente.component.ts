import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { HttpService } from "src/app/shared/services/http/http.service";
import { environment } from "src/environments/environment";

declare var require: any;
const Swal = require('sweetalert2')

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html'
})
export class ClienteComponent implements OnInit {

  listClientes: any;
  tipoDocumento: any;
  newForm: FormGroup;

  total: number = 0;
  size: number = 0;
  query: {
    pageNo: number,
    pageZise: number
  }

  principal: boolean = true;
  crud: boolean = false;
  title: string;
  action: string;

  constructor(
    private formBuilder: FormBuilder,
    private httpSv: HttpService,
    private toastr: ToastrService,
  ) {
    this.query = {
      pageNo: 0,
      pageZise: 10,
    };
  }

  ngOnInit(): void {        
    this.getReload();
    this.httpSv.getList(environment.apiURL + 'tipo_documento').subscribe(
      resp => {
        this.tipoDocumento = resp;
      },
      err => {
        this.toastr.warning(err.mensaje, 'Error!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
  }

  getReload() {
    const { pageZise, pageNo } = this.query;
    this.httpSv.getList(environment.apiURL + `clientes/paginate?pageNo=${pageNo}&pageZise=${pageZise}`).subscribe(
      resp => {
        this.listClientes = resp.content;
        this.total = resp.totalElements;
        this.size = resp.size;
      },
      err => {
        this.toastr.warning(err.mensaje, 'Error!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
  }

  getPage(page: any) {
    this.query.pageNo = page - 1;
    this.getReload();
  }

  enableNuevo() {
    this.title = 'Nuevo Cliente';
    this.validateForm(null);
    this.crud = true;
    this.principal = false;
    this.action = 'Guardar'
  }

  enableEditar(row: any) {
    this.title = 'Editar Cliente';
    this.crud = true;
    this.principal = false;
    this.validateForm(row);
    this.action = 'Actualizar'
  }

  enableCerrar(){
    this.crud = false;
    this.principal = true;
    this.title = '';
    this.action = '';
  }

  validateForm(data: any) {
    this.newForm = this.formBuilder.group({
      id: [(data ? data.id : '')],
      primerNombre: [(data ? data.primerNombre : ''), Validators.required],
      segundoNombre: [(data ? data.segundoNombre : '')],
      primerApellido: [(data ? data.primerApellido : ''), Validators.required],
      segundoApellido: [(data ? data.segundoApellido : ''), Validators.required],
      tipoDocumento: [(data ? data.tipoDocumento.id : ''), Validators.required],
      n_documento: [(data ? data.n_documento : ''), Validators.required],
      celular: [(data ? data.celular : ''), Validators.required],
      direccion: [(data ? data.direccion : ''), Validators.required],
      email: [(data ? data.email: ''), [Validators.required, Validators.email]],
    });
  }

  save(form: FormGroup) {
    if (form.valid) {
      const newData: any = form.value;
      newData.tipoTercero = 1;
      if (!newData.id) {
        this.httpSv.postSave(environment.apiURL + 'terceros', JSON.parse(JSON.stringify(newData))).subscribe(
          resp => {
            if (resp.success == 1) {
              this.toastr.success(resp.mensaje, 'Exito!', {
                timeOut: 3000, positionClass: 'toast-top-center'
              });
              this.getReload();
            }
          },
          err => {
            this.toastr.error(err.mensaje, 'Error!', {
              timeOut: 3000, positionClass: 'toast-top-center'
            });
          }
        );
      } else {
        this.httpSv.putUpdate(environment.apiURL + 'terceros', JSON.parse(JSON.stringify(newData))).subscribe(
          resp => {
            if (resp.success == 1) {
              this.toastr.success(resp.mensaje, 'Exito!', {
                timeOut: 3000, positionClass: 'toast-top-center'
              });
              this.getReload();
            }
          },
          err => {
            this.toastr.error(err.mensaje, 'Error!', {
              timeOut: 3000, positionClass: 'toast-top-center'
            });
          }
        );
      }
    }
    this.enableCerrar();
    this.newForm.reset();
  }

  delete(value: any) {
    Swal.fire({
      title: 'Eliminar',
      text: "Estás seguro de eliminar el registro " + value.primerNombre+' '+value.primerApellido,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result: any) => {
      if (result.value) {
        this.httpSv.dataDelete(environment.apiURL + 'tercero/' + value.id).subscribe(
          resp => {
            if (resp.success == 1) {
              this.toastr.success(resp.mensaje, 'Exito!', {
                timeOut: 3000, positionClass: 'toast-top-center'
              });
              this.getReload();
            }
          },
          err => {
            this.toastr.warning(err.mensaje, 'Error!', {
              timeOut: 3000, positionClass: 'toast-top-center'
            });
          }
        );
      }
    })
  }

}