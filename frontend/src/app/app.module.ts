import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NZ_I18N, en_US } from 'ng-zorro-antd/i18n';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';

import { registerLocaleData, PathLocationStrategy, LocationStrategy } from '@angular/common';
import en from '@angular/common/locales/en';

import { AppRoutingModule } from './app-routing.module';
import { TemplateModule } from './shared/template/template.module';
import { SharedModule } from './shared/shared.module';

import { AppComponent } from './app.component';
import { CommonLayoutComponent } from './layouts/common-layout';
import { FullLayoutComponent } from './layouts/full-layout';

import { NgChartjsModule } from 'ng-chartjs';
import { ThemeConstantService } from './shared/services/theme-constant.service';
import { StoreModule } from '@ngrx/store';
import { userReducer } from './shared/store/reducers/user.reducer';
import { EffectsArray } from './shared/store/effects';
import { EffectsModule } from '@ngrx/effects';
import { environment } from 'src/environments/environment';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { ToastrModule } from 'ngx-toastr';
import { NgSelectModule } from '@ng-select/ng-select';
import { AuthGuard } from './shared/guard/auth.guard';
import { AuthService } from './shared/services/auth/auth.service';
import { OnlyNumbersDirective } from './shared/directives/only-numbers.directive';
import { OnlyAlphabetsDirective } from './shared/directives/only-alphabets.directive';
import { NgxPermissionsModule } from 'ngx-permissions';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    CommonLayoutComponent,
    FullLayoutComponent,
    OnlyNumbersDirective,
    OnlyAlphabetsDirective,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NzBreadCrumbModule,
    TemplateModule,
    SharedModule,
    NgChartjsModule,
    NgxPermissionsModule.forRoot(),
    ToastrModule.forRoot(),
    StoreModule.forRoot({
      user: userReducer as any,
    }),
    EffectsModule.forRoot(EffectsArray),
    StoreDevtoolsModule.instrument({
      maxAge: 25,
      logOnly: environment.production
    }),
    NgSelectModule,
  ],
  providers: [
    AuthService,
    AuthGuard,
    {
      provide: NZ_I18N,
      useValue: en_US,
    },
    {
      provide: LocationStrategy,
      useClass: PathLocationStrategy
    },
    ThemeConstantService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
