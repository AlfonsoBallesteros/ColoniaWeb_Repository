package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IExpJobDao;
import com.springbootapp.coloniaweb.models.entity.ExpJob;
import com.springbootapp.coloniaweb.models.services.IExpJobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpJobImpl implements IExpJobService {

    @Autowired
    private IExpJobDao expJobDao;

    @Override
    @Transactional(readOnly = true)
    public List<ExpJob> findAll() {

        return expJobDao.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public ExpJob findById(Long id) {

        return expJobDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ExpJob save(ExpJob expJob) {

        return expJobDao.save(expJob);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        expJobDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExpJob> findAll(Pageable pageable) {

        return expJobDao.findAll(pageable);

    }

}