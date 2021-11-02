package com.app.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Manuel Gomez
 */
@Entity
@Table(name = "productos_servicios")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @Column(name = "valor_minimo", columnDefinition = "Decimal(16,2) default '0.00'", nullable = true)
    private Double valorMinimo;

    @Column(name = "valor_maximo", columnDefinition = "Decimal(16,2) default '0.00'", nullable = true)
    private Double valorMaximo;

    @Column(columnDefinition = "Decimal(16,2) default '0.00'", nullable = true)
    private Double precio;

    @ManyToOne()
    @JoinColumn(name = "tipo_producto_id", foreignKey = @ForeignKey(name = "fk_productos_tipo_producto_id"))
    @NotNull
    private TipoProducto tipoProducto;

    public Producto() {
    }

    public Producto(Long id) {
        this.id = id;
    }

    public Producto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.toUpperCase();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Producto(String nombre, Double valorMinimo, Double valorMaximo, Double precio, TipoProducto tipoProducto) {
        this.nombre = nombre;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.precio = precio;
        this.tipoProducto = tipoProducto;
    }

}
