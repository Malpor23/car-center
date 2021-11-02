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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Manuel Gomez
 */
@Entity
@Table(name = "terceros")
public class Tercero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "tipo_documento_id", foreignKey = @ForeignKey(name = "fk_terceros_tipo_documento_id"))
    @NotNull
    private TipoDocumento tipoDocumento;

    @NotEmpty
    @Column(length = 25)
    private String n_documento;

    @NotEmpty(message = "El primer nombre es obligatorio")
    @Column(length = 40, name = "primer_nombre")
    private String primerNombre;

    @Column(nullable = true, length = 40, name = "segundo_nombre")
    private String segundoNombre;

    @NotEmpty(message = "El primer apellido es obligatorio")
    @Column(nullable = true, length = 40, name = "primer_apellido")
    private String primerApellido;

    @NotEmpty(message = "El segundo apellido es obligatorio")
    @Column(nullable = true, length = 40, name = "segundo_apellido")
    private String segundoApellido;

    @NotEmpty(message = "El celular es obligatorio")
    @Column(nullable = true, length = 15)
    private String celular;

    @NotEmpty(message = "La dirección es obligatoria")
    @Column(length = 100)
    private String direccion;

    @Email
    @Column(nullable = true, length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean estado = true;

    @ManyToOne()
    @JoinColumn(name = "tipo_tercero_id", foreignKey = @ForeignKey(name = "fk_terceros_tipo_tercero_id"))
    @NotNull
    private TipoTercero tipoTercero;

    public Tercero() {
    }

    public Tercero(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getN_documento() {
        return n_documento;
    }

    public void setN_documento(String n_documento) {
        this.n_documento = n_documento;
    }

    public String getPrimerNombre() {
        return primerNombre.toUpperCase();
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre.toUpperCase();
    }

    public String getSegundoNombre() {
        return segundoNombre.toUpperCase();
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre.toUpperCase();
    }

    public String getPrimerApellido() {
        return primerApellido.toUpperCase();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido.toUpperCase();
    }

    public String getSegundoApellido() {
        return segundoApellido.toUpperCase();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido.toUpperCase();
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public TipoTercero getTipoTercero() {
        return tipoTercero;
    }

    public void setTipoTercero(TipoTercero tipoTercero) {
        this.tipoTercero = tipoTercero;
    }

}
