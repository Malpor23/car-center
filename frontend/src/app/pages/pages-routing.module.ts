import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProductosComponent } from './productos';
import { ClienteComponent } from './cliente';
import { MecanicoComponent } from './mecanico';
import { SolicitudComponent } from './solicitud';
import { FacturaComponent } from './factura';
import { VehiculoComponent } from './vehiculo';
import { MantenimientoComponent } from './mantenimiento';
import { UsuarioComponent } from './usuario';

const routes: Routes = [
  {
    path: 'productos',
    component: ProductosComponent,
    data: {
      title: 'Productos ',
    }
  },
  {
    path: 'clientes',
    component: ClienteComponent,
    data: {
      title: 'Clientes',
    }
  },
  {
    path: 'mecanicos',
    component: MecanicoComponent,
    data: {
      title: 'Mecanicos',
    }
  },
  {
    path: 'mis-vehiculos',
    component: VehiculoComponent,
    data: {
      title: 'Mis Vehículos',
    }
  },
  {
    path: 'solicitud-servicio',
    component: SolicitudComponent,
    data: {
      title: 'Solicitud de Servicio',
    }
  },
  {
    path: 'matenimientos',
    component: MantenimientoComponent,
    data: {
      title: 'Mantenimientos',
    }
  },
  {
    path: 'factura',
    component: FacturaComponent,
    data: {
      title: 'Factura',
    }
  },
  {
    path: 'usuarios',
    component: UsuarioComponent,
    data: {
      title: 'Usuarios',
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule { }
