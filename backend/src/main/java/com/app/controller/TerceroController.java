package com.app.controller;

import com.app.model.dao.TerceroDao;
import com.app.model.dao.UsuarioDao;
import com.app.model.entity.Tercero;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class TerceroController {
    
    @Autowired
    private TerceroDao dao;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    UsuarioDao usuarioDao;
   
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO", "ROLE_CLIENTE"})
    @GetMapping("clientes")
    public List<Tercero> getClientes() {
        return dao.getClientes();
    }

    @Secured({"ROLE_ADMIN", "ROLE_MECANICO", "ROLE_CLIENTE"})
    @GetMapping("mecanicos")
    public List<Tercero> getMacanicos() {
        return dao.getMecanicos();
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("clientes/paginate")
    public Page<Tercero> getPaginate(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.findAllClientes(pageable);
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("mecanicos/paginate")
    public Page<Tercero> getPaginateMecanicos(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.findAllMecanicos(pageable);
    }
    
    @Secured({"ROLE_ADMIN"})
    @PostMapping("terceros")
    public ResponseEntity<?> save(@Valid @RequestBody Tercero tercero, BindingResult result) {
        Tercero newData = null;
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
            newData = dao.saveAndFlush(tercero);
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
    @PutMapping("terceros")
    public ResponseEntity<?> edit(@Valid @RequestBody Tercero tercero, BindingResult result) {
        Tercero newData = null;
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
            if (tercero.getId() == null) {
                response.put("mensaje", "Error al realizar la actualización base de datos");
                response.put("error", "Se requiere ID");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            newData = dao.saveAndFlush(tercero);
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
    @DeleteMapping("tercero/{id}")
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
    
    @Secured("ROLE_ADMIN")
    @GetMapping("tercero/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id) {
        Optional<Tercero> ter = null;
        Map<String, Object> response = new HashMap<>();

        try {
            ter = dao.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el registro de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success", 1);
        response.put("data", ter.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("terceros/sin-user")
    public List<Tercero> getSinUser() {
        return dao.getSinUser();
    }
    
    
}
