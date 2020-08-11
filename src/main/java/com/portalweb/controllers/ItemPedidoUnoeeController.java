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
import java.util.Date;

/**
 *
 * @author Lccabalr
 */
@Named(value = "itemPedidoUnoeeController")
@SessionScoped
public class ItemPedidoUnoeeController implements Serializable {

    private String codItem;
    private String descripcion;
    private String und;
    private String ext1;
    private String ext2;
    private BigDecimal cantPedida;
    private BigDecimal precioUnd;
    private BigDecimal valorBruto;
    private BigDecimal valorImp;
    private BigDecimal valorNeto;
    private Date fechaEntrega;
    
    public ItemPedidoUnoeeController() {
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnd() {
        return und;
    }

    public void setUnd(String und) {
        this.und = und;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public BigDecimal getCantPedida() {
        return cantPedida;
    }

    public void setCantPedida(BigDecimal cantPedida) {
        this.cantPedida = cantPedida;
    }

    public BigDecimal getPrecioUnd() {
        return precioUnd;
    }

    public void setPrecioUnd(BigDecimal precioUnd) {
        this.precioUnd = precioUnd;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getValorImp() {
        return valorImp;
    }

    public void setValorImp(BigDecimal valorImp) {
        this.valorImp = valorImp;
    }

    public BigDecimal getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(BigDecimal valorNeto) {
        this.valorNeto = valorNeto;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
}
