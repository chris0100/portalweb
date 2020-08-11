/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.sessions;

import com.portalweb.entities.T001Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T001UsuarioFacade extends AbstractFacade<T001Usuario> {

    @PersistenceContext(unitName = "PortalWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T001UsuarioFacade() {
        super(T001Usuario.class);
    }
    public T001Usuario findByUsername(String query){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByUsername");
          q.setParameter("username",query);
          try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public List<String> findByValidarUsername(String query){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByUsername");
          q.setParameter("username",query);
          return q.getResultList();
    }
    public List<String> nitUsuarioNuevo(int nit) {
        Query q= getEntityManager().createNamedQuery("execValidarNitUsuarioNuevo");
        q.setParameter("nit", nit);
        return q.getResultList();
    }
    public List<String> nitContraseñaUsuarioNuevo(String nit, String contrasena) {
        Query q= getEntityManager().createNamedQuery("execRazonSocialUsuarioNuevo");
        q.setParameter("nit", nit);
        q.setParameter("contrasena", contrasena);
        return q.getResultList();
    }
    public List<String> nitContraseñaUsuarioNuevoProveedor(String nit, String contrasena) {
        Query q= getEntityManager().createNamedQuery("execRazonSocialUsuarioNuevoProveedor");
        q.setParameter("nit", nit);
        q.setParameter("contrasena", contrasena);
        return q.getResultList();
    }
    public List<String> validarEntidadDinamicaCliente(String nit, String sucursal) {
        Query q= getEntityManager().createNamedQuery("execEntidadDinamicaLoginCliente");
        q.setParameter("nit", nit);
        q.setParameter("sucursal", sucursal);
        return q.getResultList();
    }
    public List<String> validarEntidadDinamicaProveedor(String nit, String sucursal) {
        Query q= getEntityManager().createNamedQuery("execEntidadDinamicaLoginProveedor");
        q.setParameter("nit", nit);
        q.setParameter("sucursal", sucursal);
        return q.getResultList();
    }
    
    public List<String> listaSucursalNit(String nit) {
        Query q= getEntityManager().createNamedQuery("execSucursalNit");
        q.setParameter("nit", nit);
        return q.getResultList();
    }
    public List<String> listaPuntoEnvio (String nit, String sucursal){
        Query q= getEntityManager().createNamedQuery("execListaPuntoEnvio");
        q.setParameter("nit",nit);
        q.setParameter("sucursal",sucursal);
        return q.getResultList(); 
    } 
    public T001Usuario findByNitSucursalPuntoEnvio(String nit, String idSucursal, String idPuntoEnvio){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByNitSucursalPuntoEnvio");
          q.setParameter("nit",nit);
          q.setParameter("idSucursal",idSucursal);
          q.setParameter("idPuntoEnvio",idPuntoEnvio);
          try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public T001Usuario findByNit(String nit){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByNit");
          q.setParameter("nit",nit);
          try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public T001Usuario findByCorreoEditar(String correo, String correoEditar){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByCorreoEditar");
          q.setParameter("correo",correo);
          q.setParameter("correoEditar",correoEditar);
          try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public T001Usuario findByCorreo(String correo){
        Query q= getEntityManager().createNamedQuery("T001Usuario.findByCorreo");
        q.setParameter("correo",correo);
        try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public T001Usuario findByCorreoRecuperarContra(String correo, String nit, String idSucursal, String idPuntoEnvio){
        Query q= getEntityManager().createNamedQuery("T001Usuario.findByCorreoRecuperarContra");
        q.setParameter("correo",correo);
        q.setParameter("nit",nit);
        q.setParameter("idSucursal",idSucursal);
        q.setParameter("idPuntoEnvio",idPuntoEnvio);
        try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public T001Usuario findByCorreoRecuperarContraProveedor(String correo, String username){
        Query q= getEntityManager().createNamedQuery("T001Usuario.findByCorreoRecuperarContraProveedor");
        q.setParameter("correo",correo);
        q.setParameter("username",username);
        try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    
    
    public T001Usuario findByToken(String token){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByToken");
          q.setParameter("token",token);
          try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public int findById(String nit, String idSucursal){
          Query q= getEntityManager().createNamedQuery("T005UsuarioSucursal.findByIdUsuarioSucursal");
          q.setParameter("nit",nit);
          q.setParameter("idSucursal",idSucursal);
          return (int) q.getSingleResult();
    }
}
