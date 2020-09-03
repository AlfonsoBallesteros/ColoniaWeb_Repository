package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.Gender;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IGenderDao extends JpaRepository<Gender, Long> {

}