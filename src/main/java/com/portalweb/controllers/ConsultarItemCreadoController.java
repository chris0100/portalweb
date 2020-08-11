/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LUIS CARLOS CABAL
 */
@Named(value = "consultarItemCreadoController")
@SessionScoped
public class ConsultarItemCreadoController implements Serializable {
    
    private String itemPatron;
    private int codItem;
    private String referencia;
    private String descripcion;
    private String undInventario;
    private String undOrden;
    private String ext1;
    private String descExt1;
    private String ext2;
    private String descExt2;
    private String codBarra;
    private String tipoExt1;
    private String tipoExt2;
    private BigDecimal disponible;
    private List<String> listaItemPatronConsulta;
    private List<String> listaExt1Consulta;
    private List<String> listaExt2Consulta;
    
    private int contar =0;
    
    public ConsultarItemCreadoController() {
    }

    public int getCodItem() {
        return codItem;
    }

    public void setCodItem(int codItem) {
        this.codItem = codItem;
    }

  

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUndInventario() {
        return undInventario;
    }

    public void setUndInventario(String undInventario) {
        this.undInventario = undInventario;
    }

    public String getUndOrden() {
        return undOrden;
    }

    public void setUndOrden(String undOrden) {
        this.undOrden = undOrden;
    }

    

   
    

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getDescExt1() {
        return descExt1;
    }

