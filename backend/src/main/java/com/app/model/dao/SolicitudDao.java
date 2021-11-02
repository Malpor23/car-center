package com.app.model.dao;

import com.app.model.entity.Solicitud;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Manuel Gomez
 */
public interface SolicitudDao extends JpaRepository<Solicitud, Long> {

    @Query("SELECT s FROM Solicitud s ORDER BY s.created_at DESC")
    public Page<Solicitud> findPaginate(Pageable pageable);

    @Query(
        "SELECT s FROM Solicitud s JOIN s.clienteVehiculo cv JOIN cv.cliente c "
        + "WHERE c.id = ?1 ORDER BY s.created_at DESC"
    )
    public Page<Solicitud> findPaginateByClienteId(Long cliente_id, Pageable pageable);
    
    @Query(value = "SELECT * FROM solicitud_mantenimiento WHERE estado = 'En solicitud'", nativeQuery = true)
    List<Solicitud> getEnSolicitud();

}
