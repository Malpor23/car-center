package com.app.controller;

import com.app.model.dao.FacturaDao;
import com.app.model.dao.ProductoDao;
import com.app.model.entity.Factura;
import com.app.model.entity.Producto;
import com.app.util.dto.dao.FacturaDtoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
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
public class FacturaController {
    
    @Autowired
    FacturaDao dao;
    
    @Autowired
    ProductoDao prodDao;
    
    @Autowired
    FacturaDtoService service;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping("facturas/paginate")
    public ResponseEntity<Object> getPaginate(
        @RequestParam(defaultValue = "1") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageZise
    ) {
        Pageable paging = PageRequest.of(pageNo, pageZise);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.getPaginate(paging));
    }
    
    @PostMapping("facturas")
    public ResponseEntity<?> save(@Valid @RequestBody Factura fact, BindingResult result) {
        Factura newData = null;
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
            fact.getItems().forEach((item) -> {
                Optional<Producto> pro = prodDao.findById(item.getProducto().getId());
                item.setProducto(pro.get());
                String sql = "UPDATE inventarios SET existencia = existencia - ? WHERE producto_id = ?";
                jdbcTemplate.update(sql, item.getCantidad(), item.getProducto().getId());
            });
            newData = dao.saveAndFlush(fact);
            String sql = "UPDATE mantenimientos SET facturado = true WHERE id = ?";
            jdbcTemplate.update(sql, fact.getMantenimiento().getId());
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            System.out.println(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success", 1);
        response.put("mensaje", "Factura guardada satisfactoriamente!");
        response.put("data", newData);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
}
