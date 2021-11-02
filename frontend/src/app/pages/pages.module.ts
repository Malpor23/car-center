import { LOCALE_ID, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule, registerLocaleData } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { QuillModule } from 'ngx-quill';
import localeEsCO from '@angular/common/locales/es-CO';

import { ThemeConstantService } from '../shared/services/theme-constant.service';
import { AppsService } from '../shared/services/apps.service';
import { TableService } from '../shared/services/table.service';

import { NzAutocompleteModule } from 'ng-zorro-antd/auto-complete';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzRateModule } from 'ng-zorro-antd/rate';
import { NzBadgeModule } from 'ng-zorro-antd/badge';
import { NzProgressModule } from 'ng-zorro-antd/progress';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzTimelineModule } from 'ng-zorro-antd/timeline';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzListModule } from 'ng-zorro-antd/list';
import { NzCalendarModule } from 'ng-zorro-antd/calendar';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzMessageModule } from 'ng-zorro-antd/message';
import { NzSwitchModule } from 'ng-zorro-antd/switch';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions';

import { ProductosComponent } from './productos';
import { PagesRoutingModule } from './pages-routing.module';
import { ClienteComponent } from './cliente';
import { MecanicoComponent } from './mecanico';
import { SolicitudComponent } from './solicitud';
import { FacturaComponent } from './factura';
import { VehiculoComponent } from './vehiculo';
import { MantenimientoComponent } from './mantenimiento';
import { UsuarioComponent } from './usuario';

const antdModule = [
  NzAutocompleteModule,
  NzButtonModule,
  NzCardModule,
  NzAvatarModule,
  NzRateModule,
  NzBadgeModule,
  NzProgressModule,
  NzRadioModule,
  NzTableModule,
  NzDropDownModule,
  NzTimelineModule,
  NzTabsModule,
  NzTagModule,
  NzListModule,
  NzCalendarModule,
  NzToolTipModule,
  NzFormModule,
  NzModalModule,
  NzSelectModule,
  NzUploadModule,
  NzInputModule,
  NzPaginationModule,
  NzDatePickerModule,
  NzCheckboxModule,
  NzMessageModule,
  NzSwitchModule,
  NzDescriptionsModule,
]

registerLocaleData(localeEsCO, 'es-CO');

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SharedModule,
    PagesRoutingModule,
    QuillModule.forRoot(),
    ...antdModule
  ],
  declarations: [
    ProductosComponent,
    ClienteComponent,
    MecanicoComponent,
    VehiculoComponent,
    SolicitudComponent,
    MantenimientoComponent,
    FacturaComponent,
    UsuarioComponent,
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'es-Co' },
    ThemeConstantService,
    AppsService,
    TableService
  ]
})

export class PagesModule { }