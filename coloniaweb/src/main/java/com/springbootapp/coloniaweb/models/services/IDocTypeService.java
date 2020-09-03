package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.DocType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDocTypeService {

    public List<DocType> findAll();     

    public DocType findById(Long id);             

    public DocType save(DocType docType);      
  
    public void deleteById(Long id); 
    
    public Page<DocType> findAll(Pageable pageable);     

}