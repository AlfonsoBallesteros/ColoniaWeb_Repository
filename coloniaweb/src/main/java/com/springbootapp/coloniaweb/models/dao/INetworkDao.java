package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.Network;

import org.springframework.data.jpa.repository.JpaRepository;

public interface INetworkDao extends JpaRepository<Network, Long>{

}