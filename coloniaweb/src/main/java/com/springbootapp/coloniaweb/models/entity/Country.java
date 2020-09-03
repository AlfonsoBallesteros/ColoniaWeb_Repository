package com.springbootapp.coloniaweb.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "countries")
@Entity
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 2, max = 20)
    private String prefix;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    public void prePersist() {

        Date date = new Date();
        this.createAt = date;
        this.updateAt = this.createAt;

    }

    public Country() {
    }

    public Country(String name, String prefix) {
        this.name = name.trim().toUpperCase();
        this.prefix = prefix.trim();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.trim().toUpperCase();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix.trim();
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Country)) {
            return false;
        }
        Country country = (Country) o;
        return Objects.equals(id, country.id) && Objects.equals(name, country.name)
                && Objects.equals(prefix, country.prefix) && Objects.equals(createAt, country.createAt)
                && Objects.equals(updateAt, country.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, prefix, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", prefix='" + getPrefix() + "'"
                + ", createAt='" + getCreateAt() + "'" + ", updateAt='" + getUpdateAt() + "'" + "}";
    }

}