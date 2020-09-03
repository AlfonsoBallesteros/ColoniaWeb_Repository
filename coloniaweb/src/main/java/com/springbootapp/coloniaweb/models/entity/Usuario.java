	package com.springbootapp.coloniaweb.models.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int EXPIRATION = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 100)
    private String password;

    @Email
    @NotEmpty
    @Column(unique = true)
    @NotNull
    @Size(min = 4, max = 100)
    private String email;

    //@NotNull
    //@NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    //@NotNull
    //@NotEmpty
    @Size(min = 3, max = 50)
    private String lastname;

    //@NotNull
    //@NotEmpty
    @Column(unique = true)
    @Size(min = 4, max = 20)
    private String docId;

    @Past
    @NotNull
    private Date birthdate;

    //@NotNull
    //@NotEmpty
    @Column(unique = true)
    @Size(min = 5, max = 30)
    private String cellphone;

    //@NotNull
    //@NotEmpty
    @Size(min = 5, max = 50)
    private String ocupation;

    @Column(unique = true)
    private String avatar;

    @ManyToMany
    //@NotNull
    private Set<Rol> roles;

    @ManyToOne
    //@NotNull
    private Country country;

    @ManyToOne
    //@NotNull
    private DocType docType;

    @ManyToOne
    //@NotNull
    private Gender gender;

    @OneToMany(mappedBy = "usuario")
    private List<ExpAcademic> expAcademics;

    @OneToMany(mappedBy = "usuario")
    private List<ExpJob> expJobs;

    @OneToMany(mappedBy = "usuario")
    private List<UserNetwork> userNetworks;

    private boolean enable;
    
    private boolean verified;

    private Date createAt;

    private Date updateAt;
    
    private Date expiryRegistry;

    @PrePersist
    public void prePersist() {
        Date date = new Date();
        this.createAt = date;
        this.updateAt = this.createAt;
        this.enable = true;
        this.verified = false;
        this.expiryRegistry = calculateExpiryDate(EXPIRATION);
    }

    public Usuario() {
    }


    public Usuario(String username, String password, String email, String name, String lastname, String docId,

            Date birthdate, String cellphone, String ocupation, String avatar, Set<Rol> roles, Country country,
            DocType docType, Gender gender ) {

        this.username = username.trim();
        this.password = password;
        this.email = email.trim();
        this.name = name.trim().toUpperCase();
        this.lastname = lastname.trim().toUpperCase();
        this.docId = docId.trim();
        this.birthdate = birthdate;
        this.cellphone = cellphone.trim();
        this.ocupation = ocupation.trim().toUpperCase();
        this.avatar = avatar;
        this.roles = roles;
        this.country = country;
        this.docType = docType;
        this.gender = gender;
        
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.trim().toUpperCase();
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.trim().toUpperCase();
    }

    public String getDocId() {
        return this.docId;
    }

    public void setDocId(String docId) {
        this.docId = docId.trim();
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCellphone() {
        return this.cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone.trim();
    }

    public String getOcupation() {
        return this.ocupation;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation.trim().toUpperCase();
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<Rol> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public DocType getDocType() {
        return this.docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public List<ExpAcademic> getExpAcademics() {
        return this.expAcademics;
    }

    public void setExpAcademics(List<ExpAcademic> expAcademics) {
        this.expAcademics = expAcademics;
    }

    public List<ExpJob> getExpJobs() {
        return this.expJobs;
    }

    public void setExpJobs(List<ExpJob> expJobs) {
        this.expJobs = expJobs;
    }

    public List<UserNetwork> getUserNetworks() {
        return this.userNetworks;
    }

    public void setUserNetworks(List<UserNetwork> userNetworks) {
        this.userNetworks = userNetworks;
    }

    public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
    public Date getExpiryRegistry() {
		return expiryRegistry;
	}

	public void setExpiryRegistry(Date expiryRegistry) {
		this.expiryRegistry = expiryRegistry;
	}
	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
	    final Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new Date().getTime());
	    cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	    return new Date(cal.getTime().getTime());
	}
	
	@Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario user = (Usuario) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username)
                && Objects.equals(password, user.password) && Objects.equals(email, user.email)
                && Objects.equals(name, user.name) && Objects.equals(lastname, user.lastname)
                && Objects.equals(docId, user.docId) && Objects.equals(birthdate, user.birthdate)
                && Objects.equals(cellphone, user.cellphone) && Objects.equals(ocupation, user.ocupation)
                && Objects.equals(avatar, user.avatar) && Objects.equals(roles, user.roles)
                && Objects.equals(country, user.country) && Objects.equals(docType, user.docType)
                && Objects.equals(gender, user.gender) && Objects.equals(expAcademics, user.expAcademics)
                && Objects.equals(expJobs, user.expJobs) && Objects.equals(userNetworks, user.userNetworks)
                && enable == user.enable && verified == user.verified 
                && Objects.equals(createAt, user.createAt)
                && Objects.equals(updateAt, user.updateAt)
                && Objects.equals(expiryRegistry, user.expiryRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, name, lastname, docId, birthdate, cellphone, ocupation,
                avatar, roles, country, docType, gender, expAcademics, expJobs, userNetworks, enable, verified, createAt,
                updateAt, expiryRegistry);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", username='" + getUsername() + "'" + ", password='" + getPassword()
                + "'" + ", email='" + getEmail() + "'" + ", name='" + getName() + "'" + ", lastname='" + getLastname()
                + "'" + ", docId='" + getDocId() + "'" + ", birthdate='" + getBirthdate() + "'" + ", cellphone='"
                + getCellphone() + "'" + ", ocupation='" + getOcupation() + "'" + ", avatar='" + getAvatar() + "'"
                + ", roles='" + getRoles() + "'" + ", country='" + getCountry() + "'" + ", docType='" + getDocType()
                + "'" + ", gender='" + getGender() + "'" + ", expAcademics='" + getExpAcademics() + "'" + ", expJobs='"
                + getExpJobs() + "'" + ", userNetworks='" + getUserNetworks() + "'" + ", enable='" + isEnable() + ", verified='"
                + isVerified()+"'" +", createAt='" + getCreateAt() + "'" + ", updateAt='" + getUpdateAt() + "'" + ", expiryRegistry='" + getExpiryRegistry() + "'" + "}";
    }
    
}