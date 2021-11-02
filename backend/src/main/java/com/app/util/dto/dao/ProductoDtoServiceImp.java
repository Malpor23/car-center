package com.app.util.dto.dao;

import com.app.util.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Gomez
 */
@Service
public class ProductoDtoServiceImp implements ProductoDtoService {
    
    @Autowired
    ProductoDtoDao dao;

    @Override
    public List<ProductoDTO> findExistencia() {
        return dao.findExistencia();
    }

    @Override
    public Optional<ProductoDTO> findById(Long id) {
        return dao.findById(id);
    }
    
}
