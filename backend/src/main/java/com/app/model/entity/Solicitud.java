package com.app.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author Manuel Gomez
 */
@Entity
@Table(name = "solicitud_mantenimiento")
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "cliente_vehiculo_id", foreignKey = @ForeignKey(name = "fk_solicitud_cliente_vehiculo_id"))
    @NotNull
    private ClienteVehiculo clienteVehiculo;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "servicio_id", foreignKey = @ForeignKey(name = "fk_solicitud_servicio_id"))
    private Producto servicio;

    @Column(columnDefinition = "Decimal(16,2) default '0.00'", nullable = true)
    private Double presupuesto;

    @Lob
    @Column(nullable = true)
    private String observacion;
    
    @Column(nullable = true, length = 15)
    private String estado;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_servicio", nullable = true, columnDefinition = "DATE")
    private Date fechaServicio;
    
    @ManyToOne()
    @JoinColumn(name = "tienda_id", foreignKey = @ForeignKey(name = "fk_mantenimientos_tienda_id"))
    @NotNull
    private Tienda tienda;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date created_at;

    @PrePersist
    public void prePersist() {
        created_at = new Date();
    }

    public Solicitud() {
    }

    public Solicitud(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteVehiculo getClienteVehiculo() {
        return clienteVehiculo;
    }

    public void setClienteVehiculo(ClienteVehiculo clienteVehiculo) {
        this.clienteVehiculo = clienteVehiculo;
    }

    public Producto getServicio() {
        return servicio;
    }

    public void setServicio(Producto servicio) {
        this.servicio = servicio;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

}
