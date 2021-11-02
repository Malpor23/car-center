package com.app.model.dao;

import com.app.model.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel Gomez
 */
public interface TipoDocumentoDao extends JpaRepository<TipoDocumento, String> {
    
}
