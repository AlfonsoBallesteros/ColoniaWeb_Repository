package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IUserNetworkDao;
import com.springbootapp.coloniaweb.models.entity.UserNetwork;
import com.springbootapp.coloniaweb.models.services.IUserNetworkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNetworkImpl implements IUserNetworkService {

    @Autowired
    private IUserNetworkDao userNetworkDao;

    @Override
    public UserNetwork findById(Long id) {

        return userNetworkDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserNetwork> findAll() {

        return userNetworkDao.findAll();
    }

    @Override
    @Transactional
    public UserNetwork save(UserNetwork expAcademic) {

        return userNetworkDao.save(expAcademic);
    }

    @Override
    public void deleteById(Long id) {

        userNetworkDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserNetwork> findAll(Pageable pageable) {

        return userNetworkDao.findAll(pageable);
    }

}