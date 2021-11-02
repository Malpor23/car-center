package com.app.controller;

import com.app.model.dao.MantenimientoDao;
import com.app.model.dao.TerceroDao;
import com.app.model.entity.Mantenimiento;
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
public class MantenimientoController {
    
    @Autowired
    MantenimientoDao dao;
    
    @Autowired
    TerceroDao terceroDao;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("mantenimientos/terminados")
    public List<Mantenimiento> getTerminados() {
        return dao.getTerminados();
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("mantenimientos/terminados/{cliente_id}")
    public List<Mantenimiento> getTerminados(@PathVariable Long cliente_id) {
        return dao.getTerminadosCliente(cliente_id);
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("mantenimientos/paginate")
    public Page<Mantenimiento> getPaginate(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.getPaginate(pageable);
    }
    
    @Secured({"ROLE_MECANICO"})
    @GetMapping("mantenimientos/paginate/mecanico")
    public Page<Mantenimiento> getPaginateByMecanico(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise,
        @RequestParam(defaultValue = "") Long mecanico_id
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.getPaginateByMecanico(mecanico_id, pageable);
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @PostMapping("mantenimientos")
    public ResponseEntity<?> save(@Valid @RequestBody Mantenimiento mant, BindingResult result) {
        Mantenimiento newData = null;
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
            Optional<Tercero> ter = terceroDao.findById(mant.getMecanico().getId());
            mant.setMecanico(ter.get());
            newData = dao.saveAndFlush(mant);
            String sql = "UPDATE solicitud_mantenimiento SET estado = ? WHERE id = ?";
            jdbcTemplate.update(sql, mant.getEstado(), mant.getSolicitud().getId());
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
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @PutMapping("mantenimientos")
    public ResponseEntity<?> editar(@Valid @RequestBody Mantenimiento mant, BindingResult result) {
        Mantenimiento newData = null;
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
            if (mant.getId() == null) {
                response.put("mensaje", "Error al realizar la actualización base de datos Se requiere ID");
                response.put("error", "Se requiere ID");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            newData = dao.saveAndFlush(mant);
            String sql = "UPDATE solicitud_mantenimiento SET estado = ? WHERE id = ?";
            jdbcTemplate.update(sql, mant.getEstado(), mant.getSolicitud().getId());
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
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @DeleteMapping("mantenimiento/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Mantenimiento> mant = dao.findById(id);
        Map<String, Object> response = new HashMap<>();

        try {
            dao.deleteById(id);
            
            String sql = "UPDATE solicitud_mantenimiento SET estado = 'En solicitud' WHERE id = ?";
            jdbcTemplate.update(sql, mant.get().getSolicitud().getId());
            
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el registro de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success", 1);
        response.put("mensaje", "El registro fue eliminado satisfactoriamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("mantenimiento/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id) {
        Optional<Mantenimiento> mant = dao.findById(id);
        Map<String, Object> response = new HashMap<>();

        try {
            mant = dao.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el registro de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("success", 1);
        response.put("data", mant.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
