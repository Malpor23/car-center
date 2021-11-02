package com.app.util;

import java.util.List;

/**
 *
 * @author Manuel Gomez
 */
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private Long terceroId;
    private String username;
    private String nombre;
    private String apellido;
    private List<String> role;

    public JwtResponse(String accessToken, Long id, Long terceroId, String username, String nombre, String apellido, 
            List<String> role) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.role = role;
        this.terceroId = terceroId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTerceroId() {
        return terceroId;
    }

    public void setTerceroId(Long terceroId) {
        this.terceroId = terceroId;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

}
