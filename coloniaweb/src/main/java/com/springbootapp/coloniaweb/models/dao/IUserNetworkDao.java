package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.UserNetwork;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserNetworkDao extends JpaRepository<UserNetwork, Long> {

}