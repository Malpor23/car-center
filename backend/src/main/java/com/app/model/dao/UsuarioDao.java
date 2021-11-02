package com.app.model.dao;

import com.app.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Manuel Gomez
 */
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername(String username);
    
    @Query("SELECT u FROM Usuario u ORDER BY u.username")
    public Page<Usuario> getPaginate(Pageable pageable);

}
