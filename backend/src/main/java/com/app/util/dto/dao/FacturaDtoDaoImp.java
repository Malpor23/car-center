package com.app.util.dto.dao;

import com.app.util.dto.FacturaDTO;
import com.app.util.Paginacion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Gomez
 */
@Service
@Component
public class FacturaDtoDaoImp implements FacturaDtoDao {
    
    @Autowired
    private DataSource dataSource;

    @Override
    public Paginacion getPaginate(Pageable page) {
        Paginacion obj = new Paginacion();
        List<FacturaDTO> list = new ArrayList<FacturaDTO>();
        
        Integer limit = page.getPageSize();
        Integer offset = ((page.getPageNumber() * page.getPageSize()) - page.getPageSize());
        Long totalpages = Long.valueOf(0);
        Integer totalregistros = 0;
        
        String sql1 = "SELECT f.id, f.created_at, f.total, "
            + "REPLACE(CONCAT(te.primer_nombre,' ',te.segundo_nombre,' ',te.primer_apellido,' ',te.segundo_apellido), '  ', ' ') nombre, "
            + "ps.nombre servicio "
            + "FROM facturas f "
            + "INNER JOIN mantenimientos t ON f.mantenimiento_id = t.id "
            + "INNER JOIN solicitud_mantenimiento s ON t.solicitud_id = s.id "
            + "INNER JOIN cliente_vehiculo cv ON s.cliente_vehiculo_id = cv.id "
            + "INNER JOIN terceros te ON cv.cliente_id = te.id "
            + "INNER JOIN productos_servicios ps ON s.servicio_id = ps.id "
            + "ORDER BY f.created_at DESC "
            + "LIMIT "+limit+" OFFSET "+offset+"";
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        list = namedTemplate.query(sql1, (ResultSet rs, int rowNum) -> {
            FacturaDTO data = (new BeanPropertyRowMapper<>(FacturaDTO.class)).mapRow(rs, rowNum);
            return data;
        });
        
        String sql2 = "SELECT COUNT(*) FROM facturas";
        
        NamedParameterJdbcTemplate namedTemplate2 = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource params2 = new MapSqlParameterSource();
        totalregistros = namedTemplate2.queryForObject(sql2, params2, Integer.class);
        
        double numero = totalregistros * 1.0 / page.getPageSize() * 1.0;
        double parteDecimal = numero % 1;
        double parteEntera = numero - parteDecimal;
        double totalpag = parteDecimal > 0.0 ? parteEntera + 1 : parteEntera;

        totalpages = Math.round(totalpag);

        obj.setContent(list);
        obj.setNumber(page.getPageNumber());
        obj.setSize(page.getPageSize());
        obj.setTotalPages(totalpages);
        obj.setTotalRegistros(totalregistros);
        return obj;
    }
    
}
