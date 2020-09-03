package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.Country;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICountryDao extends JpaRepository<Country, Long> {

    
} 