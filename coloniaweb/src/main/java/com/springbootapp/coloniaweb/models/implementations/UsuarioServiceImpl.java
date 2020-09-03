package com.springbootapp.coloniaweb.models.implementations;

import java.util.Date;
import java.util.List;

import com.springbootapp.coloniaweb.models.dao.IUsuarioDao;
import com.springbootapp.coloniaweb.models.entity.Usuario;
import com.springbootapp.coloniaweb.models.services.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {

        return usuarioDao.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {

        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Usuario save(Usuario user) {

        return usuarioDao.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        usuarioDao.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {

        return usuarioDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        
        return usuarioDao.findAll(pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmailIgnoreCase(String email) {

        return usuarioDao.findByEmailIgnoreCase(email);
    }


	@Override
	public void deleteAllExpiredSince(Date now) {
		usuarioDao.deleteAllExpiredSince(now);
	}

}