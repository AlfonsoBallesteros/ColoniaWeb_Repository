package com.springbootapp.coloniaweb.models.implementations;

import java.util.List;

import com.springbootapp.coloniaweb.models.dao.INetworkDao;
import com.springbootapp.coloniaweb.models.entity.Network;
import com.springbootapp.coloniaweb.models.services.INetworkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NetworkImpl implements INetworkService {

    @Autowired
    private INetworkDao networkDao;

    @Override
    @Transactional(readOnly = true)
    public List<Network> findAll() {

        return networkDao.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Network findById(Long id) {

        return networkDao.findById(id).orElse(null);

    }

    @Override
    @Transactional
    public Network save(Network network) {

        return networkDao.save(network);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        networkDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Network> findAll(Pageable pageable) {

        return networkDao.findAll(pageable);

    }

}