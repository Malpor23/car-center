package com.app.model.dao;

import com.app.model.entity.Mantenimiento;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Manuel Gomez
 */
public interface MantenimientoDao extends JpaRepository<Mantenimiento, Long>{
    
    @Query("SELECT m FROM Mantenimiento m WHERE m.facturado = false ORDER BY m.created_at DESC")
    public Page<Mantenimiento> getPaginate(Pageable pageable);
    
    @Query("SELECT m FROM Mantenimiento m JOIN m.mecanico me WHERE me.id = ?1 AND m.facturado = false ORDER BY m.created_at DESC")
    public Page<Mantenimiento> getPaginateByMecanico(Long mecanico_id, Pageable pageable);
 
    @Query("SELECT m FROM Mantenimiento m WHERE m.estado = 'Terminado' AND m.facturado NOT IN (true)")
    List<Mantenimiento> getTerminados();
    
    @Query(
        "SELECT m FROM Mantenimiento m JOIN m.solicitud s JOIN s.clienteVehiculo cv JOIN cv.cliente c "
            + "WHERE m.estado = 'Terminado' AND c.id = ?1 AND m.facturado NOT IN (true)"
    )
    List<Mantenimiento> getTerminadosCliente(Long cliente_id);
    
}
