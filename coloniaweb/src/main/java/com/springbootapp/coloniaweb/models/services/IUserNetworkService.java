package com.springbootapp.coloniaweb.models.services;

import java.util.List;

import com.springbootapp.coloniaweb.models.entity.UserNetwork;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserNetworkService {

    public List<UserNetwork> findAll();

    public UserNetwork findById(Long id);

    public UserNetwork save(UserNetwork userNetwork);

    public void deleteById(Long id);

    public Page<UserNetwork> findAll(Pageable pageable);


}