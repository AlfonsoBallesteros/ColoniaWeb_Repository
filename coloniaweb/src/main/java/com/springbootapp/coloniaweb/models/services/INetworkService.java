package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.Network;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INetworkService {

    public List<Network> findAll();     

    public Network findById(Long id); 

    public Network save(Network network);                     
  
    public void deleteById(Long id); 

    public Page<Network> findAll(Pageable pageable);  

}