package com.app.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author Manuel Gomez
 */
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 3)
    private String id;

    @NotEmpty
    @Column(length = 30)
    private String descripcion;

    public TipoDocumento() {
    }

    public TipoDocumento(String id) {
        this.id = id;
    }

    public TipoDocumento(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
