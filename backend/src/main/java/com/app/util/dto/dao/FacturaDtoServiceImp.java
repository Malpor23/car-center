package com.app.util.dto.dao;

import com.app.util.Paginacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Gomez
 */
@Service
public class FacturaDtoServiceImp implements FacturaDtoService {

    @Autowired
    FacturaDtoDao dao;
    
    @Override
    public Paginacion getPaginate(Pageable pageable) {
        return dao.getPaginate(pageable);
    }
    
}
