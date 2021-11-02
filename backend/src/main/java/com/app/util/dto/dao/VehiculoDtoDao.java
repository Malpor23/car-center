package com.app.util.dto.dao;

import com.app.util.dto.VehiculoDTO;
import java.util.List;

/**
 *
 * @author Manuel Gomez
 */
public interface VehiculoDtoDao {
    
    List<VehiculoDTO> byClienteId(Long cliente_id);
    
}
