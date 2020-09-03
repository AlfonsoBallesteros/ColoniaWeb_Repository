package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IExpAcademicDao;
import com.springbootapp.coloniaweb.models.entity.ExpAcademic;
import com.springbootapp.coloniaweb.models.services.IExpAcademicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpAcademicImpl implements IExpAcademicService {

    @Autowired
    private IExpAcademicDao expAcademicDao;

    @Override
    @Transactional(readOnly = true)
    public List<ExpAcademic> findAll() {

        return expAcademicDao.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public ExpAcademic findById(Long id) {

        return expAcademicDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ExpAcademic save(ExpAcademic expAcademic) {

        return expAcademicDao.save(expAcademic);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        expAcademicDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExpAcademic> findAll(Pageable pageable) {

        return expAcademicDao.findAll(pageable);

    }

}