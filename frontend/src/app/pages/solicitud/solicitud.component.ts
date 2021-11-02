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
  selector: 'app-solicitud',
  templateUrl: './solicitud.component.html'
})
export class SolicitudComponent implements OnInit {

  listSolicitudes: any;
  listVehiculos: any;
  listServicios: any;
  listTienda: any;
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
    this.getVehiculos();
    this.getServicios();
    this.getTiendas();
  }

  getReload() {
    this.userSubscription = this.store.select('user').subscribe(currentUser => {
      this.currentUser = currentUser;

      const { pageZise, pageNo } = this.query;
      this.httpSv.getList(environment.apiURL + `solicitudes/paginate/cliente?pageNo=${pageNo}&pageZise=${pageZise}&cliente_id=${this.currentUser.terceroId}`).subscribe(
        resp => {
          this.listSolicitudes = resp.content;
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

  getVehiculos() {
    this.httpSv.getList(environment.apiURL + 'vehiculo/cliente/'+this.currentUser.terceroId).subscribe(
      resp => {
        this.listVehiculos = resp.map(item => {
          return {
            label: item.marca + ' - ' + item.modelo,
            value: item.id
          };
        });
      },
      err => {
        this.toastr.warning(err.mensaje, 'Error!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
  }

  getServicios() {
    this.httpSv.getList(environment.apiURL + 'servicios').subscribe(
      resp => {
        this.listServicios = resp;
      },
      err => {
        this.toastr.warning(err.mensaje, 'Error!', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
  }

  getTiendas() {
    this.httpSv.getList(environment.apiURL + 'tiendas').subscribe(
      resp => {
        this.listTienda = resp;
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
    this.title = 'Nueva Solicitud';
    this.validateForm(null);
    this.crud = true;
    this.principal = false;
    this.action = 'Guardar'
  }

  enableEditar(row: any) {
    this.title = 'Editar Solicitud';
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

  validateForm(data: any) {
    this.newForm = this.formBuilder.group({
      id: [(data ? data.id : '')],
      clienteVehiculo: [(data ? data.clienteVehiculo.id : ''), Validators.required],
      servicio: [(data ? data.servicio.id : ''), Validators.required],
      presupuesto: [(data ? data.presupuesto : '')],
      observacion: [(data ? data.observacion : '')],
      fechaServicio: [(data ? data.fechaServicio : ''), Validators.required],
      tienda: [(data ? data.tienda.id : ''), Validators.required],
    });
  }

  save(form: FormGroup) {
    if (form.valid) {
      const newData: any = form.value;
      newData.estado = 'En solicitud'
      if (!newData.id) {
        this.httpSv.postSave(environment.apiURL + 'solicitudes', JSON.parse(JSON.stringify(newData))).subscribe(
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
        this.httpSv.putUpdate(environment.apiURL + 'solicitudes', JSON.parse(JSON.stringify(newData))).subscribe(
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
      text: "Estás seguro de eliminar esta solicitud?",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result: any) => {
      if (result.value) {
        this.httpSv.dataDelete(environment.apiURL + 'solicitud/' + value.id).subscribe(
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