package com.app.controller;

import com.app.model.dao.TiendaDao;
import com.app.model.dao.TipoDocumentoDao;
import com.app.model.dao.TipoProductoDao;
import com.app.model.dao.VehiculoDao;
import com.app.model.entity.Tienda;
import com.app.model.entity.TipoDocumento;
import com.app.model.entity.TipoProducto;
import com.app.model.entity.Vehiculo;
import com.app.util.dto.ClienteVehiculoDTO;
import com.app.util.dto.dao.ClienteVehiculoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manuel Gomez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class LoadController {
    
    @Autowired
    TipoProductoDao tipoDao;
    
    @Autowired
    TipoDocumentoDao tipoDocDao;
    
    @Autowired
    TiendaDao tiendaDao;
    
    @Autowired
    VehiculoDao vehiculoDao;
    
    @Autowired
    ClienteVehiculoService service;
    
    @GetMapping("tipo_producto")
    public List<TipoProducto> getTipoProducto() {
        return tipoDao.findAll();
    }
    
    @GetMapping("tipo_documento")
    public List<TipoDocumento> getTipoDocumento() {
        return tipoDocDao.findAll();
    }
    
    @GetMapping("tiendas")
    public List<Tienda> getTienda() {
        return tiendaDao.findAll();
    }
    
    @GetMapping("vehiculos")
    public List<Vehiculo> getVehiculo() {
        return vehiculoDao.findAll();
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_ADMIN"})
    @PostMapping("cliente-vehiculo")
    public ResponseEntity<?> saveCliVeh(@Valid @RequestBody ClienteVehiculoDTO dto, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.saveOrUpdate(dto);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success", 1);
        response.put("mensaje", "Registro actualizado satisfactoriamente!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_ADMIN"})
    @PutMapping("cliente-vehiculo")
    public ResponseEntity<?> editCliVeh(@Valid @RequestBody ClienteVehiculoDTO dto, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.saveOrUpdate(dto);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("success", 1);
        response.put("mensaje", "Registro guardado satisfactoriamente!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_ADMIN"})
    @DeleteMapping("cliente-vehiculo/{cliente_id}/{vehiculo_id}")
    public ResponseEntity<?> delete(@PathVariable Long cliente_id, @PathVariable Long vehiculo_id) {
        Map<String, Object> response = new HashMap<>();

            try {
                service.delete(cliente_id, vehiculo_id);
            } catch (DataAccessException e) {
                response.put("mensaje", "Error al realizar el insert en la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        response.put("success", 1);
        response.put("mensaje", "Registro eliminado satisfactoriamente!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_CLIENTE"})
    @GetMapping("cliente/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            return new ResponseEntity<>(service.findAllCliente(id), HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
