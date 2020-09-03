package com.springbootapp.coloniaweb.models.services;

import com.springbootapp.coloniaweb.models.entity.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IUsuarioService {

    public List<Usuario> findAll();    

    public Usuario findById(Long id); 

    public Usuario findByUsername(String username);

    public Usuario findByEmailIgnoreCase(String username);

    public Usuario save(Usuario user);      
        
    public void deleteById(Long id);   

    public Page<Usuario> findAll(Pageable pageable); 
    
    void deleteAllExpiredSince(Date now);
    
}