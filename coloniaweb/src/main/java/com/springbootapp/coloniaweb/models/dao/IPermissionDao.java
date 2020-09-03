package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.Permission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionDao extends JpaRepository<Permission, Long> {

}