package com.app.controller;

import com.app.model.dao.RoleDao;
import com.app.model.dao.TerceroDao;
import com.app.model.dao.UsuarioDao;
import com.app.model.entity.Role;
import com.app.model.entity.Tercero;
import com.app.model.entity.Usuario;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UsuarioController {
    
    @Autowired
    UsuarioDao dao;
    
    @Autowired
    RoleDao roleDao;
    
    @Autowired
    TerceroDao terceroDao;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("roles")
    public List<Role> getRoles() {
        return roleDao.findAll();
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("usuarios/paginate")
    public Page<Usuario> getPaginate(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageZise);
        return dao.getPaginate(pageable);
    }
    
    @Secured({"ROLE_ADMIN"})
    @PostMapping("usuarios")
    public ResponseEntity<?> save(@Valid @RequestBody Usuario usu, BindingResult result) {
        Usuario newData = null;
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
            Optional<Tercero> ter = terceroDao.findById(usu.getTercero().getId());
            usu.setTercero(ter.get());
            usu.setPassword(encoder.encode(usu.getPassword()));
            newData = dao.saveAndFlush(usu);
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
    
}
