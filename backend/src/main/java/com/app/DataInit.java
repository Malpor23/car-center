package com.app;

import com.app.model.dao.*;
import com.app.model.entity.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Manuel Gomez
 */
@Component
public class DataInit implements CommandLineRunner {
    
    @Autowired
    TipoTerceroDao tipoTerceroDao;

    @Autowired
    TipoDocumentoDao tipoDocumentoDao;
    
    @Autowired
    TipoProductoDao tipoProductoDao;
    
    @Autowired
    TerceroDao terceroDao;
    
    @Autowired
    ProductoDao productoDao;
    
    @Autowired
    VehiculoDao vehiculoDao;
    
    @Autowired
    TiendaDao tiendaDao;
    
    @Autowired
    InventarioDao inventarioDao;
    
    @Autowired
    RoleDao roleDao;
    
    @Autowired
    UsuarioDao usuarioDao;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    
    private void loadData() {        
        if (tiendaDao.count() == 0) {
            for (int i = 1; i <= 8; i++) {
                Tienda ti = new Tienda("123456789-"+i, "Car Center Sede "+i);
                tiendaDao.save(ti);
            }
        }
        
        if (tipoTerceroDao.count() == 0) {
            TipoTercero tipoT1 = new TipoTercero("Cliente");
            TipoTercero tipoT2 = new TipoTercero("Mecánico");
            TipoTercero tipoT3 = new TipoTercero("Administrador");
            tipoTerceroDao.save(tipoT1);
            tipoTerceroDao.save(tipoT2);
            tipoTerceroDao.save(tipoT3);
        }
        
        if (tipoDocumentoDao.count() == 0) {
            TipoDocumento tipo1 = new TipoDocumento("CC", "Cedula de ciudadania");
            TipoDocumento tipo2 = new TipoDocumento("NIT", "Nit");
            TipoDocumento tipo3 = new TipoDocumento("CE", "Cedula de extranjeria");
            tipoDocumentoDao.save(tipo1);
            tipoDocumentoDao.save(tipo2);
            tipoDocumentoDao.save(tipo3);
        }
        
        if (tipoProductoDao.count() == 0) {
            TipoProducto tipoT1 = new TipoProducto("Producto");
            TipoProducto tipoT2 = new TipoProducto("Servicio");
            tipoProductoDao.save(tipoT1);
            tipoProductoDao.save(tipoT2);
        }
        
        if (roleDao.count() == 0) {
            Role rol1 = new Role("ROLE_CLIENTE", "Cliente");
            Role rol2 = new Role("ROLE_MECANICO", "Mecánico");
            Role rol3 = new Role("ROLE_ADMIN", "Administrador");
            roleDao.save(rol1);
            roleDao.save(rol2);
            roleDao.save(rol3);
        }
        
        if (terceroDao.count() == 0) {
            Optional<TipoDocumento> tipoDoc = tipoDocumentoDao.findById("CC");
            Optional<TipoTercero> terClient = tipoTerceroDao.findById(Long.valueOf(1));
            Optional<TipoTercero> terMecanic = tipoTerceroDao.findById(Long.valueOf(2));
            Optional<TipoTercero> terAdmin = tipoTerceroDao.findById(Long.valueOf(3));
            
            Tercero ter = new Tercero();
            ter.setPrimerNombre("Manuel");
            ter.setSegundoNombre("Andres");
            ter.setPrimerApellido("Gomez");
            ter.setSegundoApellido("Leon");
            ter.setTipoDocumento(tipoDoc.get());
            ter.setTipoTercero(terAdmin.get());
            ter.setCelular("3008005903");
            ter.setDireccion("Cr xx # xx - xx");
            ter.setEmail("mg230886@gmail.com");
            ter.setEstado(Boolean.TRUE);
            ter.setN_documento("1234567890");
            terceroDao.save(ter);
            
            Tercero ter2 = new Tercero();
            ter2.setPrimerNombre("Ana");
            ter2.setSegundoNombre("Maria");
            ter2.setPrimerApellido("Hoyos");
            ter2.setSegundoApellido("Lopez");
            ter2.setTipoDocumento(tipoDoc.get());
            ter2.setTipoTercero(terClient.get());
            ter2.setCelular("0000000000");
            ter2.setDireccion("Cr xx # xx - xx");
            ter2.setEmail("ana@user.com");
            ter2.setEstado(Boolean.TRUE);
            ter2.setN_documento("0987654321");
            terceroDao.save(ter2);
            
            Tercero ter3 = new Tercero();
            ter3.setPrimerNombre("Pedro");
            ter3.setSegundoNombre("Luis");
            ter3.setPrimerApellido("Lara");
            ter3.setSegundoApellido("Quiñonez");
            ter3.setTipoDocumento(tipoDoc.get());
            ter3.setTipoTercero(terMecanic.get());
            ter3.setCelular("1111111111");
            ter3.setDireccion("Cr xx # xx - xx");
            ter3.setEmail("pedro@user.com");
            ter3.setEstado(Boolean.TRUE);
            ter3.setN_documento("0987123456");
            terceroDao.save(ter3);
        }
        
        if (usuarioDao.count() == 0) {
            Optional<Tercero> tercer1 = terceroDao.findById(Long.valueOf(1));
            Optional<Tercero> tercer2 = terceroDao.findById(Long.valueOf(2));
            Optional<Tercero> tercer3 = terceroDao.findById(Long.valueOf(3));
            Optional<Role> rolClient = roleDao.findById(Long.valueOf(1));
            Optional<Role> rolMecanic = roleDao.findById(Long.valueOf(2));
            Optional<Role> rolAdmin = roleDao.findById(Long.valueOf(3));
            
            Usuario user1 = new Usuario();
            user1.setUsername("user-admin");
            user1.setPassword(passwordEncoder.encode("admin123"));
            user1.setEnabled(Boolean.TRUE);
            user1.setTercero(tercer1.get());
            user1.setRole(rolAdmin.get());
            usuarioDao.save(user1);
            
            Usuario user2 = new Usuario();
            user2.setUsername("user-client");
            user2.setPassword(passwordEncoder.encode("client123"));
            user2.setEnabled(Boolean.TRUE);
            user2.setTercero(tercer2.get());
            user2.setRole(rolClient.get());
            usuarioDao.save(user2);
            
            Usuario user3 = new Usuario();
            user3.setUsername("user-mecanic");
            user3.setPassword(passwordEncoder.encode("mecanic123"));
            user3.setEnabled(Boolean.TRUE);
            user3.setTercero(tercer3.get());
            user3.setRole(rolMecanic.get());
            usuarioDao.save(user3);
        }
        
        if (productoDao.count() == 0) {
            saveProducto(productoDao);
        }
        
        if (vehiculoDao.count() == 0) {
            saveVehiculo(vehiculoDao);
        }
        
        if (inventarioDao.count() == 0) {
            for (int i = 1; i < 25; i++) {
                jdbcTemplate.execute(
                    "INSERT INTO inventarios(existencia, producto_id) VALUES (200, " + i + ");"
                );
            }
        }
        
    }
    
