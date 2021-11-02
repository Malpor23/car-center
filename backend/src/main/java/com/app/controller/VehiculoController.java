package com.app.controller;

import com.app.util.dto.VehiculoDTO;
import com.app.util.dto.dao.VehiculoDtoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VehiculoController {
    
    @Autowired
    private VehiculoDtoService dtoService;
    
    @Secured({"ROLE_ADMIN", "ROLE_MECANICO", "ROLE_CLIENTE"})
    @GetMapping("vehiculo/cliente/{cliente_id}")
    public List<VehiculoDTO> byClienteId(@PathVariable Long cliente_id) {
        return dtoService.byClienteId(cliente_id);
    }
    
}