    public void setDescExt1(String descExt1) {
        this.descExt1 = descExt1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getDescExt2() {
        return descExt2;
    }

    public void setDescExt2(String descExt2) {
        this.descExt2 = descExt2;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public List<String> getListaItemPatronConsulta() {
        return listaItemPatronConsulta;
    }

    public void setListaItemPatronConsulta(List<String> listaItemPatronConsulta) {
        this.listaItemPatronConsulta = listaItemPatronConsulta;
    }

    public String getItemPatron() {
        return itemPatron;
    }

    public void setItemPatron(String itemPatron) {
        this.itemPatron = itemPatron;
    }

    public String getTipoExt1() {
        return tipoExt1;
    }

    public void setTipoExt1(String tipoExt1) {
        this.tipoExt1 = tipoExt1;
    }

    public String getTipoExt2() {
        return tipoExt2;
    }

    public void setTipoExt2(String tipoExt2) {
        this.tipoExt2 = tipoExt2;
    }

    public List<String> getListaExt1Consulta() {
        return listaExt1Consulta;
    }

    public void setListaExt1Consulta(List<String> listaExt1Consulta) {
        this.listaExt1Consulta = listaExt1Consulta;
    }

    public List<String> getListaExt2Consulta() {
        return listaExt2Consulta;
    }

    public void setListaExt2Consulta(List<String> listaExt2Consulta) {
        this.listaExt2Consulta = listaExt2Consulta;
    }

    public BigDecimal getDisponible() {
        return disponible;
    }

    public void setDisponible(BigDecimal disponible) {
        this.disponible = disponible;
    }

    public int getContar() {
        return contar;
    }

    public void setContar(int contar) {
        this.contar = contar;
    }
    
    
    public void consultarListaExt1() throws SQLException{
        listaExt1Consulta = new ArrayList<>();
        listaExt2Consulta = new ArrayList<>();
        codItem = Integer.parseInt(itemPatron.substring(0,7));
        obtenerListaExt1(codItem);
        obtenerListaExt2(codItem);
    }
    public void consultarListaExt2() throws SQLException{
        listaExt1Consulta = new ArrayList<>();
        ext1 = (getDescExt1().substring(0,4));
        obtenerListaExt2(codItem);
    }
    public void consultarItem() throws SQLException{
        try {
            if(!listaExt1Consulta.isEmpty()){
                ext1 =(getDescExt1().substring(0,4));
                ext2 =(getDescExt2().substring(0,5));
                descExt1 = getDescExt1().substring(5,getDescExt1().length()).trim();
                descExt2 = getDescExt2().substring(5,getDescExt2().length()).trim();
                obtenerDatosItem(codItem, ext1, ext2);
                obtenerDiponibleItem(codItem, ext1, ext2);
            }else{
                ext1 = "";
                ext2 = "";
                descExt1 = "";
                descExt2 = "";
                obtenerDatosItem(codItem);
                obtenerDiponibleItem(codItem);
            }
            if(contar==0){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ítem no encontrado", "El ítem no fue encontrado en la base de datos del UnoEE");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                recargarCampos();
            }
            contar = 0;
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ítem no encontrado", "El ítem no fue encontrado en la base de datos del UnoEE");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            recargarCampos();
        }
    }
    
    public List<String> completeItemPatron(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaItemPatronConsulta) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
     public List<String> completeExt2(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaExt2Consulta) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
    public void recargarCampos(){
        codBarra ="";
        itemPatron = "";
        descripcion ="";
        ext1="";
        ext2="";
        codItem =0;
        undInventario="";
        undOrden="";
        disponible = new BigDecimal(0);
    }
    public void recargarListas() throws SQLException{
        listaItemPatronConsulta = new ArrayList<>();
        listaExt1Consulta = new ArrayList<>();
        listaExt2Consulta = new ArrayList<>();
        obtenerListaItemPatron();
        recargarCampos();
        contar=0;
    }
    public String prepareList() throws SQLException{
        recargarListas();
        return "/usuario/consultarItemCreado/consultar";
    }
    
    public void obtenerListaItemPatron()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT CAST( RIGHT('0000000'+convert(varchar,isnull( f120_id ,'')),7)as nvarchar(7)) +' | ' + CAST( LEFT(f120_descripcion + '                                        ', 40)as nvarchar(40)) itemPatron FROM UnoEE.dbo.t120_mc_items t120 inner join UnoEE.dbo.t149_mc_tipo_inv_serv t149 ON t149.f149_id = t120.f120_id_tipo_inv_serv where t120.f120_id_cia = '001' ORDER BY f120_id ASC";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaItemPatronConsulta.add(rs.getString("itemPatron"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void obtenerListaExt1(int idItem)
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT DISTINCT [f117_id] +' - '+ [f117_descripcion] ext1 FROM [UnoEE].[dbo].[t117_mc_extensiones1_detalle] inner join v121 on v121.v121_id_ext1_detalle = f117_id and v121.v121_id_extension1 = f117_id_extension1 where v121.v121_id_item = "+idItem;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaExt1Consulta.add(rs.getString("ext1"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void obtenerListaExt2(int idItem)
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT DISTINCT [f119_id] +' - '+ [f119_descripcion] ext2 FROM [UnoEE].[dbo].[t119_mc_extensiones2_detalle] inner join v121 on v121.v121_id_ext2_detalle = f119_id and v121.v121_id_extension2 = f119_id_extension2 where v121.v121_id_item = "+idItem;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaExt2Consulta.add(rs.getString("ext2"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    public void obtenerDatosItem(int idItem, String ext1 , String ext2)
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "select v121_descripcion, v121_referencia, v121_id_unidad_inventario, v121_id_unidad_orden, v121_id_barras_principal from UnoEE.dbo.v121 where v121_id_item = "+idItem+" and v121_id_ext1_detalle = '"+ext1+"' and v121_id_ext2_detalle = '"+ext2+"' and v121_id_cia = '001'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                descripcion = rs.getString("v121_descripcion");
                referencia = rs.getString("v121_referencia");
                undInventario = rs.getString("v121_id_unidad_inventario");
                undOrden = rs.getString("v121_id_unidad_orden");
                codBarra = rs.getString("v121_id_barras_principal");
                contar ++;
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void obtenerDiponibleItem(int idItem, String ext1 , String ext2)
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT sum(v400_cant_disponible_1-v400_cant_pendiente_salir_1) disponible_real FROM [UnoEE].[dbo].[v400_item_ext] v400 inner join [UnoEE].[dbo].[t150_mc_bodegas] t150 on v400.v400_rowid_bodega = t150.f150_rowid inner join [UnoEE].[dbo].v121 on v121.v121_rowid_item_ext = v400_rowid_item_ext where v121.v121_id_item = "+idItem+" and v121.v121_id_ext1_detalle = '"+ext1+"' and v121.v121_id_ext2_detalle = '"+ext2+"' and v121.v121_id_cia = '001' and (t150.f150_id = '001' or t150.f150_id = '002')";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                disponible = rs.getBigDecimal("disponible_real");
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void obtenerDatosItem(int idItem)
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "select v121_descripcion, v121_referencia, v121_id_unidad_inventario, v121_id_unidad_orden, v121_id_barras_principal from UnoEE.dbo.v121 where v121_id_item = "+idItem +"and v121_id_cia = '001'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                descripcion = rs.getString("v121_descripcion");
                referencia = rs.getString("v121_referencia");
                undInventario = rs.getString("v121_id_unidad_inventario");
                undOrden = rs.getString("v121_id_unidad_orden");
                codBarra = rs.getString("v121_id_barras_principal");
                contar ++;
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void obtenerDiponibleItem(int idItem)
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT sum(v400_cant_disponible_1-v400_cant_pendiente_salir_1) disponible_real FROM [UnoEE].[dbo].[v400_item_ext] v400 inner join [UnoEE].[dbo].[t150_mc_bodegas] t150 on v400.v400_rowid_bodega = t150.f150_rowid inner join [UnoEE].[dbo].v121 on v121.v121_rowid_item_ext = v400_rowid_item_ext where v121.v121_id_item = "+idItem +"and v121.v121_id_cia = '001' and (t150.f150_id = '001' or t150.f150_id = '002')";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                disponible = rs.getBigDecimal("disponible_real");
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    
   
}
