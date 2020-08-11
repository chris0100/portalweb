/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.sessions;

import com.portalweb.entities.T003Pedidos;
import com.portalweb.entities.T004ItemPedido;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T004ItemPedidoFacade extends AbstractFacade<T004ItemPedido> {

    @PersistenceContext(unitName = "PortalWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    public T004ItemPedidoFacade() {
        super(T004ItemPedido.class);
    }
    
    
    public List<T004ItemPedido> consultarItemPedido(T003Pedidos idPedido){
        Query q= getEntityManager().createNamedQuery("T004ItemPedido.findByIdPedido");
        q.setParameter("idPedido",idPedido);
        return q.getResultList();
    }
    
    
    public List<String> consultaExt1 (String tipoExt){
        Query query = em.createNamedQuery("execListaExt1");
        query.setParameter("tipoExt", tipoExt);
        return query.getResultList();
    } 
    
    
    
    public List<String> consultaExt2 (String tipoExt){
        Query query = em.createNamedQuery("execListaExt2");
        query.setParameter("tipoExt", tipoExt);
        return query.getResultList();
    } 
    
    
    public List<String> consultaUnidadInventario (int codItem){
        Query query = em.createNamedQuery("execUnidadInventario");
        query.setParameter("codItem", codItem);
        return query.getResultList();
    } 
    
    
    public List<String> consultaListaItemPatron (String nit){
        Query query = em.createNamedQuery("execListaItemPatron");
        query.setParameter("nit", nit);
        return query.getResultList(); 
    }  
    
    
    public List<String> consultaExt1Q (int codigo, String nit){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execListaExt1");
        String codItem=String.valueOf(codigo);
        query.setParameter("codItem", codItem);
        query.setParameter("nit", nit);
        List<String> resultado = new ArrayList<>();
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado.add(String.valueOf(obj[0])); 
          
        }        
        return resultado; 
    } 
    
    
    public List<String> consultaExt2Q (int codigo,String ext1, String nit){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execListaExt2");
        String codItem=String.valueOf(codigo);
        query.setParameter("codItem", codItem);
        query.setParameter("ext1", ext1);
        query.setParameter("nit", nit);
        List<String> resultado = new ArrayList<>();
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado.add(String.valueOf(obj[0])); 
          
        }        
        return resultado; 
    } 
    
    
    
    public List<String> itemCliente (String nit){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execItemCliente");
        query.setParameter("nit", nit);
        List<String> resultado = new ArrayList<>();
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado.add(String.valueOf(obj[0])); 
        }        
        return resultado; 
    } 
    
    
    public String homologarItem (String nit, String codigo){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execHomologarItem");
        query.setParameter("nit", nit);
        query.setParameter("codigo", codigo);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        
        //Itera la lista del resultado para guardarlo en una cadena de String.
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado = String.valueOf(obj[0]); 
          
        }
        //System.out.println("resultado de homologar item:" + resultado);
        
        return resultado;
    } 
    
    
    //PARTE NUEVA
    public String itemPorCodBarras(String codigoBarras){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerItemCodBarras");
        query.setParameter("codigo_barras", codigoBarras);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado = String.valueOf(obj[0]); 
        }
        return resultado; 
    }
    
    
            
            
            
            
    
    public String consultarItemCodBarra (String codigo){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execConsultarItemCodBarra");
        query.setParameter("codigo", codigo);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado = String.valueOf(obj[0]); 
        }
        return resultado;
    }
    
    
    
    public int contarHomologarItemDuplicado (String nit, String codigo){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execHomologarItem");
        query.setParameter("nit", nit);
        query.setParameter("codigo", codigo);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        int contar = 0;
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado = String.valueOf(obj[0]); 
           contar ++;
        }
        return contar;
    } 
    
    
    
    //PARTE NUEVA
    public int contarCodigoBarrasItem(String codigoBarras){
               StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerItemCodBarras");
        query.setParameter("codigo_barras", codigoBarras);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        int contar = 0;
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado = String.valueOf(obj[0]); 
           contar ++;
        }
        return contar; 
    }
    
    
    
    public int contarHomologarItem (String nit){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execContarHomologarItem");
        query.setParameter("nit", nit);
        
        String resultado="";
        List<Object> result = query.getResultList();
        
        Iterator itr = result.iterator();
        int contar = 0;
        
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado = String.valueOf(obj[0]); 
           contar ++;
        }
        
        return contar;
    } 
    
    
    
    public List<String> consultaDecimalesUnidadInventario (String unidad){
        Query query = em.createNamedQuery("execDecimalesUnidadInventario");
        query.setParameter("unidad", unidad);
        return query.getResultList();
    }
    
    
    
     public List<String> consultaDescripcionLabelExt1 (String id){
        Query query = em.createNamedQuery("execDescripcionLabelExt1");
        query.setParameter("id", id);
        return query.getResultList();
    }
     
     
     
    public List<String> consultaDescripcionLabelExt2 (String id){
        Query query = em.createNamedQuery("execDescripcionLabelExt2");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    
    
    public List<T004ItemPedido> topPedidos (String nit){
        Query query = em.createNamedQuery("execTopItemPedido");
        query.setParameter("nit", nit);
        return query.getResultList(); 
    } 
    
    
    
    public void spEjecutarSincronizarItems (String idPedido, String nit){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEjecutarSincronizarItems");
        query.setParameter("idPedido", idPedido);
        query.setParameter("nit", nit);
        query.execute();
    }
    
    
    
    public List<String> consultaTipoExtensiones (int idItem){
        Query query = em.createNamedQuery("execTipoExtensiones");
        query.setParameter("idItem", idItem);
        return query.getResultList(); 
    }
    
    
    
    public String obtenerPrecioItemEcuador (String codItem, String nit, String unidadMedia){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerPrecio");
        query.setParameter("codItem", codItem);
        query.setParameter("nit", nit);
        query.setParameter("unidadMedia", unidadMedia);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        int contar = 0;
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado = String.valueOf(obj[0]); 
        }
        return resultado;
    } 
    
    
    
    public Date obtenerFechaPrecioItemEcuador (String codItem, String nit, String unidadMedia){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerFechaPrecio");
        query.setParameter("codItem", codItem);
        query.setParameter("nit", nit);
        query.setParameter("unidadMedia", unidadMedia);
        Date resultado = null;
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        int contar = 0;
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           resultado =(Date)obj[0]; 
        }
        return resultado;
    } 
}
