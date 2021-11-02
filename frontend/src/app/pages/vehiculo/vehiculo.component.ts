import { Component, OnDestroy, OnInit } from "@angular/core";
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
  selector: 'app-vehiculo',
  templateUrl: './vehiculo.component.html'
})
export class VehiculoComponent implements OnInit, OnDestroy {

  listVehiculos: any;
  vehi: any;
  newForm: FormGroup;
  currentUser: IUser;
  userSubscription: Subscription;

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
  }

  ngOnInit(): void {
    this.getReload();
    this.httpSv.getList(environment.apiURL + `vehiculos`).subscribe(
      resp => {
        this.vehi = resp.map(item => {
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
    )
  }

  getReload() {
    this.userSubscription = this.store.select('user').subscribe(currentUser => {
      this.currentUser = currentUser;
      this.httpSv.getList(environment.apiURL + `cliente/${this.currentUser.terceroId}`).subscribe(
        resp => {
          this.listVehiculos = resp;
        },
        err => {
          this.toastr.warning(err.mensaje, 'Error!', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
        }
      );
    });
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }

  enableNuevo() {
    this.title = 'Nuevo Vehículo';
    this.validateForm(null);
    this.crud = true;
    this.principal = false;
    this.action = 'Guardar'
  }

  enableEditar(row: any) {
    this.title = 'Editar Vehículo';
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
      vehiculo_id: [(data ? data.vehiculo_id : ''), Validators.required],
      cliente_id: this.currentUser.terceroId
    });
  }

  save(form: FormGroup) {
    if (form.valid) {
      const newData: any = form.value;
      newData.tipoTercero = 2;
      if (!newData.id) {
        this.httpSv.postSave(environment.apiURL + 'cliente-vehiculo', JSON.parse(JSON.stringify(newData))).subscribe(
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
        this.httpSv.putUpdate(environment.apiURL + 'cliente-vehiculo', JSON.parse(JSON.stringify(newData))).subscribe(
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
      text: "Estás seguro de eliminar el registro " + value.vehiculo.marca+' '+value.vehiculo.modelo,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result: any) => {
      if (result.value) {
        this.httpSv.dataDelete(environment.apiURL + 'cliente-vehiculo/'+value.cliente_id+'/'+value.vehiculo_id).subscribe(
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