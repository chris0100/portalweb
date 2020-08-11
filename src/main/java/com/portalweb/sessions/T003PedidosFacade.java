/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.sessions;

import com.portalweb.entities.T003Pedidos;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T003PedidosFacade extends AbstractFacade<T003Pedidos> {

    @PersistenceContext(unitName = "PortalWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T003PedidosFacade() {
        super(T003Pedidos.class);
    }
    
    
    
    public String obtenerPuntoEnvio (String nit, String codigo, String punto){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerPuntoEnvio");
        query.setParameter("nit", nit);
        query.setParameter("codigo", codigo);
        query.setParameter("punto", punto);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           //now you have one array of Object for each row
           resultado = String.valueOf(obj[0]); // don't know the type of column CLIENT assuming String 
          
        }
        return resultado;
    } 
    
    
    
    public String obtenerConectorPedido (String idPedido){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerConectorPedido");
        query.setParameter("idPedido", idPedido);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        
        
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           //now you have one array of Object for each row
           resultado = resultado + String.valueOf(obj[0]); // don't know the type of column CLIENT assuming String 
          
        }
        return resultado;
    } 
    
    
    
    public List<String> consultarRowidNit(String nit){
        Query q= getEntityManager().createNamedQuery("execRowIdNit");
        q.setParameter("nit",nit);
        return q.getResultList();
    }
    
    
    public List<T003Pedidos> consultarPedidoNit(String nit){
        Query q= getEntityManager().createNamedQuery("T003Pedidos.findByNit");
        q.setParameter("nit",nit);
        return q.getResultList();
    }
    public List<T003Pedidos> consultarPedidoNitEstado(String nit, int estado){
        Query q= getEntityManager().createNamedQuery("T003Pedidos.findByNitEstado");
        q.setParameter("nit",nit);
        q.setParameter("estado",estado);
        return q.getResultList();
    }
    
    
    public List<T003Pedidos> consultarPedidoEstado( int estadoWB){
        Query q= getEntityManager().createNamedQuery("T003Pedidos.findByEstado");
        q.setParameter("estadoWB",estadoWB);
        return q.getResultList();
    }
    public List<T003Pedidos> consultarPedidoEstadoAsesor( int estadoWB, String terceroAsesor){
        Query q= getEntityManager().createNamedQuery("T003Pedidos.findByEstadoAsesor");
        q.setParameter("estadoWB",estadoWB);
        q.setParameter("terceroAsesor",terceroAsesor);
        return q.getResultList();
    }
    
    
    public List<T003Pedidos> consultarPedidoEstadoWB(String nit, int estadoWB){
        Query q= getEntityManager().createNamedQuery("T003Pedidos.findByEstadoNitWB");
        q.setParameter("nit",nit);
        q.setParameter("estadoWB",estadoWB);
        return q.getResultList();
    }
    
    
     public T003Pedidos findByOc(String oc, String nit){
          Query q= getEntityManager().createNamedQuery("T003Pedidos.findByOc");
          q.setParameter("oc",oc);
          q.setParameter("nit",nit);
          try {
            return (T003Pedidos) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
     
     
     
    public List<String> consultaListaPuntoEnvio (String nit, String sucursal){
        Query query = em.createNamedQuery("execListaPuntoEnvio");
        query.setParameter("nit",nit);
        query.setParameter("sucursal",sucursal);
        return query.getResultList(); 
    } 
}
