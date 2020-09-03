package com.springbootapp.coloniaweb.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Table(name = "expjobs")
@Entity
public class ExpJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 150)
    private String employment;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 150)
    private String company;

    @NotNull
    @Past
    private Date entry;

    @Past
    private Date exit;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 255)
    private String description;

    @NotNull
    @ManyToOne
    @JsonBackReference
    private Usuario usuario;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    public void prePersist() {
        Date date = new Date();
        this.createAt = date;
        this.updateAt = this.createAt;
    }

    public ExpJob() {
    }

    public ExpJob(String employment, String company, Date entry, Date exit, String description, Usuario usuario) {

        this.employment = employment.trim().toUpperCase();
        this.company = company.trim().toUpperCase();
        this.entry = entry;
        this.exit = exit;
        this.description = description.trim();
        this.usuario = usuario;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployment() {
        return this.employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment.trim().toUpperCase();
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company.trim().toUpperCase();
    }

    public Date getEntry() {
        return this.entry;
    }

    public void setEntry(Date entry) {
        this.entry = entry;
    }

    public Date getExit() {
        return this.exit;
    }

    public void setExit(Date exit) {
        this.exit = exit;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt =createAt;
    }

    public Date getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ExpJob)) {
            return false;
        }
        ExpJob expJob = (ExpJob) o;
        return Objects.equals(id, expJob.id) && Objects.equals(employment, expJob.employment)
                && Objects.equals(company, expJob.company) && Objects.equals(entry, expJob.entry)
                && Objects.equals(exit, expJob.exit) && Objects.equals(description, expJob.description)
                && Objects.equals(usuario, expJob.usuario) && Objects.equals(createAt, expJob.createAt)
                && Objects.equals(updateAt, expJob.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employment, company, entry, exit, description, usuario, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", employment='" + getEmployment() + "'" + ", company='" + getCompany()
                + "'" + ", entry='" + getEntry() + "'" + ", exit='" + getExit() + "'" + ", description='"
                + getDescription() + "'" + ", usuario='" + getUsuario() + "'" + ", createAt='" + getCreateAt() + "'"
                + ", updateAt='" + getUpdateAt() + "'" + "}";
    }

}