package com.app.security.user;

import com.app.model.dao.UsuarioDao;
import com.app.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Manuel Gomez
 */
@Service
public class UsuarioPrincipalServiceImp implements UserDetailsService {

    @Autowired
    UsuarioDao dao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = null;
        try {
            usuario = dao.findByUsername(username);
            UsuarioPrincipal userPrincipal = new UsuarioPrincipal(usuario);
            return userPrincipal;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UsernameNotFoundException("Usuario: " + username + " no existe en el sistema!");
        }

    }

}
