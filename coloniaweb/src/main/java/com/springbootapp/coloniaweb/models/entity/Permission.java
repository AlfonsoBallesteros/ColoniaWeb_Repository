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

@Table(name = "permissions")
@Entity
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 100)
    private String description;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    public void prePersist() {
        Date date = new Date();
        this.createAt = date;
        this.updateAt = this.createAt;
    }

    public Permission() {
    }

    public Permission(String name, String description) {
        this.name = name.trim().toUpperCase();
        this.description = description.trim();
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
        if (!(o instanceof Permission)) {
            return false;
        }
        Permission permission = (Permission) o;
        return Objects.equals(id, permission.id) && Objects.equals(name, permission.name)
                && Objects.equals(description, permission.description) && Objects.equals(createAt, permission.createAt)
                && Objects.equals(updateAt, permission.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
                + ", createAt='" + getCreateAt() + "'" + ", updateAt='" + getUpdateAt() + "'" + "}";
    }

}