    private Producto createProducto(String nombre, Double valorMinimo, Double valorMaximo, Double precio, TipoProducto tipoProducto){
        Producto pro = new Producto();
        pro.setNombre(nombre.toUpperCase());
        pro.setValorMinimo(valorMinimo);
        pro.setValorMaximo(valorMaximo);
        pro.setPrecio(precio);
        pro.setTipoProducto(tipoProducto);
        return pro;
    }
    
    private void saveProducto(ProductoDao dao) {
        Optional<TipoProducto> prod = tipoProductoDao.findById(Long.valueOf(1));
        Optional<TipoProducto> serv = tipoProductoDao.findById(Long.valueOf(2));
        //Productos
        dao.save(createProducto("Correa de Distribución", 0.0, 0.0, 15000.0, prod.get()));
        dao.save(createProducto("Funda para asientos", 0.0, 0.0, 95000.0, prod.get()));
        dao.save(createProducto("Parasoles", 0.0, 0.0, 25000.0, prod.get()));
        dao.save(createProducto("Fundas para volantes", 0.0, 0.0, 9500.0, prod.get()));
        dao.save(createProducto("Neumáticos para furgonetas", 0.0, 0.0, 45000.0, prod.get()));
        dao.save(createProducto("Neumáticos para 4×4", 0.0, 0.0, 32590.0, prod.get()));
        dao.save(createProducto("Llanta power", 0.0, 0.0, 535900.0, prod.get()));
        dao.save(createProducto("Set de dos llantas Road", 0.0, 0.0, 860700.0, prod.get()));
        dao.save(createProducto("Calibrador de presión", 0.0, 0.0, 12300.0, prod.get()));
        dao.save(createProducto("Sellador llantas con neumaticos", 0.0, 0.0, 32500.0, prod.get()));
        dao.save(createProducto("Tapa valvula para neumaticos", 0.0, 0.0, 25000.0, prod.get()));
        dao.save(createProducto("compresor inflador para llantas", 0.0, 0.0, 61900.0, prod.get()));
        dao.save(createProducto("Centro rin tapa Hiunday", 0.0, 0.0, 19600.0, prod.get()));
        dao.save(createProducto("Forro timon en pvc", 0.0, 0.0, 23990.0, prod.get()));
        dao.save(createProducto("Set cubre volante + cubre cinturon", 0.0, 0.0, 70000.0, prod.get()));
        dao.save(createProducto("Cubierta o teja para lluvia espejo retrovisor", 0.0, 0.0, 19900.0, prod.get()));
        dao.save(createProducto("Kit embellecimiento automotriz", 0.0, 0.0, 72000.0, prod.get()));
        dao.save(createProducto("Bolsa para basura de vehiculo", 0.0, 0.0, 21400.0, prod.get()));
        dao.save(createProducto("Perfume concentrado para carro", 0.0, 0.0, 48000.0, prod.get()));
        dao.save(createProducto("Gti shampoo con cera", 0.0, 0.0, 25000.0, prod.get()));
        dao.save(createProducto("Balsamo renovador de cuero para autos", 0.0, 0.0, 14900.0, prod.get()));
        dao.save(createProducto("Protector de llaves Renault Logan", 0.0, 0.0, 46000.0, prod.get()));
        dao.save(createProducto("Antena mini de aluminio", 0.0, 0.0, 39000.0, prod.get()));
        dao.save(createProducto("Cubierta pedales lujo para carro", 0.0, 0.0, 60000.0, prod.get()));
        //Servicios
        dao.save(createProducto("Cambio de aceite y filtro", 74500.0, 94900.0, 94900.0, serv.get()));
        dao.save(createProducto("Cambio filtro combustible", 37000.0, 45500.0, 45500.0, serv.get()));
        dao.save(createProducto("Cambio bujias", 60000.0, 82800.0, 82800.0, serv.get()));
        dao.save(createProducto("Cambio de aceite de caja", 90400.0, 113700.0, 113700.0, serv.get()));
        dao.save(createProducto("limpieza de inyectores", 46500.0, 56500.0, 56500.0, serv.get()));
        dao.save(createProducto("ABC Frenos", 300000.0, 357800.0, 357800.0, serv.get()));
        dao.save(createProducto("Cambio de banda de distribucion", 280000.0, 320000.0, 320000.0, serv.get()));
        dao.save(createProducto("Lavado express", 74500.0, 94900.0, 0.0, serv.get()));
    }
    
