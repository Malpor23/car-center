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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author Manuel Gomez
 */
@Entity
@Table(name = "mantenimientos")
public class Mantenimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String descripcion;

    @ManyToOne()
    @JoinColumn(name = "solicitud_id", foreignKey = @ForeignKey(name = "fk_mantenimientos_solicitud_id"))
    @NotNull
    private Solicitud solicitud;

    @ManyToOne()
    @JoinColumn(name = "mecanico_id", foreignKey = @ForeignKey(name = "fk_mantenimientos_mecanico_id"))
    @NotNull
    private Tercero mecanico;

    @NotEmpty
    @Column(length = 20)
    private String estado;

    @Column(nullable = true)
    private String observacion;

    @Column(columnDefinition = "boolean default false")
    private Boolean facturado;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date created_at;

    @PrePersist
    public void prePersist() {
        created_at = new Date();
    }

    public Mantenimiento() {
    }

    public Mantenimiento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Tercero getMecanico() {
        return mecanico;
    }

    public void setMecanico(Tercero mecanico) {
        this.mecanico = mecanico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getFacturado() {
        return facturado;
    }

    public void setFacturado(Boolean facturado) {
        this.facturado = facturado;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

}
