/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.sessions;

import com.portalweb.entities.T002Rol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T002RolFacade extends AbstractFacade<T002Rol> {

    @PersistenceContext(unitName = "PortalWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T002RolFacade() {
        super(T002Rol.class);
    }
    public List<T002Rol> consultaRol(){
        Query q= getEntityManager().createNamedQuery("T002Rol.findAll");
        return q.getResultList();
    }
    public List<T002Rol> consultaRolCalidad(){
        Query q= getEntityManager().createNamedQuery("Roles.findByIdRol");
        q.setParameter("idRol", "CALIDAD");
        return q.getResultList();
    }
}
