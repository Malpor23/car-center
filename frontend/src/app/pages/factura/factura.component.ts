import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { HttpService } from "src/app/shared/services/http/http.service";
import { environment } from "src/environments/environment";

declare var require: any;
const Swal = require('sweetalert2')

@Component({
  selector: 'app-factura',
  templateUrl: './factura.component.html'
})
export class FacturaComponent implements OnInit {

  listFacturas: any;
  listClientes: any;
  ListMantenimientos: any;
  listProductos: any;
  dataManteniento: any;
  mantemientoSubscription: Subscription;
  newForm: FormGroup;
  tableProductos: any[] = [];

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

  subTotal: number;
  iva: number;
  granTotal: number;
  descuento: number;

  precioServicio: number;
  presupuesto: number;
  totalProductos: number;

  constructor(
    private formBuilder: FormBuilder,
    private httpSv: HttpService,
    private toastr: ToastrService,
  ) {
    this.query = {
      pageNo: 1,
      pageZise: 10,
    };

    this.subTotal = 0;
    this.iva = 0;
    this.granTotal = 0;
    this.descuento = 0;
    this.precioServicio = 0;
    this.presupuesto = 0;
    this.totalProductos = 0;
  }

  ngOnInit(): void {
    this.getReload();
    this.getClientes();
    this.validateForm(null);
  }

  getReload() {
    const { pageZise, pageNo } = this.query;
    this.httpSv.getList(environment.apiURL + `facturas/paginate?page=${pageNo}&pageZise=${pageZise}`).subscribe(
      resp => {
        this.listFacturas = resp.content;
        this.total = resp.totalRegistros;
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
    this.query.pageNo = page;
    this.getReload();
  }

  getClientes() {
    this.httpSv.getList(environment.apiURL + 'clientes').subscribe(
      resp => {
        this.listClientes = resp.map(item => {
          return {
            label: item.n_documento+' - '+item.primerNombre+ ' '+item.primerApellido+ ' '+item.segundoApellido,
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

  enableNuevo() {
    this.title = 'Nueva Factura';
    this.validateForm(null);
    this.getProductos();
    this.crud = true;
    this.principal = false;
    this.action = 'Guardar';
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
      cliente: [(data ? data.cliente : ''), Validators.required],
      mantenimiento: [(data ? data.mantenimiento.id : ''), Validators.required],
      n_mecanico: [(data ? data.n_mecanico : '')],
      product: [(data ? data.product : '')],
    });
  }

  getProductos() {
    this.httpSv.getList(environment.apiURL + 'productos/existencia').subscribe(
      resp => {
        this.listProductos = resp.map(item => {
          return {
            label: item.nombre+' - '+item.precio,
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

  onChange(data) {
    if(data != null) {
      this.httpSv.getList(environment.apiURL + 'mantenimientos/terminados/'+data).subscribe(
        resp => {
          if (resp.length == 0) {
            this.toastr.warning('El cliente no tiene mantemientos en estado terminado', 'Aviso!', {
              timeOut: 3000, positionClass: 'toast-top-center'
            });
            return;
          }
          this.ListMantenimientos = resp.map(item => {
            return {
              label: item.solicitud.servicio.nombre+' - '+item.estado,
              value: item.id,
              mecanico: item.mecanico.primerNombre+ ' '+item.mecanico.primerApellido+ ' '+item.mecanico.segundoApellido
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
  }

  addProduct(data) {
    if (data != null) {
      this.httpSv.getList(environment.apiURL + 'productos/existencia/'+data).subscribe(
        resp => {
          if (resp.existencia <= 0) {
            this.toastr.warning('El producto '+resp.nombre+' no tiene existencias', 'Error!', {
              timeOut: 3000, positionClass: 'toast-top-center'
            });
            return;
          }
  
          const data = {
            producto: resp.id,
            descripcion: resp.nombre,
            precio: resp.precio,
            cantidad: 1,
            total: resp.precio * 1,
            tipo: resp.tipo_producto_id
          }
          
          this.tableProductos.push(data);
          this.calcularTotales();
          this.newForm.controls.product.reset();
  
        },
        err => {
          this.toastr.warning(err.mensaje, 'Error!', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
        }
      )
    }
  }

  onChangeMant(data) {
    if (data != null) {
      this.httpSv.getList(environment.apiURL + 'mantenimiento/'+data).subscribe(
        resp => {
          this.dataManteniento = resp.data;
          this.newForm.controls.n_mecanico.setValue(
            resp.data.mecanico.n_documento+' - '+resp.data.mecanico.primerNombre+' '+
            resp.data.mecanico.primerApellido+' '+resp.data.mecanico.segundoApellido
          );
  
          const data = {
            producto: resp.data.solicitud.servicio.id,
            descripcion: resp.data.solicitud.servicio.nombre,
            precio: resp.data.solicitud.servicio.precio,
            cantidad: 1,
            total: resp.data.solicitud.servicio.precio * 1,
            tipo: resp.data.solicitud.servicio.tipoProducto.id
          }
  
          this.precioServicio = resp.data.solicitud.servicio.precio;
          this.presupuesto = resp.data.solicitud.presupuesto;
          
          this.tableProductos.push(data);
          this.calcularTotales();
  
        },
        err => {
          this.toastr.warning(err.mensaje, 'Error!', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
        }
      )
    }
  }

  deleteItem(id: number): void {
    this.tableProductos = this.tableProductos.filter(item => id !== item.producto);
    this.calcularTotales();
  }

  onCantidad(id: number, event: any){
    let cantidad: number = event.target.value as number;

    if (cantidad == 0) {
      this.toastr.warning('La cantidad debe ser mayor a 0 ', 'Aviso!', {
        timeOut: 3000, positionClass: 'toast-top-center'
      });
      return;
    }

    this.tableProductos.forEach(item => {
      if (id === item.producto) {
        item.cantidad = cantidad;
        item.total = item.precio * cantidad;
      }
    })
     this.calcularTotales();
  }

  calcularTotales() {
    this.subTotal = 0;
    this.descuento = 0;
    this.tableProductos.forEach(item => {
      this.subTotal += item.total
      if (item.tipo === 1) {
        this.totalProductos += item.precio * item.cantidad;
      }
    });

    this.totalProductos >= 3000000 ? 
      this.descuento = Math.round(((this.precioServicio * 50) / 100)) :
        this.descuento = 0;
    
    this.iva = Math.round(((this.subTotal * 19) / 100));
    this.granTotal = Math.round(((this.subTotal + this.iva) - this.descuento));
  }

  save(form: FormGroup) {
    if (form.valid) {
      const newData: any = form.value;
      if (!newData.id) {
        if(this.presupuesto > 0) {
          if (this.granTotal > this.presupuesto) {
            this.toastr.warning('El total de factura supera el presupuesto estipulado por el cliente', 'Error!', {
              timeOut: 3000, positionClass: 'toast-top-center'
            });
            return;
          }
        }
        newData.subtotal = this.subTotal,
        newData.iva = this.iva,
        newData.descuento = this.descuento,
        newData.total = this.granTotal
        newData.items = this.tableProductos.map(item => {
          return {
            producto: item.producto,
            cantidad: item.cantidad,
            precio: item.precio
          };
        });
        console.log(newData)
        this.httpSv.postSave(environment.apiURL + 'facturas', JSON.parse(JSON.stringify(newData))).subscribe(
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
        
      }
    }
    this.enableCerrar();
    this.newForm.reset();
  }

  
}