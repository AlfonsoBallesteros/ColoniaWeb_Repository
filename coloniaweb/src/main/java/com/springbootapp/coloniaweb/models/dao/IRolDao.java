package com.springbootapp.coloniaweb.models.dao;
import com.springbootapp.coloniaweb.models.entity.Rol;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * IRolDao
 */
public interface IRolDao extends JpaRepository<Rol, Long> {


    
}
