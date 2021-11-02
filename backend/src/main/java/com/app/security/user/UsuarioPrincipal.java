package com.app.security.user;

import com.app.model.entity.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Manuel Gomez
 */
public class UsuarioPrincipal implements UserDetails {

    private final Usuario usuario;

    public UsuarioPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(this.usuario.getRole().getNombre());
       authorities.add(authority);
        return authorities;
    }

    public Long getId() {
        return this.usuario.getId();
    }
    
    public Long getTerceroId() {
        return this.usuario.getTercero().getId();
    }

    @Override
    public String getPassword() {
        return this.usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return this.usuario.getUsername();
    }

    public String getNombre() {
        return this.usuario.getTercero().getPrimerNombre();
    }

    public String getApellido() {
        return this.usuario.getTercero().getPrimerApellido();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.usuario.getEnabled().equals(true);
    }

}
