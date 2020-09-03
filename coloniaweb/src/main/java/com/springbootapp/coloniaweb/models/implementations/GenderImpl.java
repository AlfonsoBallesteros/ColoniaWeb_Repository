package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IGenderDao;
import com.springbootapp.coloniaweb.models.entity.Gender;
import com.springbootapp.coloniaweb.models.services.IGenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenderImpl implements IGenderService {

    @Autowired
    private IGenderDao genderDao;

    @Override
    @Transactional(readOnly = true)
    public List<Gender> findAll() {

        return genderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Gender findById(Long id) {

        return genderDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Gender save(Gender gender) {

        return genderDao.save(gender);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        genderDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Gender> findAll(Pageable pageable) {

        return genderDao.findAll(pageable);
    }

}