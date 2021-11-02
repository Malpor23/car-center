package com.app.controller;

import com.app.util.dto.ProductoDTO;
import com.app.util.dto.dao.ProductoDtoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manuel Gomez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class InventarioController {
    
    @Autowired
    ProductoDtoService service;
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("productos/existencia")
    public List<ProductoDTO> getProductoExistencia() {
        return service.findExistencia();
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO"})
    @GetMapping("productos/existencia/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Optional<ProductoDTO> data = null;
        Map<String, Object> response = new HashMap<>();
        
        try {
            data = service.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!data.isPresent()) {
            response.put("mensaje", "El producto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
}
