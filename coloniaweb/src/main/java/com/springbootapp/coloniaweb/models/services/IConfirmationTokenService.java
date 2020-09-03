package com.springbootapp.coloniaweb.models.services;

import java.util.Date;

import com.springbootapp.coloniaweb.models.entity.ConfirmationToken;



public interface IConfirmationTokenService {
	public ConfirmationToken findByConfirmationToken(String confirmationToken);
	public ConfirmationToken save(ConfirmationToken token);
	public String validatePasswordResetToken(String token);
	void deleteAllExpiredSince(Date now);
}
