package com.app.util.dto.dao;

import com.app.util.dto.ClienteVehiculoDTO;
import java.util.List;

/**
 *
 * @author Manuel Gomez
 */
public interface ClienteVehiculoService {

    ClienteVehiculoDTO saveOrUpdate(ClienteVehiculoDTO t);
    
    List<ClienteVehiculoDTO> findAllCliente(Long cliente_id);
    
    public void delete(Long cliente_id, Long vehiculo_id);

}
