package com.app.model.dao;

import com.app.model.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel Gomez
 */
public interface VehiculoDao extends JpaRepository<Vehiculo, Long>{
    
}
