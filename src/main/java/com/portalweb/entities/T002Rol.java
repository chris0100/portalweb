/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lccabalr
 */
@Entity
@Table(name = "t002_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T002Rol.findAll", query = "SELECT t FROM T002Rol t")
    , @NamedQuery(name = "T002Rol.findByIdRol", query = "SELECT t FROM T002Rol t WHERE t.idRol = :idRol")
    , @NamedQuery(name = "T002Rol.findByNombre", query = "SELECT t FROM T002Rol t WHERE t.nombre = :nombre")})
public class T002Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_rol")
    private String idRol;
    @Size(max = 80)
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "t002RolList")
    private List<T001Usuario> t001UsuarioList;

    public T002Rol() {
    }

    public T002Rol(String idRol) {
        this.idRol = idRol;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<T001Usuario> getT001UsuarioList() {
        return t001UsuarioList;
    }

    public void setT001UsuarioList(List<T001Usuario> t001UsuarioList) {
        this.t001UsuarioList = t001UsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T002Rol)) {
            return false;
        }
        T002Rol other = (T002Rol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIdRol()+" - "+getNombre();
    }
    
}
