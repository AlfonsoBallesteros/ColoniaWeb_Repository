package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.ExpJob;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IExpJobDao extends JpaRepository<ExpJob, Long>{

}