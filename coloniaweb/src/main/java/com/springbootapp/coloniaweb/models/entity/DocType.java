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

@Table(name = "doctypes")
@Entity
public class DocType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 2, max = 100)
    private String name;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    public void prePersist() {

        Date date = new Date();
        this.createAt = date;
        this.updateAt = createAt;
    }

    public DocType() {
    }

    public DocType(String name) {
        this.name = name.trim().toUpperCase();
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
        if (!(o instanceof DocType)) {
            return false;
        }
        DocType docType = (DocType) o;
        return Objects.equals(id, docType.id) && Objects.equals(name, docType.name)
                && Objects.equals(createAt, docType.createAt) && Objects.equals(updateAt, docType.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", createAt='" + getCreateAt() + "'"
                + ", updateAt='" + getUpdateAt() + "'" + "}";
    }

}