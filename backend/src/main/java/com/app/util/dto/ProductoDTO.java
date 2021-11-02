package com.app.util.dto;

/**
 *
 * @author Manuel Gomez
 */
public class ProductoDTO {

    private Long id;

    private String nombre;

    private Double valorMinimo;

    private Double valorMaximo;

    private Double precio;
    
    private Long tipo_producto_id;
    
    private Integer existencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getTipo_producto_id() {
        return tipo_producto_id;
    }

    public void setTipo_producto_id(Long tipo_producto_id) {
        this.tipo_producto_id = tipo_producto_id;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }
    
    
    
}
