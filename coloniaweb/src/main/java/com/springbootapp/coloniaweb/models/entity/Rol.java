package com.springbootapp.coloniaweb.models.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "roles")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 100)
    private String description;

    @ManyToMany
    @NotNull
    @JsonBackReference
    private Set<Permission> permissions;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    public void prePersist() {
        Date date = new Date();
        this.createAt = date;
        this.updateAt = this.createAt;
    }

    public Rol() {
    }

    public Rol(String name, String description, Set<Permission> permissions) {
        this.name = name.trim().toUpperCase();
        this.description = description.trim();
        this.permissions = permissions;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
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
        if (!(o instanceof Rol)) {
            return false;
        }
        Rol rol = (Rol) o;
        return Objects.equals(id, rol.id) && Objects.equals(name, rol.name)
                && Objects.equals(description, rol.description) && Objects.equals(permissions, rol.permissions)
                && Objects.equals(createAt, rol.createAt) && Objects.equals(updateAt, rol.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, permissions, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
                + ", permissions='" + getPermissions() + "'" + ", createAt='" + getCreateAt() + "'" + ", updateAt='"
                + getUpdateAt() + "'" + "}";
    }

}