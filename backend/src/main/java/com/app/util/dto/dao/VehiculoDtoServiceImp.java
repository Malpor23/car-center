package com.app.util.dto.dao;

import com.app.util.dto.VehiculoDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Gomez
 */
@Service
public class VehiculoDtoServiceImp implements VehiculoDtoService {
    
    @Autowired
    VehiculoDtoDao dao;

    @Override
    public List<VehiculoDTO> byClienteId(Long cliente_id) {
        return dao.byClienteId(cliente_id);
    }
    
}
