package com.springbootapp.coloniaweb.models.implementations;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springbootapp.coloniaweb.models.dao.IConfirmationTokenDao;
import com.springbootapp.coloniaweb.models.entity.ConfirmationToken;
import com.springbootapp.coloniaweb.models.services.IConfirmationTokenService;
@Service
public class ConfirmationTokenImpl implements IConfirmationTokenService{
	
	@Autowired
	private IConfirmationTokenDao TokenDao;
	
	@Override
	@Transactional(readOnly = true)
	    public ConfirmationToken findByConfirmationToken(String confirmationToken) {

	        return TokenDao.findByConfirmationToken(confirmationToken);
	    }
	@Override
	public ConfirmationToken save(ConfirmationToken token) {
		return TokenDao.save(token);
		
	}
	@Override
	public void deleteAllExpiredSince(Date now) {
		 TokenDao.deleteAllExpiredSince(now);
	}

	/*
	 *Con éste método validamos el token, si no se encuentra, expiró o si fue usado 
	 **/
	
	  @Override
	    public String validatePasswordResetToken(String token) {
	        final ConfirmationToken passToken = TokenDao.findByConfirmationToken(token);

	        return !isTokenFound(passToken) ? "invalidToken"
	                : isTokenExpired(passToken) ? "expired"
	                : !passToken.isEnabled() ? "TokenUsed"
	                :null;
	    }

	    private boolean isTokenFound(ConfirmationToken passToken) {
	        return passToken != null;
	    }

	    private boolean isTokenExpired(ConfirmationToken passToken) {
	        final Calendar cal = Calendar.getInstance();
	        return passToken.getExpiryDate().before(cal.getTime());
	    }
		
		


		
}
