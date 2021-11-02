package com.app.util.dto.dao;

import com.app.util.Paginacion;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Manuel Gomez
 */
public interface FacturaDtoDao {
   
    public Paginacion getPaginate(Pageable pageable);
}
