package com.app.model.dao;

import com.app.model.entity.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel Gomez
 */
public interface TipoProductoDao extends JpaRepository<TipoProducto, Long> {
    
}
