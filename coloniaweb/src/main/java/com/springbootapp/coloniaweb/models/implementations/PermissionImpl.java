package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IPermissionDao;
import com.springbootapp.coloniaweb.models.entity.Permission;
import com.springbootapp.coloniaweb.models.services.IPermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionImpl implements IPermissionService {

    @Autowired
    private IPermissionDao permissionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findAll() {

        return permissionDao.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Permission findById(Long id) {

        return permissionDao.findById(id).orElse(null);

    }

    @Override
    @Transactional
    public Permission save(Permission permission) {

        return permissionDao.save(permission);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        permissionDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Permission> findAll(Pageable pageable) {

        return permissionDao.findAll(pageable);

    }

}