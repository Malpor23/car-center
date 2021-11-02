import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { IAppState } from '../../interfaces/app-state';
import { IUser } from '../../interfaces/user';
import { LogoutService } from '../../services/auth/logout.service';
import { ThemeConstantService } from '../../services/theme-constant.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})

export class HeaderComponent {

  searchVisible: boolean = false;
  quickViewVisible: boolean = false;
  isFolded: boolean;
  isExpand: boolean;

  user: IUser;
  initials: string;
  circleColor: string;

  colors = [
    '#EB7181', '#468547', '#FFD558', '#3670B2',
    '#f56a00', '#7265e6', '#ffbf00', '#00a2ae',
    '#e91e63', '#9c27b0', '#3f51b5', '#00bcd4',
    '#009688', '#cddc39', '#ffc107', '#795548',
    '#4682B4', '#EE82EE', '#FF7F50', '#FFA07A'
  ];

  constructor(
    private themeService: ThemeConstantService,
    private log: LogoutService,
    private store: Store<IAppState>,
  ) { }

  ngOnInit(): void {
    this.themeService.isMenuFoldedChanges.subscribe(isFolded => this.isFolded = isFolded);
    this.themeService.isExpandChanges.subscribe(isExpand => this.isExpand = isExpand);
    this.store.select('user').subscribe(user => {
      this.user = user;
      this.initialName();
    })
    const randomIndex = Math.floor(Math.random() * Math.floor(this.colors.length));
    this.circleColor = this.colors[randomIndex];
  }

  toggleFold() {
    this.isFolded = !this.isFolded;
    this.themeService.toggleFold(this.isFolded);
  }

  toggleExpand() {
    this.isFolded = false;
    this.isExpand = !this.isExpand;
    this.themeService.toggleExpand(this.isExpand);
    this.themeService.toggleFold(this.isFolded);
  }

  logout() {
    this.log.logout();
  }

  searchToggle(): void {
    this.searchVisible = !this.searchVisible;
  }

  quickViewToggle(): void {
    this.quickViewVisible = !this.quickViewVisible;
  }

  private initialName(): void {
    let initial = "";

    const name = this.user.nombre+' '+this.user.apellido;

    for (let i = 0; i < name.length; i++) {
      if (name.charAt(i) === ' ') {
        continue;
      }

      if (name.charAt(i) === name.charAt(i).toUpperCase()) {
        initial += name.charAt(i);
        if (initial.length == 2) {
          break;
        }
      }
    }
    this.initials = initial;
  }
}
