package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.Permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPermissionService {

    public List<Permission> findAll();  

    public Permission findById(Long id); 

    public Permission save(Permission permission);                        
  
    public void deleteById(Long id); 

    public Page<Permission> findAll(Pageable pageable);  

    
}