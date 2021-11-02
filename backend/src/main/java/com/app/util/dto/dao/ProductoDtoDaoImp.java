package com.app.util.dto.dao;

import com.app.util.dto.ProductoDTO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Gomez
 */
@Service
@Component
public class ProductoDtoDaoImp implements ProductoDtoDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ProductoDTO> findExistencia() {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        String SQL = "SELECT p.*, COALESCE(i.existencia, 0) existencia "
                + "FROM productos_servicios p "
                + "LEFT JOIN inventarios i ON p.id = i.producto_id "
                + "WHERE p.tipo_producto_id = 1";
        return (List<ProductoDTO>) namedTemplate.query(SQL, (ResultSet rs, int rowNum) -> {
            ProductoDTO data = new BeanPropertyRowMapper<>(ProductoDTO.class).mapRow(rs, rowNum);
            return data;
        });
    }

    @Override
    public Optional<ProductoDTO> findById(Long id) {
        List<ProductoDTO> list = new ArrayList<>();
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        String SQL = "SELECT p.*, COALESCE(i.existencia, 0) existencia "
                + "FROM productos_servicios p "
                + "LEFT JOIN inventarios i ON p.id = i.producto_id "
                + "WHERE p.tipo_producto_id = 1 AND p.id = "+id;

        list = namedTemplate.query(SQL, (ResultSet rs, int rowNum) -> {
            ProductoDTO data = (new BeanPropertyRowMapper<>(ProductoDTO.class)).mapRow(rs, rowNum);
            return data;
        });
        return list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty();
    }
    
}
