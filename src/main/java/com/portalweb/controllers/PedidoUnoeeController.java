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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lccabalr
 */
@Named(value = "pedidoUnoeeController")
@SessionScoped
public class PedidoUnoeeController implements Serializable {
    private int rowid;
    private String numeroDocumento;
    private String razonSocialCliente;
    private String estado;
    private String notas;
    private String ordenCompra;
    private BigDecimal valorBrutoDocumento;
    private BigDecimal valorImpDocumento;
    private BigDecimal valorNetoDocumento;
    private String direccion1;
    private Date fecha;
    private Date fechaEntrega;
    private String contacto;
    private String vendedor;
    private String sucursalFactura;
    private String sucursalDespacho;
    private String barrio;
    private String puntoEnvio;
    private String idPuntoEnvio;
    
    
    private List<PedidoUnoeeController> listaPedidosUnoee = new ArrayList<>();
    public PedidoUnoeeController() {
    }

    public String getRazonSocialCliente() {
        return razonSocialCliente;
    }

    public void setRazonSocialCliente(String razonSocialCliente) {
        this.razonSocialCliente = razonSocialCliente;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public List<PedidoUnoeeController> getListaPedidosUnoee() {
        return listaPedidosUnoee;
    }

    public void setListaPedidosUnoee(List<PedidoUnoeeController> listaPedidosUnoee) {
        this.listaPedidosUnoee = listaPedidosUnoee;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(String ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public BigDecimal getValorBrutoDocumento() {
        return valorBrutoDocumento;
    }

    public void setValorBrutoDocumento(BigDecimal valorBrutoDocumento) {
        this.valorBrutoDocumento = valorBrutoDocumento;
    }

    public BigDecimal getValorImpDocumento() {
        return valorImpDocumento;
    }

    public void setValorImpDocumento(BigDecimal valorImpDocumento) {
        this.valorImpDocumento = valorImpDocumento;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public BigDecimal getValorNetoDocumento() {
        return valorNetoDocumento;
    }

    public void setValorNetoDocumento(BigDecimal valorNetoDocumento) {
        this.valorNetoDocumento = valorNetoDocumento;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getSucursalFactura() {
        return sucursalFactura;
    }

    public void setSucursalFactura(String sucursalFactura) {
        this.sucursalFactura = sucursalFactura;
    }

    public String getSucursalDespacho() {
        return sucursalDespacho;
    }

    public void setSucursalDespacho(String sucursalDespacho) {
        this.sucursalDespacho = sucursalDespacho;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getPuntoEnvio() {
        return puntoEnvio;
    }

    public void setPuntoEnvio(String puntoEnvio) {
        this.puntoEnvio = puntoEnvio;
    }

    public String getIdPuntoEnvio() {
        return idPuntoEnvio;
    }

    public void setIdPuntoEnvio(String idPuntoEnvio) {
        this.idPuntoEnvio = idPuntoEnvio;
    }
    
    @Override
    public String toString() {
        return getRazonSocialCliente()+"-"+getNotas();
    }
}
