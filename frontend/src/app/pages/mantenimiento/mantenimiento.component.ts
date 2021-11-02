import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Store } from "@ngrx/store";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { IAppState } from "src/app/shared/interfaces/app-state";
import { IUser } from "src/app/shared/interfaces/user";
import { HttpService } from "src/app/shared/services/http/http.service";
import { environment } from "src/environments/environment";

declare var require: any;
const Swal = require('sweetalert2')

@Component({
  selector: 'app-mantenimiento',
  templateUrl: './mantenimiento.component.html'
})
export class MantenimientoComponent implements OnInit {

  listMantenimientos: any;
  listSolicitudes: any;
  newForm: FormGroup;
  currentUser: IUser;
  userSubscription: Subscription

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
    private store: Store<IAppState>,
  ) {
    this.query = {
      pageNo: 0,
      pageZise: 10,
    };
  }

  ngOnInit(): void { 
    this.getReload();
    this.getSolicitudes();
  }

  getReload() {
    this.userSubscription = this.store.select('user').subscribe(currentUser => {
      this.currentUser = currentUser;

      const { pageZise, pageNo } = this.query;
      this.httpSv.getList(environment.apiURL + `mantenimientos/paginate/mecanico?pageNo=${pageNo}&pageZise=${pageZise}&mecanico_id=${this.currentUser.terceroId}`).subscribe(
        resp => {
          this.listMantenimientos = resp.content;
          this.total = resp.totalElements;
          this.size = resp.size;
        },
        err => {
          this.toastr.warning(err.mensaje, 'Error!', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
        }
      );
    });
  }

  getPage(page: any) {
    this.query.pageNo = page - 1;
    this.getReload();
  }

  enableNuevo() {
    this.title = 'Nuevo Mantenimiento';
    this.validateForm(null);
    this.crud = true;
    this.principal = false;
    this.action = 'Guardar'
  }

  enableEditar(row: any) {
    this.title = 'Editar Mantenimiento';
    this.crud = true;
    this.principal = false;
    this.validateForm(row);
    this.action = 'Actualizar';
  }

  enableCerrar(){
    this.crud = false;
    this.principal = true;
    this.title = '';
    this.action = '';
  }

  getSolicitudes(){
    this.httpSv.getList(environment.apiURL + 'solicitudes/en-espera').subscribe(
      resp => {
        this.listSolicitudes = resp.map(item => {
          return {
            label: item.clienteVehiculo.cliente.primerNombre + ' ' + 
                    item.clienteVehiculo.cliente.primerApellido + ' ' +
                    item.clienteVehiculo.cliente.segundoApellido + ' - ' +
                    item.servicio.nombre,
            value: item.id
          };
        });
      },
      err => {
        this.toastr.warning(err.mensaje, 'Error!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    )
  }

  validateForm(data: any) {
    this.newForm = this.formBuilder.group({
      id: [(data ? data.id : '')],
      solicitud: [(data ? data.solicitud.id : ''), Validators.required],
      estado: [(data ? data.estado : ''), Validators.required],
      descripcion: [(data ? data.descripcion : ''), Validators.required],
      observacion: [(data ? data.observacion : '')],
      mecanico: this.currentUser.terceroId
    });
  }

  save(form: FormGroup) {
    if (form.valid) {
      const newData: any = form.value;
      newData.facturado = false;
      if (!newData.id) {
        this.httpSv.postSave(environment.apiURL + 'mantenimientos', JSON.parse(JSON.stringify(newData))).subscribe(
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
        this.httpSv.putUpdate(environment.apiURL + 'mantenimientos', JSON.parse(JSON.stringify(newData))).subscribe(
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
      text: "Estás seguro de eliminar esta mantemiento?",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result: any) => {
      if (result.value) {
        this.httpSv.dataDelete(environment.apiURL + 'mantenimiento/' + value.id).subscribe(
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