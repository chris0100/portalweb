/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
/**
 *
 * @author Lccabalr
 */
@Named(value = "ejecutarWebService")
@SessionScoped
public class EjecutarWebService implements Serializable {

    /**
     * Creates a new instance of EjecutarWebService
     */
    public EjecutarWebService() {
    }
    @Asynchronous
    public void  metodoLento() {

    try {
    Thread.sleep(10000);
    } catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }

    System.out.println("que lento va esto");

    } 
}
