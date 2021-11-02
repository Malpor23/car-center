package com.app.controller;

import com.app.model.dao.ProductoDao;
import com.app.model.entity.Producto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ProductoController {
    
    @Autowired
    private ProductoDao dao;
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("productos/all")
    public List<Producto> getAll() {
        return dao.findAll();
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("productos")
    public List<Producto> getProductos() {
        return dao.getProductos();
    }

    @Secured({"ROLE_ADMIN", "ROLE_MECANICO", "ROLE_CLIENTE"})
    @GetMapping("servicios")
    public List<Producto> getServicios() {
        return dao.getServicios();
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/productos/paginate")
    public Page<Producto> getPaginate(
        @RequestParam Integer pageNo,
        @RequestParam Integer pageZise
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.findAll(pageable);
    }
    
    @Secured({"ROLE_ADMIN"})
    @PostMapping("productos")
    public ResponseEntity<?> save(@Valid @RequestBody Producto prod, BindingResult result) {
        Producto newData = null;
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
            newData = dao.saveAndFlush(prod);
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
    
    @Secured({"ROLE_ADMIN"})
    @PutMapping("productos")
    public ResponseEntity<?> editar(@Valid @RequestBody Producto prod, BindingResult result) {
        Producto newData = null;
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
            if (prod.getId() == null) {
                response.put("mensaje", "Error al realizar la actualización base de datos Se requiere ID");
                response.put("error", "Se requiere ID");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            newData = dao.saveAndFlush(prod);
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
    
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/producto/{id}")
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
