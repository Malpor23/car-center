package com.app.model.dao;

import com.app.model.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Manuel Gomez
 */
public interface FacturaDao extends JpaRepository<Factura, Long> {

    @Query("SELECT f FROM Factura f ORDER BY f.created_at DESC")
    public Page<Factura> getPaginate(Pageable pageable);

}
