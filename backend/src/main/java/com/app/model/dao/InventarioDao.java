package com.app.model.dao;

import com.app.model.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel Gomez
 */
public interface InventarioDao extends JpaRepository<Inventario, Long>{
    
}
