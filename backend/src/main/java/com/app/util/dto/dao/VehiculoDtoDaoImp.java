package com.app.util.dto.dao;

import com.app.util.dto.VehiculoDTO;
import java.sql.ResultSet;
import java.util.List;
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
public class VehiculoDtoDaoImp implements VehiculoDtoDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DataSource dataSource;

    @Override
    public List<VehiculoDTO> byClienteId(Long cliente_id) {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        String SQL = "SELECT cv.id, v.marca, v.modelo, cv.cliente_id "
                + "FROM cliente_vehiculo cv "
                + "INNER JOIN vehiculos v ON cv.vehiculo_id = v.id "
                + "WHERE cv.cliente_id = "+cliente_id;
        return (List<VehiculoDTO>) namedTemplate.query(SQL, (ResultSet rs, int rowNum) -> {
            VehiculoDTO data = new BeanPropertyRowMapper<>(VehiculoDTO.class).mapRow(rs, rowNum);
            return data;
        });
    }
    
}
