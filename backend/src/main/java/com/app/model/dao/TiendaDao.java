package com.app.model.dao;

import com.app.model.entity.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel Gomez
 */
public interface TiendaDao extends JpaRepository<Tienda, Long> {
    
}