    private Vehiculo createVehiculo(String marca, String modelo) {
        Vehiculo veh = new Vehiculo();
        veh.setMarca(marca.toUpperCase());
        veh.setModelo(modelo.toUpperCase());
        return veh;
    }
    
    private void saveVehiculo(VehiculoDao dao) {
        dao.save(createVehiculo("mazda", "xc-5"));
        dao.save(createVehiculo("nissan", "versa"));
        dao.save(createVehiculo("kia", "rio"));
        dao.save(createVehiculo("nissan", "np300"));
        dao.save(createVehiculo("nissan", "march"));
        dao.save(createVehiculo("kia", "picanto"));
        dao.save(createVehiculo("mazda", "3"));
        dao.save(createVehiculo("renault", "duster"));
        dao.save(createVehiculo("chevrolet", "tracker"));
        dao.save(createVehiculo("toyota", "fortuner"));
        dao.save(createVehiculo("toyota", "prado"));
        dao.save(createVehiculo("suzuki", "vitara"));
        dao.save(createVehiculo("renault", "captur"));
        dao.save(createVehiculo("ford", "eco-sport"));
        dao.save(createVehiculo("nissan", "kicks"));
        dao.save(createVehiculo("ford", "scape"));
        dao.save(createVehiculo("nissan", "qashgai"));
    }
    
}
