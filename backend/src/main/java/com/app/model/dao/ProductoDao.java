package com.app.model.dao;

import com.app.model.entity.Producto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Manuel Gomez
 */
public interface ProductoDao extends JpaRepository<Producto, Long> {
    
    @Query("SELECT p FROM Producto p JOIN p.tipoProducto t WHERE t.id = 1 ORDER BY p.nombre")
    public List<Producto> getProductos();

    @Query("SELECT p FROM Producto p JOIN p.tipoProducto t WHERE t.id = 2 ORDER BY p.nombre")
    public List<Producto> getServicios();

    /**
     *
     * @param pageable
     * @return
     */
    @Query("SELECT p FROM Producto p ORDER BY p.nombre ASC")
    @Override
    public Page<Producto> findAll(Pageable pageable);
   
}
