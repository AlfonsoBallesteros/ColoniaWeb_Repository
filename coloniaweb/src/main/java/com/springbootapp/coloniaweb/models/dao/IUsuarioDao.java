package com.springbootapp.coloniaweb.models.dao;

import com.springbootapp.coloniaweb.models.entity.Usuario;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

   public Usuario findByUsername(String username);
   
   public Usuario findByEmailIgnoreCase(String email);
   
   @Modifying
   @Query(value = "DELETE FROM expacademics e USING usuarios u WHERE e.usuario_id = u.id AND u.expiry_registry <= ?1 AND u.verified = false;"
   		+ "DELETE FROM expjobs ej USING usuarios u WHERE ej.usuario_id = u.id AND u.expiry_registry <= ?1 AND u.verified = false;"
   		+ "DELETE FROM usuarios_networks un USING usuarios u WHERE un.usuario_id = u.id AND u.expiry_registry <= ?1 AND u.verified = false;"
   		+ "DELETE FROM usuarios_roles ur USING usuarios u WHERE ur.usuario_id = u.id AND u.expiry_registry <= ?1 AND u.verified = false;"
   		+ "DELETE FROM usuarios u WHERE u.expiry_registry <= ?1 AND u.verified = false", nativeQuery = true)
   void deleteAllExpiredSince(Date now);

} 