import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { HttpService } from "src/app/shared/services/http/http.service";
import { environment } from "src/environments/environment";

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html'
})
export class UsuarioComponent implements OnInit {

  newForm: FormGroup;
  listUsuarios: any;
  listTerceros: any;
  listRoles: any;

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
    this.getTerceros();
    this.getRoles();
  }

  getReload() {
    const { pageZise, pageNo } = this.query;
    this.httpSv.getList(environment.apiURL + `usuarios/paginate?pageNo=${pageNo}&pageZise=${pageZise}`).subscribe(
      resp => {
        this.listUsuarios = resp.content;
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

  getTerceros() {
    this.httpSv.getList(environment.apiURL + 'terceros/sin-user').subscribe(
      resp => {
        this.listTerceros = resp.map(item => {
          return {
            label: item.primerNombre+' '+item.primerApellido+' '+item.segundoApellido
              +' - Tipo: '+item.tipoTercero.descripcion,
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

  getRoles() {
    this.httpSv.getList(environment.apiURL + 'roles').subscribe(
      resp => {
        this.listRoles = resp;
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
    this.title = 'Nuevo Usuario';
    this.validateForm(null);
    this.crud = true;
    this.principal = false;
    this.action = 'Guardar'
  }

  enableEditar(row: any) {
    this.title = 'Editar Usuario';
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
      username: [(data ? data.username : ''), Validators.required],
      password: [(data ? data.password : ''), Validators.required],
      enabled: [(data ? data.enabled : '')],
      tercero: [(data ? data.tercero.id : ''), Validators.required],
      role: [(data ? data.role.id : ''), Validators.required],
    });
  }

  save(form: FormGroup) {
    if (form.valid) {
      const newData: any = form.value;
      if (!newData.id) {
        this.httpSv.postSave(environment.apiURL + 'usuarios', JSON.parse(JSON.stringify(newData))).subscribe(
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
        this.httpSv.putUpdate(environment.apiURL + 'usuarios', JSON.parse(JSON.stringify(newData))).subscribe(
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

}