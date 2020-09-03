package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IDocTypeDao;
import com.springbootapp.coloniaweb.models.entity.DocType;
import com.springbootapp.coloniaweb.models.services.IDocTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctypeImpl implements IDocTypeService {

    @Autowired
    private IDocTypeDao docTypeDao;

    @Override
    @Transactional(readOnly = true)
    public List<DocType> findAll() {

        return docTypeDao.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public DocType findById(Long id) {

        return docTypeDao.findById(id).orElse(null);

    }

    @Override
    @Transactional
    public DocType save(DocType docType) {

        return docTypeDao.save(docType);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        docTypeDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocType> findAll(Pageable pageable) {

        return docTypeDao.findAll(pageable);
    }

}