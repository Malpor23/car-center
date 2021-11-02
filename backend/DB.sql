CREATE DATABASEcar_center

USE car_center;

CREATE TABLE tipo_documento (
  id varchar(3) COLLATE utf8mb4_spanish_ci NOT NULL,
  descripcion varchar(30) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

insert  into tipo_documento(id,descripcion) values 
('CC','Cedula de ciudadania'),
('CE','Cedula de extranjeria'),
('NIT','Nit');


CREATE TABLE tipo_producto (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  descripcion varchar(30) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


insert  into tipo_producto(id,descripcion) values 
(1,'Producto'),
(2,'Servicio');


CREATE TABLE tipo_tercero (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  descripcion varchar(30) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


insert  into tipo_tercero(id,descripcion) values 
(1,'Cliente'),
(2,'Mecánico'),
(3,'Administrador');


CREATE TABLE productos_servicios (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  nombre varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
  precio decimal(16,2) DEFAULT 0.00,
  valor_maximo decimal(16,2) DEFAULT 0.00,
  valor_minimo decimal(16,2) DEFAULT 0.00,
  tipo_producto_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_productos_tipo_producto_id (tipo_producto_id),
  CONSTRAINT fk_productos_tipo_producto_id FOREIGN KEY (tipo_producto_id) REFERENCES tipo_producto (id)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


insert  into productos_servicios(id,nombre,precio,valor_maximo,valor_minimo,tipo_producto_id) values 
(1,'CORREA DE DISTRIBUCIÓN',15000.00,0.00,0.00,1),
(2,'FUNDA PARA ASIENTOS',95000.00,0.00,0.00,1),
(3,'PARASOLES',25000.00,0.00,0.00,1),
(4,'FUNDAS PARA VOLANTES',9500.00,0.00,0.00,1),
(5,'NEUMÁTICOS PARA FURGONETAS',45000.00,0.00,0.00,1),
(6,'NEUMÁTICOS PARA 4×4',32590.00,0.00,0.00,1),
(7,'LLANTA POWER',535900.00,0.00,0.00,1),
(8,'SET DE DOS LLANTAS ROAD',860700.00,0.00,0.00,1),
(9,'CALIBRADOR DE PRESIÓN',12300.00,0.00,0.00,1),
(10,'SELLADOR LLANTAS CON NEUMATICOS',32500.00,0.00,0.00,1),
(11,'TAPA VALVULA PARA NEUMATICOS',25000.00,0.00,0.00,1),
(12,'COMPRESOR INFLADOR PARA LLANTAS',61900.00,0.00,0.00,1),
(13,'CENTRO RIN TAPA HIUNDAY',19600.00,0.00,0.00,1),
(14,'FORRO TIMON EN PVC',23990.00,0.00,0.00,1),
(15,'SET CUBRE VOLANTE + CUBRE CINTURON',70000.00,0.00,0.00,1),
(16,'CUBIERTA O TEJA PARA LLUVIA ESPEJO RETROVISOR',19900.00,0.00,0.00,1),
(17,'KIT EMBELLECIMIENTO AUTOMOTRIZ',72000.00,0.00,0.00,1),
(18,'BOLSA PARA BASURA DE VEHICULO',21400.00,0.00,0.00,1),
(19,'PERFUME CONCENTRADO PARA CARRO',48000.00,0.00,0.00,1),
(20,'GTI SHAMPOO CON CERA',25000.00,0.00,0.00,1),
(21,'BALSAMO RENOVADOR DE CUERO PARA AUTOS',14900.00,0.00,0.00,1),
(22,'PROTECTOR DE LLAVES RENAULT LOGAN',46000.00,0.00,0.00,1),
(23,'ANTENA MINI DE ALUMINIO',39000.00,0.00,0.00,1),
(24,'CUBIERTA PEDALES LUJO PARA CARRO',60000.00,0.00,0.00,1),
(25,'CAMBIO DE ACEITE Y FILTRO',94900.00,94900.00,74500.00,2),
(26,'CAMBIO FILTRO COMBUSTIBLE',45500.00,45500.00,37000.00,2),
(27,'CAMBIO BUJIAS',82800.00,82800.00,60000.00,2),
(28,'CAMBIO DE ACEITE DE CAJA',113700.00,113700.00,90400.00,2),
(29,'LIMPIEZA DE INYECTORES',56500.00,56500.00,46500.00,2),
(30,'ABC FRENOS',357800.00,357800.00,300000.00,2),
(31,'CAMBIO DE BANDA DE DISTRIBUCION',320000.00,320000.00,280000.00,2),
(32,'LAVADO EXPRESS',0.00,94900.00,74500.00,2);


CREATE TABLE terceros (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  celular varchar(15) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  direccion varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  email varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  estado tinyint(1) NOT NULL DEFAULT 1,
  n_documento varchar(25) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  primer_apellido varchar(40) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  primer_nombre varchar(40) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  segundo_apellido varchar(40) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  segundo_nombre varchar(40) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  tipo_documento_id varchar(3) COLLATE utf8mb4_spanish_ci NOT NULL,
  tipo_tercero_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_terceros_tipo_documento_id (tipo_documento_id),
  KEY fk_terceros_tipo_tercero_id (tipo_tercero_id),
  CONSTRAINT fk_terceros_tipo_documento_id FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento (id),
  CONSTRAINT fk_terceros_tipo_tercero_id FOREIGN KEY (tipo_tercero_id) REFERENCES tipo_tercero (id)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


insert  into terceros(id,celular,direccion,email,estado,n_documento,primer_apellido,primer_nombre,segundo_apellido,segundo_nombre,tipo_documento_id,tipo_tercero_id) values 
(1,'3008005903','Cr xx # xx - xx','mg230886@gmail.com',1,'1234567890','GOMEZ','MANUEL','LEON','ANDRES','CC',3),
(2,'0000000000','Cr xx # xx - xx','ana@user.com',1,'0987654321','HOYOS','ANA','LOPEZ','MARIA','CC',1),
(3,'1111111111','Cr xx # xx - xx','pedro@user.com',1,'0987123456','LARA','PEDRO','QUIÑONEZ','LUIS','CC',2),
(4,'234785','Cll xx cr xx - xx','juan@user.com',1,'888877765','MARTINEZ','JUAN','GONZZALES','CARLOS','CE',1),
(5,'38247237','xx xx xxx xxxxx','marcos@user.com',1,'8786746324','GUEVARA','MARCOS','HERNANDEZ','','CC',2);


CREATE TABLE tiendas (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  nit varchar(25) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  razon_social varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


insert  into tiendas(id,nit,razon_social) values 
(1,'123456789-1','Car Center Sede 1'),
(2,'123456789-2','Car Center Sede 2'),
(3,'123456789-3','Car Center Sede 3'),
(4,'123456789-4','Car Center Sede 4'),
(5,'123456789-5','Car Center Sede 5'),
(6,'123456789-6','Car Center Sede 6'),
(7,'123456789-7','Car Center Sede 7'),
(8,'123456789-8','Car Center Sede 8');


CREATE TABLE vehiculos (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  marca varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  modelo varchar(30) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


insert  into vehiculos(id,marca,modelo) values 
(1,'MAZDA','XC-5'),
(2,'NISSAN','VERSA'),
(3,'KIA','RIO'),
(4,'NISSAN','NP300'),
(5,'NISSAN','MARCH'),
(6,'KIA','PICANTO'),
(7,'MAZDA','3'),
(8,'RENAULT','DUSTER'),
(9,'CHEVROLET','TRACKER'),
(10,'TOYOTA','FORTUNER'),
(11,'TOYOTA','PRADO'),
(12,'SUZUKI','VITARA'),
(13,'RENAULT','CAPTUR'),
(14,'FORD','ECO-SPORT'),
(15,'NISSAN','KICKS'),
(16,'FORD','SCAPE'),
(17,'NISSAN','QASHGAI');


CREATE TABLE cliente_vehiculo (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  cliente_id bigint(20) DEFAULT NULL,
  vehiculo_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKjd4mag5aq28qhtukx5ob1hybt (cliente_id),
  KEY FK2ufsrlqafc9q4ww208bq2ejpq (vehiculo_id),
  CONSTRAINT FK2ufsrlqafc9q4ww208bq2ejpq FOREIGN KEY (vehiculo_id) REFERENCES vehiculos (id),
  CONSTRAINT FKjd4mag5aq28qhtukx5ob1hybt FOREIGN KEY (cliente_id) REFERENCES terceros (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

CREATE TABLE solicitud_mantenimiento (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_at datetime(6) DEFAULT NULL,
  estado varchar(15) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  fecha_servicio date DEFAULT NULL,
  observacion longtext COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  presupuesto decimal(16,2) DEFAULT 0.00,
  cliente_vehiculo_id bigint(20) NOT NULL,
  servicio_id bigint(20) NOT NULL,
  tienda_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_solicitud_cliente_vehiculo_id (cliente_vehiculo_id),
  KEY fk_solicitud_servicio_id (servicio_id),
  KEY fk_mantenimientos_tienda_id (tienda_id),
  CONSTRAINT fk_mantenimientos_tienda_id FOREIGN KEY (tienda_id) REFERENCES tiendas (id),
  CONSTRAINT fk_solicitud_cliente_vehiculo_id FOREIGN KEY (cliente_vehiculo_id) REFERENCES cliente_vehiculo (id),
  CONSTRAINT fk_solicitud_servicio_id FOREIGN KEY (servicio_id) REFERENCES productos_servicios (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


CREATE TABLE facturas (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_at datetime(6) DEFAULT NULL,
  descuento decimal(16,2) NOT NULL DEFAULT 0.00,
  iva decimal(16,2) NOT NULL DEFAULT 0.00,
  subtotal decimal(16,2) NOT NULL DEFAULT 0.00,
  total decimal(16,2) NOT NULL DEFAULT 0.00,
  mantenimiento_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_terceros_mantenimiento_id (mantenimiento_id),
  CONSTRAINT fk_terceros_mantenimiento_id FOREIGN KEY (mantenimiento_id) REFERENCES mantenimientos (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


CREATE TABLE facturas_items (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  cantidad int(11) DEFAULT NULL,
  precio decimal(16,2) DEFAULT 0.00,
  factura_id bigint(20) DEFAULT NULL,
  producto_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_factura_items_factura_id (factura_id),
  KEY fk_tfactura_items_producto_id (producto_id),
  CONSTRAINT fk_factura_items_factura_id FOREIGN KEY (factura_id) REFERENCES facturas (id),
  CONSTRAINT fk_tfactura_items_producto_id FOREIGN KEY (producto_id) REFERENCES productos_servicios (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


CREATE TABLE inventarios (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  existencia int(11) NOT NULL,
  producto_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_terceros_producto_id (producto_id),
  CONSTRAINT fk_terceros_producto_id FOREIGN KEY (producto_id) REFERENCES productos_servicios (id)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

insert  into inventarios(id,existencia,producto_id) values 
(1,200,1),
(2,200,2),
(3,200,3),
(4,200,4),
(5,200,5),
(6,200,6),
(7,200,7),
(8,200,8),
(9,200,9),
(10,200,10),
(11,200,11),
(12,200,12),
(13,200,13),
(14,200,14),
(15,200,15),
(16,200,16),
(17,200,17),
(18,200,18),
(19,200,19),
(20,200,20),
(21,200,21),
(22,200,22),
(23,200,23),
(24,200,24);

CREATE TABLE mantenimientos (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_at datetime(6) DEFAULT NULL,
  descripcion varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  estado varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  facturado tinyint(1) DEFAULT 0,
  observacion varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  mecanico_id bigint(20) NOT NULL,
  solicitud_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_mantenimientos_mecanico_id (mecanico_id),
  KEY fk_mantenimientos_solicitud_id (solicitud_id),
  CONSTRAINT fk_mantenimientos_mecanico_id FOREIGN KEY (mecanico_id) REFERENCES terceros (id),
  CONSTRAINT fk_mantenimientos_solicitud_id FOREIGN KEY (solicitud_id) REFERENCES solicitud_mantenimiento (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


CREATE TABLE roles (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  descripcion varchar(15) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  nombre varchar(15) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_ldv0v52e0udsh2h1rs0r0gw1n (nombre)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

insert  into roles(id,descripcion,nombre) values 
(1,'Cliente','ROLE_CLIENTE'),
(2,'Mecánico','ROLE_MECANICO'),
(3,'Administrador','ROLE_ADMIN');


CREATE TABLE usuarios (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  enabled bit(1) DEFAULT NULL,
  password varchar(150) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  username varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  role_id bigint(20) NOT NULL,
  tercero_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_m2dvbwfge291euvmk6vkkocao (username),
  KEY fk_usuarios_roles_role_id (role_id),
  KEY fk_usuarios_terceros_tercero_id (tercero_id),
  CONSTRAINT fk_usuarios_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT fk_usuarios_terceros_tercero_id FOREIGN KEY (tercero_id) REFERENCES terceros (id)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


insert  into usuarios(id,enabled,password,username,role_id,tercero_id) values 
(1,'','$2a$10$RmE5KP65AT8DkFtY3j1xJefGjofoRkAU0lZn0pLB5fjDgasiXdp4u','user-admin',3,1),
(2,'','$2a$10$8nCGwSfHvPUgZDQOqHOQBeC4khGgi1GnzAeRrS0xiR7b4/e85zLy6','user-client',1,2),
(3,'','$2a$10$bVC5rbVM/.SLFHO/YTPNQOQIBO4gXWqZF/WX2xS..nmhvIl2F8d96','user-mecanic',2,3),
(4,'','$2a$10$N2Fx8dAjQ4bNAhSDSlYSqOlMqKSKvLAPjExtnStZ/ObZXlHrGkHP6','juan',1,4),
(6,'','$2a$10$O5nxvkjF7jl0MkNiPmZFh.RCHMjCOvMpGAHXla7jmY3YIxgn/6VIy','marcos',2,5);
