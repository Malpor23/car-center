package com.app.model.dao;

import com.app.model.entity.Tercero;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Manuel Gomez
 */
public interface TerceroDao extends JpaRepository<Tercero, Long> {

    @Query("SELECT t FROM Tercero t JOIN t.tipoTercero tt WHERE tt.id = 1 ORDER BY t.primerNombre")
    public List<Tercero> getClientes();

    @Query("SELECT t FROM Tercero t JOIN t.tipoTercero tt WHERE tt.id = 2 ORDER BY t.primerNombre")
    public List<Tercero> getMecanicos();

    @Query("SELECT t FROM Tercero t JOIN t.tipoTercero tt WHERE tt.id = 1 ORDER BY t.primerNombre")
    public Page<Tercero> findAllClientes(Pageable pageable);

    @Query("SELECT t FROM Tercero t JOIN t.tipoTercero tt WHERE tt.id = 2 ORDER BY t.primerNombre")
    public Page<Tercero> findAllMecanicos(Pageable pageable);
    
    @Query(
        value = "SELECT * FROM terceros t WHERE NOT EXISTS (SELECT * FROM usuarios WHERE tercero_id = t.id)", 
        nativeQuery = true
    )
    public List<Tercero> getSinUser();

}
