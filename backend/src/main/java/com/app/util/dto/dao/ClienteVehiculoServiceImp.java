package com.app.util.dto.dao;

import com.app.util.dto.ClienteVehiculoDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Gomez
 */
@Service
public class ClienteVehiculoServiceImp implements ClienteVehiculoService {

    @Autowired
    ClienteVehiculoDao dao;

    @Override
    public ClienteVehiculoDTO saveOrUpdate(ClienteVehiculoDTO t) {
        return dao.saveOrUpdate(t);
    }

    @Override
    public List<ClienteVehiculoDTO> findAllCliente(Long cliente_id) {
        return dao.findAllCliente(cliente_id);
    }

    @Override
    public void delete(Long cliente_id, Long vehiculo_id) {
        dao.delete(cliente_id, vehiculo_id);
    }

   

}
