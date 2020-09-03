package com.springbootapp.coloniaweb.models.services;

import com.springbootapp.coloniaweb.models.entity.Rol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRolService {
    
    public List<Rol> findAll();

    public Rol findById(Long id);

    public Rol save(Rol rol);

    public void deleteById(Long id);

    public Page<Rol> findAll(Pageable pageable);

}