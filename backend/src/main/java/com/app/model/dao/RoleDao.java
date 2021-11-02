package com.app.model.dao;

import com.app.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel Gomez
 */
public interface RoleDao extends JpaRepository<Role, Long>{
    
}
