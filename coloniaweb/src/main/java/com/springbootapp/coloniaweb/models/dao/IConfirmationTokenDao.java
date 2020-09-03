package com.springbootapp.coloniaweb.models.dao;

import java.util.Date;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.springbootapp.coloniaweb.models.entity.ConfirmationToken;

public interface IConfirmationTokenDao extends JpaRepository<ConfirmationToken, String>{
	
	public ConfirmationToken findByConfirmationToken(String confirmationToken);
	
	Stream<ConfirmationToken> findAllByExpiryDateLessThan(Date now);
	void deleteByExpiryDateLessThan(Date now);
    @Modifying
    @Query("delete from ConfirmationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
