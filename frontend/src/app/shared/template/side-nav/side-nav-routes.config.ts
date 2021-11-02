import { SideNavInterface } from '../../interfaces/side-nav.type';

export const ROUTES_ADMIN: SideNavInterface[] = [
  {
    path: '',
    title: 'Dashboard',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'dashboard',
    submenu: [],
  },
  {
    path: 'productos',
    title: 'Productos / Servicios',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'barcode',
    submenu: []
  },
  {
    path: 'clientes',
    title: 'Clientes',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'usergroup-add',
    submenu: []
  },
  {
    path: 'mecanicos',
    title: 'Mecanicos',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'team',
    submenu: []
  },
  {
    path: 'factura',
    title: 'Facturas',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'file-text',
    submenu: []
  },
  {
    path: 'usuarios',
    title: 'Usuarios',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'user',
    submenu: []
  }
]

export const ROUTES_CLIENTE: SideNavInterface[] = [
  {
    path: '',
    title: 'Dashboard',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'dashboard',
    submenu: [],
  },
  {
    path: 'mis-vehiculos',
    title: 'Mis Vehículos',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'car',
    submenu: []
  },
  {
    path: 'solicitud-servicio',
    title: 'Solicitud de Servicio',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'solution',
    submenu: []
  },
]

export const ROUTES_MECANICO: SideNavInterface[] = [
  {
    path: '',
    title: 'Dashboard',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'dashboard',
    submenu: [],
  },
  {
    path: 'productos',
    title: 'Productos / Servicios',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'barcode',
    submenu: []
  },
  {
    path: 'clientes',
    title: 'Clientes',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'usergroup-add',
    submenu: []
  },
  {
    path: 'matenimientos',
    title: 'Mantenimientos',
    iconType: 'nzIcon',
    iconTheme: 'outline',
    icon: 'setting',
    submenu: []
  },
]