import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { HttpService } from 'src/app/shared/services/http/http.service';
import { environment } from 'src/environments/environment';

declare var require: any;
const Swal = require('sweetalert2')

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html'
})
export class ProductosComponent implements OnInit {

  listProductos: any;
  tipoProd: any;
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
  tipoSubscription: Subscription;
  servicio: boolean = false;

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
    this.httpSv.getList(environment.apiURL + 'tipo_producto').subscribe(
      resp => {
        this.tipoProd = resp;
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
    this.httpSv.getList(environment.apiURL + `productos/paginate?pageNo=${pageNo}&pageZise=${pageZise}`).subscribe(
      resp => {
        this.listProductos = resp.content;
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
    this.title = 'Nuevo Producto';
    this.validateForm(null);
    this.crud = true;
    this.principal = false;
    this.action = 'Guardar'
    this.handleSubscriptions();
  }

  enableEditar(row: any) {
    this.title = 'Editar Producto';
    this.crud = true;
    this.principal = false;
    this.validateForm(row);
    this.action = 'Actualizar'
    this.handleSubscriptions();
  }

  enableCerrar(){
    this.crud = false;
    this.principal = true;
    this.title = '';
    this.action = '';
    this.tipoSubscription.unsubscribe();
  }

  validateForm(data: any) {
    this.newForm = this.formBuilder.group({
      id: [(data ? data.id : '')],
      tipoProducto: [(data ? data.tipoProducto.id : ''), Validators.required],
      nombre: [(data ? data.nombre : ''), Validators.required],
      precio: [(data ? data.precio : ''), Validators.required],
      valorMaximo: [(data ? data.valorMaximo : '')],
      valorMinimo: [(data ? data.valorMinimo : '')],
    });
  }

  handleSubscriptions() {
    this.validateDataEdit();
    this.tipoSubscription = this.newForm.get('tipoProducto').valueChanges.subscribe(value => {
      if (value === 2) {
        this.servicio = true;
        this.newForm.controls.valorMaximo.setValidators(Validators.required);
        this.newForm.controls.valorMinimo.setValidators(Validators.required);
      }
      this.newForm.controls.valorMaximo.updateValueAndValidity();
      this.newForm.controls.valorMinimo.updateValueAndValidity();
    });
  }

  validateDataEdit() {
    const tipoProdvalue = this.newForm.get('tipoProducto').value;
    if (tipoProdvalue === 2) {
      this.servicio = true;
      this.newForm.controls.valorMaximo.setValidators(Validators.required);
      this.newForm.controls.valorMinimo.setValidators(Validators.required);
    }
    this.newForm.controls.valorMaximo.updateValueAndValidity();
    this.newForm.controls.valorMinimo.updateValueAndValidity();
  }

  save(form: FormGroup) {
    if (form.valid) {
      const newData: any = form.value;
      if (newData.tipoProducto == 1) {
        newData.valorMaximo = 0.00;
        newData.valorMinimo = 0.00;
      } else {
        newData.precio = 0.00;
      }
      if (!newData.id) {
        this.httpSv.postSave(environment.apiURL + 'productos', JSON.parse(JSON.stringify(newData))).subscribe(
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
        this.httpSv.putUpdate(environment.apiURL + 'productos', JSON.parse(JSON.stringify(newData))).subscribe(
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
      text: "Estás seguro de eliminar el registro " + value.nombre,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result: any) => {
      if (result.value) {
        this.httpSv.dataDelete(environment.apiURL + 'producto/' + value.id).subscribe(
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
