package com.app.util.dto.dao;

import com.app.model.entity.Vehiculo;
import com.app.util.dto.ClienteVehiculoDTO;
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
public class IClienteVehiculoDao implements ClienteVehiculoDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DataSource dataSource;
            
    @Override
    public ClienteVehiculoDTO saveOrUpdate(ClienteVehiculoDTO data) {
        if (data.getId() != null) {
            String sql = "UPDATE cliente_vehiculo SET cliente_id = ?, vehiculo_id = ? WHERE id = ?";
            jdbcTemplate.update(sql, data.getCliente_id(), data.getVehiculo_id(), data.getId());
        } else {
            jdbcTemplate.execute(
                "INSERT INTO cliente_vehiculo (cliente_id, vehiculo_id) VALUES ("+data.getCliente_id()+", "+data.getVehiculo_id()+")"
            );
        }
        return data;
    }

    @Override
    public List<ClienteVehiculoDTO> findAllCliente(Long cliente_id) {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        String SQL = "SELECT cv.*, v.marca, v.modelo FROM cliente_vehiculo cv "
                + "INNER JOIN vehiculos v ON cv.vehiculo_id = v.id "
                + "WHERE cv.cliente_id = "+cliente_id;
        return (List<ClienteVehiculoDTO>) namedTemplate.query(SQL, (ResultSet rs, int rowNum) -> {
            ClienteVehiculoDTO data = new BeanPropertyRowMapper<>(ClienteVehiculoDTO.class).mapRow(rs, rowNum);
            Vehiculo v = new Vehiculo();
            v.setId(rs.getLong("vehiculo_id"));
            v.setMarca(rs.getString("marca"));
            v.setModelo(rs.getString("modelo"));
            data.setVehiculo(v);
            return data;
        });
    }
    
    @Override
    public void delete(Long cliente_id, Long vehiculo_id){
      String SQL = "DELETE FROM cliente_vehiculo WHERE cliente_id = ? AND vehiculo_id = ?";
      jdbcTemplate.update(SQL, cliente_id, vehiculo_id);
      return;
   }
    
}
