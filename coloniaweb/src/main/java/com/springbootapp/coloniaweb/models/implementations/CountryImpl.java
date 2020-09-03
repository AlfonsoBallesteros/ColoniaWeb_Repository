package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.ICountryDao;
import com.springbootapp.coloniaweb.models.entity.Country;
import com.springbootapp.coloniaweb.models.services.ICountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryImpl implements ICountryService {

    @Autowired
    private ICountryDao countryDao;

    @Override
    @Transactional(readOnly = true)
    public List<Country> findAll() {

        return countryDao.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Country findById(Long id) {

        return countryDao.findById(id).orElse(null);

    }

    @Override
    @Transactional
    public Country save(Country country) {

        return countryDao.save(country);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        countryDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> findAll(Pageable pageable) {

        return countryDao.findAll(pageable);

    }

}