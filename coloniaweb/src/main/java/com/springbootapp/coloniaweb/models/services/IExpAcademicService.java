package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.ExpAcademic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IExpAcademicService {

    public List<ExpAcademic> findAll();

    public ExpAcademic findById(Long id);

    public ExpAcademic save(ExpAcademic expAcademic);

    public void deleteById(Long id);

    public Page<ExpAcademic> findAll(Pageable pageable);
    
    
}