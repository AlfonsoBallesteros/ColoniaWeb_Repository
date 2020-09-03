package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.DocType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IDocTypeDao extends JpaRepository<DocType, Long>{

}