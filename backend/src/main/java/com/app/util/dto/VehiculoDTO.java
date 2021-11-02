package com.app.util.dto;

import com.app.model.entity.Vehiculo;

/**
 *
 * @author Manuel Gomez
 */
public class VehiculoDTO {
    
    Long cliente_id;
    
    Long id;

    private String marca;

    private String modelo;

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

}
