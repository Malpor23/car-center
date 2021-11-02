package com.app.util.dto;

import com.app.model.entity.Vehiculo;

/**
 *
 * @author Manuel Gomez
 */
public class ClienteVehiculoDTO {

    private Long id;

    private Long cliente_id;

    private Long vehiculo_id;

    private Vehiculo vehiculo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Long getVehiculo_id() {
        return vehiculo_id;
    }

    public void setVehiculo_id(Long vehiculo_id) {
        this.vehiculo_id = vehiculo_id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

}
