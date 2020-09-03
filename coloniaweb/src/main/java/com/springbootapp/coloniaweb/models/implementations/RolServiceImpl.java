package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IRolDao;
import com.springbootapp.coloniaweb.models.entity.Rol;
import com.springbootapp.coloniaweb.models.services.IRolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolDao rolDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAll() {

        return (List<Rol>) rolDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Rol findById(Long id) {

        return rolDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Rol save(Rol rol) {

        return rolDao.save(rol);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        rolDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rol> findAll(Pageable pageable) {
        
        return rolDao.findAll(pageable);
    }

}