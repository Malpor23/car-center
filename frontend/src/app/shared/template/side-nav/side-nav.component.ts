import { Component } from '@angular/core';
import { 
  ROUTES_ADMIN, 
  ROUTES_CLIENTE ,
  ROUTES_MECANICO
} from './side-nav-routes.config';
import { ThemeConstantService } from '../../services/theme-constant.service';
import { IUser } from '../../interfaces/user';
import { Subscription } from 'rxjs';
import { IAppState } from '../../interfaces/app-state';
import { Store } from '@ngrx/store';
import { SideNavInterface } from '../../interfaces/side-nav.type';

@Component({
  selector: 'app-sidenav',
  templateUrl: './side-nav.component.html'
})

export class SideNavComponent {

  ROUTES: SideNavInterface[];
  public menuItems: any[]
  isFolded: boolean;
  isSideNavDark: boolean;
  isExpand: boolean;

  currentUser: IUser;
  userSubscription: Subscription;

  constructor(
    private themeService: ThemeConstantService,
    private store: Store<IAppState>,
  ) { }

  ngOnInit(): void {
    this.userSubscription = this.store.select('user').subscribe(currentUser => {
      this.currentUser = currentUser;
      if (this.currentUser.role === 'ROLE_ADMIN') {
        this.ROUTES = ROUTES_ADMIN;
      } else if (this.currentUser.role === 'ROLE_MECANICO') {
        this.ROUTES = ROUTES_MECANICO;
      } else if (this.currentUser.role === 'ROLE_CLIENTE') {
        this.ROUTES = ROUTES_CLIENTE;
      }
    });
    this.menuItems = this.ROUTES.filter(menuItem => menuItem);
    this.themeService.isMenuFoldedChanges.subscribe(isFolded => this.isFolded = isFolded);
    this.themeService.isExpandChanges.subscribe(isExpand => this.isExpand = isExpand);
    this.themeService.isSideNavDarkChanges.subscribe(isDark => this.isSideNavDark = isDark);
  }

  closeMobileMenu(): void {
    if (window.innerWidth < 992) {
      this.isFolded = false;
      this.isExpand = !this.isExpand;
      this.themeService.toggleExpand(this.isExpand);
      this.themeService.toggleFold(this.isFolded);
    }
  }
  
}
