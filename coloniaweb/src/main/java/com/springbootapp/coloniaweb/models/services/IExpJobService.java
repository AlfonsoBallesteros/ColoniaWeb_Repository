package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.ExpJob;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IExpJobService {

    public List<ExpJob> findAll();

    public ExpJob findById(Long id);

    public ExpJob save(ExpJob expJob);

    public void deleteById(Long id);

    public Page<ExpJob> findAll(Pageable pageable);

}