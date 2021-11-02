package com.app.controller;

import com.app.model.dao.RoleDao;
import com.app.model.dao.TerceroDao;
import com.app.model.dao.TipoTerceroDao;
import com.app.model.dao.UsuarioDao;
import com.app.model.entity.Role;
import com.app.model.entity.Tercero;
import com.app.model.entity.TipoTercero;
import com.app.model.entity.Usuario;
import com.app.security.jwt.JwtUtils;
import com.app.security.user.UsuarioPrincipal;
import com.app.util.JwtResponse;
import com.app.util.dto.LoginDTO;
import com.app.util.dto.TerceroDTO;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class AuthController {

    @Autowired
    AuthenticationManager auth;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    TerceroDao terceroDao;
    
    @Autowired
    TipoTerceroDao tipoDao;
    
    @Autowired
    UsuarioDao usuarioDao;
    
    @Autowired
    RoleDao roleDao;
   
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("auth")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO user) {

        Authentication authentication = auth.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UsuarioPrincipal userDetails = (UsuarioPrincipal) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(
            new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getTerceroId(),
                userDetails.getUsername(),
                userDetails.getNombre(),
                userDetails.getApellido(),
                roles
            )
        );
    }
    
    @PostMapping("registrar")
    public ResponseEntity<?> getRegistrar(@Valid @RequestBody TerceroDTO ter, BindingResult result) {
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
            Optional<TipoTercero> tp = tipoDao.findById(Long.valueOf(1));
            Tercero cli = new Tercero();
            cli.setTipoTercero(tp.get());
            cli.setPrimerNombre(ter.getPrimerNombre());
            cli.setSegundoNombre(ter.getSegundoNombre());
            cli.setPrimerApellido(ter.getPrimerApellido());
            cli.setSegundoApellido(ter.getSegundoApellido());
            cli.setTipoDocumento(ter.getTipoDocumento());
            cli.setN_documento(ter.getN_documento());
            cli.setCelular(ter.getCelular());
            cli.setDireccion(ter.getDireccion());
            cli.setEmail(ter.getEmail());
            cli.setEstado(Boolean.TRUE);
            newData = terceroDao.save(cli);
            
            Optional<Role> rol = roleDao.findById(Long.valueOf(1));
            Usuario usu = new Usuario();
            usu.setTercero(newData);
            usu.setEnabled(Boolean.TRUE);
            usu.setUsername(ter.getUsername());
            usu.setPassword(encoder.encode(ter.getPassword()));
            usu.setRole(rol.get());
            usuarioDao.save(usu);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("success", 1);
        response.put("mensaje", "Registro realizado satisfactoriamente!");
        response.put("data", newData);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
   
    @GetMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable Long id) {
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    @GetMapping("usuario/info")
    public ResponseEntity<Object> getByToken(Principal user) {
        Usuario data = usuarioDao.findByUsername(user.getName());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
