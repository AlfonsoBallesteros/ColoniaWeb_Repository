package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.Country;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface ICountryService {

    public List<Country> findAll();

    public Country findById(Long id);

    public Country save(Country country);

    public void deleteById(Long id);

    public Page<Country> findAll(Pageable pageable);

}