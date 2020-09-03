package com.springbootapp.coloniaweb.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "usuarios_networks")
public class UserNetwork implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @NotNull
    private Network network;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 5, max = 255)
    private String url;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    public void PrePersist() {
        Date date = new Date();
        this.createAt = date;
        this.updateAt = this.createAt;
    }

    public UserNetwork() {
    }

    public UserNetwork(Usuario usuario, Network network, String url) {
        this.usuario = usuario;
        this.network = network;
        this.url = url;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Network getNetwork() {
        return this.network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(o instanceof UserNetwork)) {
            return false;
        }
        UserNetwork usuarioNetwork = (UserNetwork) o;
        return Objects.equals(id, usuarioNetwork.id) && Objects.equals(usuario, usuarioNetwork.usuario)
                && Objects.equals(network, usuarioNetwork.network) && Objects.equals(url, usuarioNetwork.url)
                && Objects.equals(createAt, usuarioNetwork.createAt) && Objects.equals(updateAt, usuarioNetwork.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, network, url, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", usuario='" + getUsuario() + "'" + ", network='" + getNetwork() + "'"
                + ", url='" + getUrl() + "'" + ", createAt='" + getCreateAt() + "'" + ", updateAt='" + getUpdateAt()
                + "'" + "}";
    }

}