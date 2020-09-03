package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.Gender;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenderService {

    public List<Gender> findAll();   

    public Gender findById(Long id); 

    public Gender save(Gender gender);                    
  
    public void deleteById(Long id); 
    
    public Page<Gender> findAll(Pageable pageable);     

}