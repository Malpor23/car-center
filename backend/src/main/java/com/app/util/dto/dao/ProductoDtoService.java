package com.app.util.dto.dao;

import com.app.util.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Manuel Gomez
 */
public interface ProductoDtoService {
    
    List<ProductoDTO> findExistencia();
    
    Optional<ProductoDTO> findById(Long id);
    
}
