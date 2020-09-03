package com.springbootapp.coloniaweb.models.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {
	
	private static final int EXPIRATION = 10;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    private Date expiryDate;
    
    private boolean enabled;
    
    @OneToOne(cascade = {CascadeType.ALL},targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private Usuario usuario;
 
    public ConfirmationToken() {
    	
    }
    @PrePersist
    public void prePersist() {
    	this.setEnabled(true);
    }
    public ConfirmationToken(Usuario usuario) {
        this.usuario = usuario;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

	public long getTokenid() {
		return tokenid;
	}

	public void setTokenid(long tokenid) {
		this.tokenid = tokenid;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	 public Date getExpiryDate() {
	    return expiryDate;
	}

	public void setExpiryDate(final Date expiryDate) {
	    this.expiryDate = expiryDate;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled= enabled;
	}

	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
	    final Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new Date().getTime());
	    cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	    return new Date(cal.getTime().getTime());
	}
	public void updateToken(final String token) {
	    this.confirmationToken = token;
	    this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	@Override
	public int hashCode() {
	    final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmationToken == null) ? 0 : confirmationToken.hashCode());
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfirmationToken other = (ConfirmationToken) obj;
		if (confirmationToken == null) {
			if (other.confirmationToken != null)
				return false;
		} else if (!confirmationToken.equals(other.confirmationToken))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
		}
	@Override
	public String toString() {
	final StringBuilder builder = new StringBuilder();
	  builder.append("Token [String=").append(confirmationToken).append("]").append("[Expires").append(expiryDate).append("]");
	  return builder.toString();
	}

	


}
