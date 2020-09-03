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

@Table(name = "expacademics")
@Entity
public class ExpAcademic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 150)
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 150)
    private String institute;

    @NotNull
    @ManyToOne
    @JsonBackReference
    private Usuario usuario;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 255)
    private String description;

    @NotNull
    @Past
    private Date entry;

    @Past
    private Date exit;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    public void prePersist() {
        Date date = new Date();
        this.createAt = date;
        this.updateAt = this.createAt;
    }

    public ExpAcademic() {
    }

    public ExpAcademic(String title, String institute, Date entry, Date exit, String description, Usuario usuario) {
        this.title = title.trim().toUpperCase();
        this.institute = institute.trim().toUpperCase();
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title.trim().toUpperCase();
    }

    public String getInstitute() {
        return this.institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute.trim().toUpperCase();
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(o instanceof ExpAcademic)) {
            return false;
        }
        ExpAcademic expAcademic = (ExpAcademic) o;
        return Objects.equals(id, expAcademic.id) && Objects.equals(title, expAcademic.title)
                && Objects.equals(institute, expAcademic.institute) && Objects.equals(entry, expAcademic.entry)
                && Objects.equals(exit, expAcademic.exit) && Objects.equals(description, expAcademic.description)
                && Objects.equals(createAt, expAcademic.createAt) && Objects.equals(updateAt, expAcademic.updateAt)
                && Objects.equals(usuario, expAcademic.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, institute, entry, exit, description, createAt, updateAt, usuario);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", title='" + getTitle() + "'" + ", institute='" + getInstitute() + "'"
                + ", entry='" + getEntry() + "'" + ", exit='" + getExit() + "'" + ", description='" + getDescription()
                + "'" + ", createAt='" + getCreateAt() + "'" + ", updateAt='" + getUpdateAt() + "'" + ", usuario='"
                + getUsuario() + "'" + "}";
    }

}