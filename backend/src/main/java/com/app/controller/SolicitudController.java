package com.app.controller;

import com.app.model.dao.ProductoDao;
import com.app.model.dao.SolicitudDao;
import com.app.model.entity.Producto;
import com.app.model.entity.Solicitud;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manuel Gomez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class SolicitudController {
    
    @Autowired
    SolicitudDao dao;
    
    @Autowired
    ProductoDao productDao;
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO", "ROLE_CLIENTE"})
    @GetMapping("solicitudes")
    public List<Solicitud> getAll() {
        return dao.findAll();
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("solicitudes/en-espera")
    public List<Solicitud> getEnSolicitud() {
        return dao.getEnSolicitud();
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO", "ROLE_CLIENTE"})
    @GetMapping("solicitudes/paginate")
    public Page<Solicitud> getPaginate(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.findPaginate(pageable);
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO", "ROLE_CLIENTE"})
    @GetMapping("solicitudes/paginate/cliente")
    public Page<Solicitud> getPaginateByCliente(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise,
        @RequestParam Long cliente_id
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.findPaginateByClienteId(cliente_id, pageable);
    }
    
    @Secured({"ROLE_CLIENTE"})
    @PostMapping("solicitudes")
    public ResponseEntity<?> save(@Valid @RequestBody Solicitud sol, BindingResult result) {
        Solicitud newData = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                .collect(Collectors.toList());

            response.put("mensaje", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<Producto> pro = productDao.findById(sol.getServicio().getId());
            sol.setServicio(pro.get());
            newData = dao.saveAndFlush(sol);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success", 1);
        response.put("mensaje", "Registro guardado satisfactoriamente!");
        response.put("data", newData);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_CLIENTE"})
    @PutMapping("solicitudes")
    public ResponseEntity<?> editar(@Valid @RequestBody Solicitud sol, BindingResult result) {
        Solicitud newData = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            if (sol.getId() == null) {
                response.put("mensaje", "Error al realizar la actualización base de datos Se requiere ID");
                response.put("error", "Se requiere ID");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            newData = dao.saveAndFlush(sol);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success", 1);
        response.put("mensaje", "Registro actualizado satisfactoriamente!");
        response.put("data", newData);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @Secured("ROLE_CLIENTE")
    @DeleteMapping("solicitud/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            dao.deleteById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el registro de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success", 1);
        response.put("mensaje", "El registro fue eliminado satisfactoriamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
