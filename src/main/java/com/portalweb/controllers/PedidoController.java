/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.controllers;

import com.portalweb.entities.T001Usuario;
import com.portalweb.entities.T003Pedidos;
import com.portalweb.entities.T004ItemPedido;
import com.portalweb.sessions.T001UsuarioFacade;
import com.portalweb.sessions.T003PedidosFacade;
import com.portalweb.sessions.T004ItemPedidoFacade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.apache.axis.message.MessageElement;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.tempuri.ImportarXMLResponseImportarXMLResult;
import org.tempuri.WSUNOEESoapProxy;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;

import javax.xml.rpc.holders.ShortHolder;

import static com.portalweb.controller.util.OrderPedidoItem.*;

/**
 * @author Lccabalr
 */
@Named(value = "pedidoController")
@SessionScoped
public class PedidoController implements Serializable {

    @EJB
    private T004ItemPedidoFacade itemPedidoFacade;
    private T004ItemPedido itemPedidoActual;
    private T004ItemPedido itemPedidoTable;
    private List<T004ItemPedido> listaItemPedido = new ArrayList<>();
    private List<T004ItemPedido> listaItemPedidoSelect = new ArrayList<>();
    @EJB
    private T003PedidosFacade pedidoFacade;
    private T003Pedidos pedidoActual;
    private List<T003Pedidos> listaPedido = new ArrayList<>();


    //EJB realiza la inyeccion del usuarioRepository
    @EJB
    private T001UsuarioFacade usuarioFacade;
    private T001Usuario usuarioActual;
    private List<T001Usuario> listaUsuario = new ArrayList<>();


    private String ext1;
    private String ext2;
    private String itemPatron;

    private String tipoExt1;
    private String tipoExt2;

    private List<String> listaExt1;
    private List<String> listaExt2;
    private List<String> listaItemPatron;
    private List<String> listaTipoInventario;
    private List<String> listaPuntoEnvio;

    private String codigoCliente = "";
    private String itemHomologado = "";


    private String cadenaCaracteresText1 = "";
    private int itemsCargadosPlano;
    private String decimalesUnidadMedida;
    private boolean editarPedido;

    private String mensajeConector = "";
    private int valorConector = 0;
    private int contarItemHomologado;

    private String descripcionLabelExt1;
    private String descripcionLabelExt2;

    private EmailController emailController;
    private String p1CodigoPedido;
    private String p2UsuarioPedido;
    private String p3Oc;
    private String p4TablaItems;
    private String p5PuntoEnvio;
    private String p6mensajeErrorWb;
    private String p7razonSocialCliente;
    private String correoAsesor;
    private String nombreAsesor;
    private String nitAsesor;

    private PedidoUnoeeController pedidoUnoee;
    private List<PedidoUnoeeController> listaPedidosUnoee = new ArrayList<>();
    private String rowIdCliente;
    private Date fechaParametro1;
    private Date fechaParametro2;

    private ItemPedidoUnoeeController itemPedidoUnoee;
    private List<ItemPedidoUnoeeController> listaItemPedidosUnoee = new ArrayList<>();

    private List<String> listaItemCliente;
    private boolean estadoArchivoXls;
    private String precioUnoEE;

    public PedidoController() {
    }

    public T004ItemPedidoFacade getItemPedidoFacade() {
        return itemPedidoFacade;
    }

    public void setItemPedidoFacade(T004ItemPedidoFacade itemPedidoFacade) {
        this.itemPedidoFacade = itemPedidoFacade;
    }

    public T004ItemPedido getItemPedidoActual() {
        return itemPedidoActual;
    }

    public void setItemPedidoActual(T004ItemPedido itemPedidoActual) {
        this.itemPedidoActual = itemPedidoActual;
    }

    public List<T004ItemPedido> getListaItemPedido() {
        return listaItemPedido = getItemPedidoFacade().consultarItemPedido(pedidoActual);
    }

    public void setListaItemPedido(List<T004ItemPedido> listaItemPedido) {
        this.listaItemPedido = listaItemPedido;
    }

    public T003PedidosFacade getPedidoFacade() {
        return pedidoFacade;
    }

    public void setPedidoFacade(T003PedidosFacade pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

    public T003Pedidos getPedidoActual() {
        return pedidoActual;
    }

    public void setPedidoActual(T003Pedidos pedidoActual) {
        this.pedidoActual = pedidoActual;
    }

    public void setListaPedido(List<T003Pedidos> listaPedido) {
        this.listaPedido = listaPedido;
    }

    //Listas de pedidos
    public List<T003Pedidos> getListaPedidoElaboracionNit() {
        return listaPedido = getPedidoFacade().consultarPedidoNitEstado(usuarioActual.getNit(), 0);
    }

    public List<T003Pedidos> getListaPedidoVerificacionNit() {
        return listaPedido = getPedidoFacade().consultarPedidoEstadoWB(usuarioActual.getNit(), 1);
    }

    public List<T003Pedidos> getListaPedidoError() {
        return listaPedido = getPedidoFacade().consultarPedidoEstado(1);
    }

    public List<T003Pedidos> getListaPedidoErrorAsesor() {
        return listaPedido = getPedidoFacade().consultarPedidoEstadoAsesor(1, usuarioActual.getNit());
    }

    public List<T003Pedidos> getListaItemPedidoHistorialUsuario() {
        return listaPedido;
    }

    public List<T003Pedidos> getListaPedido() {
        return listaPedido;
    }

    public T001UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(T001UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public List<T001Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<T001Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public List<T004ItemPedido> getListaItemPedidoSelect() {
        return listaItemPedidoSelect;
    }

    public void setListaItemPedidoSelect(List<T004ItemPedido> listaItemPedidoSelect) {
        this.listaItemPedidoSelect = listaItemPedidoSelect;
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

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getItemHomologado() {
        return itemHomologado;
    }

    public void setItemHomologado(String itemHomologado) {
        this.itemHomologado = itemHomologado;
    }

    public String getCadenaCaracteresText1() {
        return cadenaCaracteresText1;
    }

    public void setCadenaCaracteresText1(String cadenaCaracteresText1) {
        this.cadenaCaracteresText1 = cadenaCaracteresText1;
    }

    public int getItemsCargadosPlano() {
        return itemsCargadosPlano;
    }

    public void setItemsCargadosPlano(int itemsCargadosPlano) {
        this.itemsCargadosPlano = itemsCargadosPlano;
    }

    public String getDecimalesUnidadMedida() {
        return decimalesUnidadMedida;
    }

    public void setDecimalesUnidadMedida(String decimalesUnidadMedida) {
        this.decimalesUnidadMedida = decimalesUnidadMedida;
    }

    public T004ItemPedido getItemPedidoTable() {
        return itemPedidoTable;
    }

    public void setItemPedidoTable(T004ItemPedido itemPedidoTable) {
        this.itemPedidoTable = itemPedidoTable;
    }

    public String getDescripcionLabelExt1() {
        return descripcionLabelExt1;
    }

    public void setDescripcionLabelExt1(String descripcionLabelExt1) {
        this.descripcionLabelExt1 = descripcionLabelExt1;
    }

    public String getDescripcionLabelExt2() {
        return descripcionLabelExt2;
    }

    public void setDescripcionLabelExt2(String descripcionLabelExt2) {
        this.descripcionLabelExt2 = descripcionLabelExt2;
    }

    public PedidoUnoeeController getPedidoUnoee() {
        return pedidoUnoee;
    }

    public void setPedidoUnoee(PedidoUnoeeController pedidoUnoee) {
        this.pedidoUnoee = pedidoUnoee;
    }

    public List<PedidoUnoeeController> getListaPedidosUnoee() {
        return listaPedidosUnoee;
    }

    public void setListaPedidosUnoee(List<PedidoUnoeeController> listaPedidosUnoee) {
        this.listaPedidosUnoee = listaPedidosUnoee;
    }

    public Date getFechaParametro1() {
        return fechaParametro1;
    }

    public void setFechaParametro1(Date fechaParametro1) {
        this.fechaParametro1 = fechaParametro1;
    }

    public Date getFechaParametro2() {
        return fechaParametro2;
    }

    public void setFechaParametro2(Date fechaParametro2) {
        this.fechaParametro2 = fechaParametro2;
    }

    public ItemPedidoUnoeeController getItemPedidoUnoee() {
        return itemPedidoUnoee;
    }

    public void setItemPedidoUnoee(ItemPedidoUnoeeController itemPedidoUnoee) {
        this.itemPedidoUnoee = itemPedidoUnoee;
    }

    public List<ItemPedidoUnoeeController> getListaItemPedidosUnoee() {
        return listaItemPedidosUnoee;
    }

    public void setListaItemPedidosUnoee(List<ItemPedidoUnoeeController> listaItemPedidosUnoee) {
        this.listaItemPedidosUnoee = listaItemPedidosUnoee;
    }

    public List<String> getListaItemCliente() {
        return listaItemCliente = getItemPedidoFacade().itemCliente(usuarioActual.getNit());
    }

    public void setListaItemCliente(List<String> listaItemCliente) {
        this.listaItemCliente = listaItemCliente;
    }

    public boolean isEstadoArchivoXls() {
        return estadoArchivoXls;
    }

    public void setEstadoArchivoXls(boolean estadoArchivoXls) {
        this.estadoArchivoXls = estadoArchivoXls;
    }

    public String getPrecioUnoEE() {
        return precioUnoEE;
    }

    public void setPrecioUnoEE(String precioUnoEE) {
        this.precioUnoEE = precioUnoEE;
    }


    public List<String> completeExt1(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaExt1) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }

    public List<String> completeExt2(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaExt2) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }

    public List<String> completeItemPatron(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaItemPatron) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }

    //Realiza la consulta sobre si es un item de cliente homologado.
    public void consultarItemHomologado() {

        try {
            itemPatron = null;
            ext1 = null;
            ext2 = null;

            //Revisa si hay un item duplicado del homologado
            //Si en el conteo encuentra un valor mayor igual a cero, envia mensaje de error porque existe un duplicado.
            int itemDuplicado = getItemPedidoFacade().contarHomologarItemDuplicado(usuarioActual.getNit(), codigoCliente);


            //Si no existe un item duplicado de este.
            if (itemDuplicado <= 1) {

                //Guarda los datos del item homologado encontrado
                //ej: 0022712 | SEP POLIESTER 4 AUTO 372 T A T COD 1    CMCO030  -30 CM 00022 -AZUL OSCURO UND 
                setItemHomologado(getItemPedidoFacade().homologarItem(usuarioActual.getNit(), codigoCliente));

                //Se distribuye sus valores en cada una de las variables del item pedido.
                if (getItemHomologado() != null) {
                    itemPatron = getItemHomologado().substring(0, 50);
                    tipoExt1 = getItemHomologado().substring(50, 52);
                    tipoExt2 = getItemHomologado().substring(52, 54);
                    ext1 = getItemHomologado().substring(54, 100);
                    ext2 = getItemHomologado().substring(100, 147);
                    itemPedidoActual.setUnidadMedida(getItemHomologado().substring(147, 151).trim());
                    itemPedidoActual.setCantidad(new BigDecimal(0));

                    for (String s : itemPedidoFacade.consultaDecimalesUnidadInventario(itemPedidoActual.getUnidadMedida())) {
                        setDecimalesUnidadMedida(s);
                    }
                    consultarDescripcionLabelExt();
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código duplicado", "El código se encuentra duplicado en la asignación de ítems, por favor comunicarse con su asesor comercial");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                setItemHomologado("");
            }

        } catch (Exception e) {
            addErrorMessage("Código no encontrado", "El código no se encuentra en nuestra base de datos");
        }

    }


    //Partir el item del cliente
    public void partirItemCliente() {
        itemPatron = codigoCliente.substring(0, 50);
        ext1 = codigoCliente.substring(50, 96);
        ext2 = codigoCliente.substring(96, 142);
        itemPedidoActual.setUnidadMedida(codigoCliente.substring(142, 146));
        tipoExt1 = codigoCliente.substring(146, 148);
        tipoExt2 = codigoCliente.substring(148, 150);
        consultarDescripcionLabelExt();
        itemPedidoActual.setCantidad(new BigDecimal(0));
        codigoCliente = "";
    }


    //Consulta por un tipo Ext
    public void consultarTipoExt() {
        int codigoItem = Integer.parseInt(itemPatron.substring(0, 7));
        for (String s : itemPedidoFacade.consultaTipoExtensiones(codigoItem)) {
            tipoExt1 = s.substring(0, 2);
            tipoExt2 = s.substring(2, 4);
        }
        listaExt1 = getItemPedidoFacade().consultaExt1(tipoExt1);
        listaExt2 = getItemPedidoFacade().consultaExt2(tipoExt2);
        List<String> unidadInventario = itemPedidoFacade.consultaUnidadInventario(codigoItem);
        for (String s : unidadInventario) {
            itemPedidoActual.setUnidadMedida(s);
        }
        for (String s : itemPedidoFacade.consultaDecimalesUnidadInventario(itemPedidoActual.getUnidadMedida())) {
            setDecimalesUnidadMedida(s);
        }
        itemPedidoActual.setCantidad(new BigDecimal(0));
        consultarDescripcionLabelExt();
        itemPatron = itemPatron.substring(0, itemPatron.length());
    }


    //Itera sobre el String de tamaño y cantidad para colocar el primer caracter en mayuscula
    //y el resto en minuscula
    public void consultarDescripcionLabelExt() {

        for (String s : itemPedidoFacade.consultaDescripcionLabelExt1(tipoExt1)) {
            descripcionLabelExt1 = Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length()).toLowerCase();
        }

        for (String s : itemPedidoFacade.consultaDescripcionLabelExt2(tipoExt2)) {
            descripcionLabelExt2 = Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length()).toLowerCase();
        }
    }


    public List<String> getListaExt1() {
        return listaExt1;
    }

    public void setListaExt1(List<String> listaExt1) {
        this.listaExt1 = listaExt1;
    }

    public List<String> getListaExt2() {
        return listaExt2;
    }

    public void setListaExt2(List<String> listaExt2) {
        this.listaExt2 = listaExt2;
    }

    public List<String> getListaPuntoEnvio() {
        return listaPuntoEnvio;
    }

    public void setListaPuntoEnvio(List<String> listaPuntoEnvio) {
        this.listaPuntoEnvio = listaPuntoEnvio;
    }


    public void recargarCampos() {
        ext1 = "";
        ext2 = "";
        itemPatron = "";
        codigoCliente = "";
        tipoExt1 = "";
        tipoExt1 = "";

        //recargarPlano();
        itemPedidoActual = new T004ItemPedido();
        decimalesUnidadMedida = "0";
        listaExt1 = new ArrayList<>();
        listaExt2 = new ArrayList<>();
        descripcionLabelExt1 = "Tamaño";
        descripcionLabelExt2 = "Color";
        mensajeConector = null;
        valorConector = 0;
        nitAsesor = "";
        correoAsesor = "";
        nombreAsesor = "";
    }


    public void recargarLista() {
    }

    public String prepareListaElaboracion() {
        return "/usuario/pedido/listaElaboracion";
    }


    public String prepareListaErrores() {
        return "/servicioCliente/listaError";
    }


    public String prepareListaErroresCompleto() {
        return "/servicioCliente/listaErrorCompleto";
    }


    public String prepareListaHistorial() throws SQLException {
        for (String s : getPedidoFacade().consultarRowidNit(usuarioActual.getNit())) {
            rowIdCliente = s;
        }
        fechaParametro1 = null;
        fechaParametro2 = null;
        listaPedidosUnoee = new ArrayList<>();
        return "/usuario/pedido/listaHistorial";
    }


    public String prepareListaVerificacion() {
        return "/usuario/pedido/listaVerificacion";
    }


    public void obtenerPuntoEnvio() {
        try {
            pedidoActual.setDireccion("");
            pedidoActual.setTerceroVendedor("");
            pedidoActual.setContacto("");
            String resultado = getPedidoFacade().obtenerPuntoEnvio(usuarioActual.getNit(), usuarioActual.getIdSucursal(), usuarioActual.getIdPuntoEnvio());
            if (resultado != null) {
                int largoRegultado = resultado.length();
                pedidoActual.setDireccion(resultado.substring(6, 47));
                pedidoActual.setTerceroVendedor(resultado.substring(49, 156));
                pedidoActual.setContacto(resultado.substring(158, largoRegultado));
                pedidoActual.setPuntoEnvio(usuarioActual.getIdPuntoEnvio() + " - " + usuarioActual.getDescripcionPuntoEnvio());
            }
        } catch (Exception e) {
            addErrorMessage("Datos incompletos", "Datos incompletos");
        }
    }


    // cuando se da click en crear nuevo pedido 
    public String prepareCreate() throws IOException {
        contarMostrarPanel(); //cuenta el item homologado desde el momento que se ingresa para realizar la creacion del item.
        recargarCampos(); //limpia los campos

        listaExt1 = new ArrayList<>();
        listaExt2 = new ArrayList<>();
        listaPuntoEnvio = getPedidoFacade().consultaListaPuntoEnvio(usuarioActual.getNit(), usuarioActual.getIdSucursal());
        listaItemPatron = getItemPedidoFacade().consultaListaItemPatron(usuarioActual.getNit());


        pedidoActual = new T003Pedidos();
        pedidoActual.setIdUsuario(usuarioActual);
        pedidoActual.setSucursalRemisionar(usuarioActual.getIdSucursal() + " - " + usuarioActual.getDescripcionSucursal());
        pedidoActual.setSucursalFacturar(usuarioActual.getIdSucursal() + " - " + usuarioActual.getDescripcionSucursal());


        itemPedidoActual = new T004ItemPedido();
        listaItemPedido = new ArrayList<>();

        //Lista de item pedidos seleccionados.
        listaItemPedidoSelect = new ArrayList<>();
        cadenaCaracteresText1 = "";
        obtenerPuntoEnvio();
        return "/usuario/pedido/crear";
    }


    public String prepareCreateEcuador() throws IOException {
        recargarCampos();
        listaExt1 = new ArrayList<>();
        listaExt2 = new ArrayList<>();
        listaPuntoEnvio = getPedidoFacade().consultaListaPuntoEnvio(usuarioActual.getNit(), usuarioActual.getIdSucursal());
        listaItemPatron = getItemPedidoFacade().consultaListaItemPatron(usuarioActual.getNit());
        pedidoActual = new T003Pedidos();
        pedidoActual.setIdUsuario(usuarioActual);
        pedidoActual.setSucursalRemisionar(usuarioActual.getIdSucursal() + " - " + usuarioActual.getDescripcionSucursal());
        pedidoActual.setSucursalFacturar(usuarioActual.getIdSucursal() + " - " + usuarioActual.getDescripcionSucursal());
        itemPedidoActual = new T004ItemPedido();
        listaItemPedido = new ArrayList<>();
        listaItemPedidoSelect = new ArrayList<>();
        cadenaCaracteresText1 = "";
        obtenerPuntoEnvio();
        return "/usuario/pedido/crearPedidoEcuador";
    }


    public String prepareEdit() {
        try {
            if (pedidoActual.getEstado() == 0) {
                recargarCampos();
                contarMostrarPanel();
                listaExt1 = new ArrayList<>();
                listaExt2 = new ArrayList<>();
                listaPuntoEnvio = getPedidoFacade().consultaListaPuntoEnvio(usuarioActual.getNit(), usuarioActual.getIdSucursal());
                listaItemPatron = getItemPedidoFacade().consultaListaItemPatron(usuarioActual.getNit());
                listaItemPedidoSelect = itemPedidoFacade.consultarItemPedido(pedidoActual);
                return "/usuario/pedido/editar";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Pedido aprobado", "Los pedidos en estado de aprobado no pueden ser modificados, por favor comun&#237quese con un asesor");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }


    public String prepareEditItemPedido() {
        BigDecimal cantidad = itemPedidoActual.getCantidad();
        itemPatron = itemPedidoActual.getDescripcionCompletaItem();
        consultarTipoExt();
        ext1 = itemPedidoActual.getDescripcionExt1();
        ext2 = itemPedidoActual.getDescripcionExt2();
        itemPedidoActual.setCantidad(cantidad);
        listaItemPatron = getItemPedidoFacade().consultaListaItemPatron(pedidoActual.getIdUsuario().getNit());
        precioUnoEE = getItemPedidoFacade().obtenerPrecioItemEcuador(itemPedidoActual.getCodCliente(), pedidoActual.getIdUsuario().getNit(), itemPedidoActual.getUnidadMedida());
        return "editarItem";
    }


    public String prepareDuplicar() throws IOException {
        recargarCampos();
        listaExt1 = new ArrayList<>();
        listaExt2 = new ArrayList<>();
        listaItemPatron = getItemPedidoFacade().consultaListaItemPatron(usuarioActual.getNit());
        pedidoActual.setIdUsuario(usuarioActual);
        pedidoActual.setSucursalRemisionar(usuarioActual.getIdSucursal() + " - " + usuarioActual.getDescripcionSucursal());
        pedidoActual.setSucursalFacturar(usuarioActual.getIdSucursal() + " - " + usuarioActual.getDescripcionSucursal());
        obtenerPuntoEnvio();
        itemPedidoActual = new T004ItemPedido();
        itemPedidoActual.setCantidad(new BigDecimal(1));
        listaItemPedido = new ArrayList<>();
        listaItemPedidoSelect = itemPedidoFacade.consultarItemPedido(pedidoActual);
        return "/usuario/pedido/duplicar";
    }


    public String prepareVer() {
        try {
            listaItemPedidoSelect = itemPedidoFacade.consultarItemPedido(pedidoActual);
            return "/usuario/pedido/ver";
        } catch (Exception e) {
            return null;
        }
    }


    public String prepareVerVerificacion() {
        try {
            listaItemPedidoSelect = itemPedidoFacade.consultarItemPedido(pedidoActual);
            return "/usuario/pedido/verVerificacion";
        } catch (Exception e) {
            return null;
        }
    }


    public String prepareVerHistorial() {
        try {
            listaItemPedidosUnoee = new ArrayList<>();
            recargarConsultarItemsPedidosUnoee();
            return "/usuario/pedido/verHistorial";
        } catch (Exception e) {
            return null;
        }
    }


    public String prepareVerError() {
        try {
            listaItemPedidoSelect = itemPedidoFacade.consultarItemPedido(pedidoActual);
            return "verError";
        } catch (Exception e) {
            return null;
        }
    }


    public String update() {
        try {
            return "/usuario/pedido/listaElaboracion";
        } catch (Exception e) {
            return null;
        }
    }


    public String add() {
        try {
            return "/usuario/pedido/listaElaboracion";
        } catch (Exception e) {
            return null;
        }
    }


    public void addItemCodClientePedido() {
        try {
            String partirCodigoItem = getItemPatron().substring(0, 7);
            String partirDescripcion = getItemPatron().substring(10, 50);
            itemPedidoActual.setCodItem(Integer.parseInt(partirCodigoItem));
            itemPedidoActual.setDescripcion(partirDescripcion);
            itemPedidoActual.setIdExt1Detalle(getExt1().substring(0, 4));
            itemPedidoActual.setDescripcionExt1(getExt1());
            itemPedidoActual.setIdExt2Detalle(getExt2().substring(0, 5));
            itemPedidoActual.setDescripcionExt2(getExt2());
            itemPedidoActual.setDescripcionCompletaItem(getItemPatron());
            listaItemPedidoSelect.add(itemPedidoActual);

            //Se inicializa nuevamente el objeto de item pedido.
            itemPedidoActual = new T004ItemPedido();
            recargarCampos();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }


    public void addItemPedido() throws SQLException {
        try {

            //Si la cantidad es cero o menor, se envia una notificacion de error.
            if (itemPedidoActual.getCantidad().equals(new BigDecimal(0))) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ítem no cargado", "La cantidad debe ser mayor a 0");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            } else {
                //Parte el codigo del item a 7 caracteres
                String partirCodigoItem = getItemPatron().substring(0, 7);

                //Parte la descripcion de 10 a 50 caracteres
                String partirDescripcion = getItemPatron().substring(10, 50);


                itemPedidoActual.setCodItem(Integer.parseInt(partirCodigoItem));
                itemPedidoActual.setDescripcion(partirDescripcion);
                itemPedidoActual.setIdExt1Detalle(getExt1().substring(0, 4));
                itemPedidoActual.setDescripcionExt1(getExt1());
                itemPedidoActual.setIdExt2Detalle(getExt2().substring(0, 5));
                itemPedidoActual.setDescripcionExt2(getExt2());
                itemPedidoActual.setDescripcionCompletaItem(getItemPatron());
                itemPedidoActual.setCodCliente(codigoCliente);
                itemPedidoActual.setUnoeeItem((short) 0);
                itemPedidoActual.setUnoeeListasMateriales((short) 0);
                itemPedidoActual.setUnoeePortafolio((short) 0);
                itemPedidoActual.setUnoeePrecios((short) 0);
                //itemPedidoActual.setPrecioUnitarioUnoee(new BigDecimal(getItemPedidoFacade().obtenerPrecioItemEcuador(itemPedidoActual.getCodCliente(), pedidoActual.getIdUsuario().getNit())));

                if (pedidoActual.getIdUsuario().getNit().equals("1792196841001")) {
                    itemPedidoActual.setIdMotivo("02");
                } else {
                    itemPedidoActual.setIdMotivo("01");
                }
                itemPedidoActual.setIdBodega(obtenerBodegaItem(itemPedidoActual.getCodItem()));

                //Todos los pedidos agregados se colocan en la lista de ItemPedidosSelect.
                listaItemPedidoSelect.add(itemPedidoActual);

                itemPedidoActual = new T004ItemPedido();
                itemPedidoActual.setCantidad(new BigDecimal(0));
                itemsCargadosPlano++;

                //Se encarga de limpiar los campos
                recargarCampos();
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }


    public String obtenerBodegaItem(int codItem) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        //String query = "select v743.v743_numero pp from  UnoEE.dbo.v121 left join UnoEE.dbo.v743  on v743_rowid = v121_rowid_entidad_item_ext  and v743_rowid_entidad_atributo = 1558 inner join UnoEE.dbo.t753_mm_movto_entidad_columna t753 ON t753.f753_rowid_movto_entidad = v121_rowid_entidad_item_ext  inner join UnoEE.dbo.t741_mm_maestro_detalle t741 ON t741.f741_rowid = f753_rowid_maestro_detalle where v121_id_item = "+capturaParadaGeActual.getIdItem()+ " and v121_id_ext1_detalle = '"+capturaParadaGeActual.getExt1()+"' and v121_id_ext2_detalle = '"+capturaParadaGeActual.getExt2()+"' ";                
        String query = "SELECT top 1 t150.f150_id idBodega FROM [t753_mm_movto_entidad_columna] t753 inner join t149_mc_tipo_inv_serv t149  on t149.f149_rowid_movto_entidad = t753.f753_rowid_movto_entidad inner join t150_mc_bodegas t150 on t150.f150_rowid = t753.f753_dato_numero inner join v121 v121 on v121.v121_id_tipo_inv_serv =  t149.f149_id WHERE v121.v121_id_item = " + codItem;
        String bodega = "";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                bodega = rs.getString("idBodega");
                break;
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return bodega;
    }


    public String updateItemPedido() {
        if (itemPedidoActual.getCantidad().equals(new BigDecimal(0))) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ítem no cargado", "La cantidad debe ser mayor a 0");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        } else {
            String partirCodigoItem = getItemPatron().substring(0, 7);
            String partirDescripcion = getItemPatron().substring(10, getItemPatron().length());
            itemPedidoActual.setCodItem(Integer.parseInt(partirCodigoItem));
            itemPedidoActual.setDescripcion(partirDescripcion);
            itemPedidoActual.setIdExt1Detalle(getExt1().substring(0, 4));
            itemPedidoActual.setDescripcionExt1(getExt1());
            itemPedidoActual.setIdExt2Detalle(getExt2().substring(0, 5));
            itemPedidoActual.setDescripcionExt2(getExt2());
            itemPedidoActual.setDescripcionCompletaItem(getItemPatron());
            itemPedidoActual.setUnoeePrecios((short) 0);
            itemPedidoActual.setUnoeeListasMateriales((short) 0);
            itemPedidoActual.setUnoeePortafolio((short) 0);
            itemPedidoActual.setUnoeeItem((short) 0);
            itemPedidoFacade.edit(itemPedidoActual);
            itemPedidoActual = new T004ItemPedido();
            addSuccessMessage("Ítem editado", "El ítem fue editado correctamente");
            return "verError";
        }
    }


    public void deleteItemPedido() {
        try {
            listaItemPedidoSelect.remove(itemPedidoTable);
            itemPedidoActual = new T004ItemPedido();
            recargarCampos();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }


    public void deleteItemPedidoGuardado() {
        try {
            itemPedidoFacade.remove(itemPedidoActual);
            listaItemPedidoSelect.remove(itemPedidoActual);
            itemPedidoActual = new T004ItemPedido();
            recargarCampos();
            addSuccessMessage("Ítem eliminado", "El ítem fue eliminado del pedido");
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }


    public void deletePedidoBorrador() {
        try {
            if (pedidoActual.getEstado() == 0) {
                listaItemPedidoSelect = itemPedidoFacade.consultarItemPedido(pedidoActual);
                for (T004ItemPedido item : listaItemPedidoSelect) {
                    itemPedidoFacade.remove(item);
                }
                pedidoFacade.remove(pedidoActual);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Pedido aprobado", "Los pedidos en estado de aprobado no pueden ser eliminados");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }


    //Realiza la confirmacion del pedido que se va a crear
    public String confirmarPedidoCrear() {
        try {

            //Si el envio no contiene algun dato de envio, envia mensaje de error
            if (pedidoActual.getContacto().isEmpty() || pedidoActual.getDireccion().isEmpty() || pedidoActual.getTerceroVendedor().isEmpty()) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pedido no creado", "El pedido no contiene la información completa del punto de envió, por favor comunicarse con un asesor");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }


            //Si la lista de pedido de items esta vacia, envia error
            if (listaItemPedidoSelect.isEmpty()) {
                addErrorMessage("Pedido no creado", "El pedido no contiene ítems");
                return null;
            } else {
                editarPedido = false;
                return "confirmarPedido";
            }

        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    public String confirmarPedidoVolver() {
        if (editarPedido) {
            return "editar";
        } else {
            return "crear";
        }
    }


    public String confirmarPedidoEditar() {
        try {
            if (pedidoActual.getContacto().isEmpty() || pedidoActual.getDireccion().isEmpty() || pedidoActual.getTerceroVendedor().isEmpty()) {
                addErrorMessage("Pedido no creado", "El pedido no contiene la información completa del punto de envió, por favor comunicarse con un asesor");
                return null;
            }
            if (listaItemPedidoSelect.isEmpty()) {
                addErrorMessage("Pedido no creado", "El pedido no contiene ítems");
                return null;
            } else {
                editarPedido = true;
                return "confirmarPedido";
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    //Se da la aprobacion del pedido(BOTON APROBAR PEDIDO).
    public String addPedido() {
        try {

            //ordenar una lista por diferentes parametros
            listaItemPedidoSelect.sort(ascending(getComparator(DESCRIPCION_SORT, DESC_EXT2_SORT, TAM_CANT_SORT)));

            //Si la lista del pedido esta vacia.
            if (listaItemPedidoSelect.isEmpty()) {
                addErrorMessage("Pedido no creado", "El pedido no contiene ítems");
                return null;
            }

            //Si la lista de itemPedidoSelect tiene contenido, pero esta en modo edicion
            else {
                if (editarPedido) {
                    pedidoActual.setEstado((short) 1);
                    getPedidoFacade().edit(pedidoActual);
                    for (T004ItemPedido itemsEliminar : itemPedidoFacade.consultarItemPedido(pedidoActual)) {
                        itemPedidoFacade.remove(itemsEliminar);
                    }
                    for (T004ItemPedido itemsCrear : listaItemPedidoSelect) {
                        itemsCrear.setIdPedido(pedidoActual);
                        getItemPedidoFacade().create(itemsCrear);
                    }
                }


                //Si la lista de itemPedidoSelect tiene contenido
                else {
                    obtenerDatosAsesorUnoEE(pedidoActual.getIdUsuario().getIdSucursal(), pedidoActual.getIdUsuario().getNit());

                    pedidoActual.setFechaPedido(new Date());
                    pedidoActual.setIdUsuario(usuarioActual);
                    pedidoActual.setEstado((short) 0);
                    pedidoActual.setTerceroAsesor(nitAsesor);

                    //Inserta el pedido en la base de datos.
                    getPedidoFacade().create(pedidoActual);

                    for (T004ItemPedido items : listaItemPedidoSelect) {
                        items.setIdPedido(pedidoActual);
                        getItemPedidoFacade().create(items);
                    }
                }


                mensajeConector = "";
                valorConector = 0;


                //Se realiza la gestion del conector con web service
                ExecutorService executor = Executors.newFixedThreadPool(5);

                executor.submit(() -> {
                    try {

                        //Se genera la data para el webservice
                        ejecutarWebService();

                        pedidoActual.setEstado((short) 1);
                        pedidoActual.setEstadoWB((short) valorConector);
                        pedidoActual.setMensajeWB(mensajeConector);
                        getPedidoFacade().edit(pedidoActual);

                        //Organiza la informacion que va a contener el email que se va a enviar.
                        parametrosMail();

                        //arma la plantilla y envia el email
                        sendMailCliente();

                        if (valorConector == 1) {
                            sendMailAsesor();
                        }

                        recargarCampos();
                    } catch (Exception ex) {
                        Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                addSuccessMessage("Su solicitud fue creada exitosamente", "Gracias por preferirnos");
                return "ver";
            }
        } catch (Exception e) {
            // Aqui se asignaria un envio de correo cuando algo sale mal.

            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    public String addPedidoEcuador() {
        try {
            if (listaItemPedidoSelect.isEmpty()) {
                addErrorMessage("Pedido no creado", "El pedido no contiene ítems");
                return null;
            } else if (cadenaCaracteresText1.contains("Fila")) {
                addErrorMessage("Pedido no creado", "El cargue de los registros tienen errores");
                return null;
            } else {
                if (editarPedido) {
                    pedidoActual.setEstado((short) 1);
                    getPedidoFacade().edit(pedidoActual);
                    for (T004ItemPedido itemsEliminar : itemPedidoFacade.consultarItemPedido(pedidoActual)) {
                        itemPedidoFacade.remove(itemsEliminar);
                    }
                    for (T004ItemPedido itemsCrear : listaItemPedidoSelect) {
                        itemsCrear.setIdPedido(pedidoActual);
                        getItemPedidoFacade().create(itemsCrear);
                    }
                } else {
                    obtenerDatosAsesorUnoEE(pedidoActual.getIdUsuario().getIdSucursal(), pedidoActual.getIdUsuario().getNit());
                    pedidoActual.setFechaPedido(new Date());
                    pedidoActual.setIdUsuario(usuarioActual);
                    pedidoActual.setEstado((short) 0);
                    pedidoActual.setTerceroAsesor(nitAsesor);
                    pedidoActual.setOc("EC-" + pedidoActual.getOc());
                    pedidoActual.setEstado((short) 1);
                    pedidoActual.setEstadoWB((short) 1);
                    getPedidoFacade().create(pedidoActual);
                    parametrosMail();
                    sendMailEcuador();
                    recargarCampos();
                    for (T004ItemPedido items : listaItemPedidoSelect) {
                        items.setIdPedido(pedidoActual);
                        getItemPedidoFacade().create(items);
                    }
                }
                addSuccessMessage("Su solicitud fue creada exitosamente", "Gracias por preferirnos");
                return "ver";
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    public String addPedidoError() {
        try {
            mensajeConector = "";
            valorConector = 0;
            ejecutarWebService();
            pedidoActual.setEstado((short) 1);
            pedidoActual.setEstadoWB((short) valorConector);
            pedidoActual.setMensajeWB(mensajeConector);
            getPedidoFacade().edit(pedidoActual);
            if (pedidoActual.getEstadoWB() == 1) {
                addErrorMessage("Pedido no creado", "El pedido no fue creado debido a errores");
                return "verError";
            } else {
                addSuccessMessage("Pedido creado", "El pedido fue creado exitosamente");
                return "ver";
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    public String addPedidoBorrador() {
        try {
            if (listaItemPedidoSelect.isEmpty()) {
                addErrorMessage("Pedido no creado", "El pedido no contiene items");
                return null;
            } else {
                if (editarPedido) {
                    pedidoActual.setEstado((short) 0);
                    getPedidoFacade().edit(pedidoActual);
                    for (T004ItemPedido itemsEliminar : itemPedidoFacade.consultarItemPedido(pedidoActual)) {
                        itemPedidoFacade.remove(itemsEliminar);
                    }
                    for (T004ItemPedido itemsCrear : listaItemPedidoSelect) {
                        itemsCrear.setIdPedido(pedidoActual);
                        getItemPedidoFacade().create(itemsCrear);
                    }
                } else {
                    pedidoActual.setFechaPedido(new Date());
                    pedidoActual.setIdUsuario(usuarioActual);
                    pedidoActual.setEstado((short) 0);
                    getPedidoFacade().create(pedidoActual);
                    for (T004ItemPedido items : listaItemPedidoSelect) {
                        items.setIdPedido(pedidoActual);
                        getItemPedidoFacade().create(items);
                    }
                }
                addSuccessMessage("Pedido creado", "El pedido fue creado exitosamente");
                recargarCampos();
                return "listaElaboracion";
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    //Archivo plano
    public void recargarPlano() throws IOException {
        try {
            BufferedWriter usuario = new BufferedWriter(new FileWriter(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.xlsx")));
            usuario.write("");
            usuario.close();
        } catch (Exception e) {
            System.out.println("No se pudo limpiar archivo " + e);
        }
    }


    //Leer archivo text1 - usuarios
    public String procesarText1(String nombre) {
        File f;
        FileReader lectorArchivo;
        try {
            f = new File(nombre);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            int contarLinea = 0;
            int errores = 0;
            String lineasErrores = "";
            String l = "";
            String aux = "";
            itemsCargadosPlano = 0;
            while (true) {
                aux = br.readLine();
                if (aux != null) {
                    try {
                        contarLinea++;
                        l = aux;
                        int maxLength = (l.length());
                        codigoCliente = l.substring(0, 30).trim();
                        if (maxLength == 42) {
                            setItemHomologado(getItemPedidoFacade().homologarItem(usuarioActual.getNit(), codigoCliente));
                            if (getItemHomologado() != null) {
                                itemPatron = getItemHomologado().substring(0, 54);
                                ext1 = getItemHomologado().substring(54, 100);
                                ext2 = getItemHomologado().substring(100, 147);
                                itemPedidoActual.setUnidadMedida(getItemHomologado().substring(147, 151).trim());
                                itemPedidoActual.setCantidad(new BigDecimal(l.substring(30, 42).trim()));
                                addItemPedido();
                            } else {
                                errores++;
                                lineasErrores = lineasErrores + contarLinea + ",";
                            }
                        } else {
                            errores++;
                            lineasErrores = lineasErrores + contarLinea + ",";
                        }
                        l = "";
                    } catch (Exception e) {
                        errores++;
                        lineasErrores = lineasErrores + contarLinea + ",";
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo cargado", "El archivo fue cargado con un total de: <br/> <span style='margin-left:0em'>- " + itemsCargadosPlano + " registros cargados. <br/> <span style='margin-left:2.3em'> - " + contarLinea + " lineas procesadas. <br/> <span style='margin-left:2.3em'> - " + errores + " errores encontrados en las lineas " + lineasErrores + "</span>");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    break;
                }

            }
            br.close();
            lectorArchivo.close();
            itemsCargadosPlano = 0;
            return l;
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }

        return null;
    }


    //LEER XLS
    public void leerXls(String nombre) {
        try {
            String rutaArchivoExcel = nombre;
            FileInputStream inputStream = new FileInputStream(new File(rutaArchivoExcel));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator iterator = firstSheet.iterator();


            DataFormatter formatter = new DataFormatter();
            int celda = 0;
            int fila = 0;

            while (iterator.hasNext()) {
                Row nextRow = (Row) iterator.next();
                nextRow = firstSheet.getRow(fila);
                Iterator cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();
                    String contenidoCelda = formatter.formatCellValue(cell);
                    System.out.println("cont-celda: " + contenidoCelda);

                    //Revisa si el contenido de la celda viene vacio
                    if(!contenidoCelda.trim().equals("")) {


                        //para la cabecera unicamente
                        if (fila == 0) {
                            if (celda == 0) {
                                celda++;
                                cadenaCaracteresText1 = String.format("%-30s", "CÓDIGO ÍTEM");
                            } else {
                                celda = 0;
                                fila++;
                                nextRow = firstSheet.getRow(fila);
                                cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + String.format("%-30s", "CANTIDAD") + "\n------------------------------------------------\n";
                            }
                        }

                        //para el resto de filas
                        else {
                            if (celda == 0) {
                                celda++;
                                cadenaCaracteresText1 = cadenaCaracteresText1 + String.format("%-30s", contenidoCelda);
                            } else {
                                celda = 0;
                                fila++;
                                nextRow = firstSheet.getRow(fila);
                                cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + contenidoCelda + "\n";
                            }
                        }
                        //System.out.println("Aqui va la cadena de caracteres****************************");
                        //System.out.println(cadenaCaracteresText1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void leerXlsEcuador(String nombre) {
        try {
            String rutaArchivoExcel = nombre;
            FileInputStream inputStream = new FileInputStream(new File(rutaArchivoExcel));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator iterator = firstSheet.iterator();
            DataFormatter formatter = new DataFormatter();
            int celda = 0;
            int fila = 0;
            while (iterator.hasNext()) {
                Row nextRow = (Row) iterator.next();
                nextRow = firstSheet.getRow(fila);
                Iterator cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();
                    String contenidoCelda = formatter.formatCellValue(cell);
                    if (fila == 0) {
                        if (celda == 0) {
                            celda++;
                            cadenaCaracteresText1 = String.format("%-20s", "CÓDIGO ÍTEM");
                        } else if (celda == 1) {
                            celda++;
                            cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + String.format("%-20s", "CANTIDAD");
                        } else if (celda == 2) {
                            celda++;
                            cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + String.format("%-20s", "PRECIO");
                        } else if (celda == 3) {
                            celda++;
                            cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + String.format("%-30s", "NOTAS");
                        } else if (celda == 4) {
                            celda++;
                        } else if (celda == 5) {
                            celda++;
                        } else if (celda == 6) {
                            celda++;
                        } else if (celda == 7) {
                            celda = 0;
                            fila++;
                            nextRow = firstSheet.getRow(fila);
                            cadenaCaracteresText1 = cadenaCaracteresText1 + "\n-----------------------------------------------------------------------------------------\n";
                        }
                    } else {
                        if (celda == 0) {
                            celda++;
                            cadenaCaracteresText1 = cadenaCaracteresText1 + String.format("%-20s", contenidoCelda);
                        } else if (celda == 1) {
                            celda++;
                            cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + String.format("%-20s", contenidoCelda);
                        } else if (celda == 2) {
                            celda++;
                            cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + String.format("%-20s", contenidoCelda);
                        } else if (celda == 3) {
                            celda++;
                            cadenaCaracteresText1 = cadenaCaracteresText1 + " | " + contenidoCelda;
                        } else if (celda == 4) {
                            celda++;
                        } else if (celda == 5) {
                            celda++;
                        } else if (celda == 6) {
                            celda++;
                        } else if (celda == 7) {
                            celda = 0;
                            fila++;
                            nextRow = firstSheet.getRow(fila);
                            cadenaCaracteresText1 = cadenaCaracteresText1 + "\n";
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Procesa el archivo que ha sido previamente leido y donde ya se han mostrado los datos en el area de texto
    public void procesarXls(String nombre) {
        int errores = 0;
        int contarLinea = 0;
        String lineasErrores = "";
        String mensajeErrores = "";
        int itemsCargadosPlano = 0;


        try {
            String rutaArchivoExcel = nombre;
            FileInputStream inputStream = new FileInputStream(new File(rutaArchivoExcel));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator iterator = firstSheet.iterator();
            DataFormatter formatter = new DataFormatter();

            int cantidadDecimales = 0;
            int celda = 0;
            int fila = 1;
            boolean itemEncontrado = false;
            boolean porCodBarras = false;


            while (iterator.hasNext()) {
                Row nextRow = (Row) iterator.next();
                nextRow = firstSheet.getRow(fila);
                Iterator cellIterator = nextRow.cellIterator();

                //Empieza a recorrer las celdas
                //En la primera vuelta toma el codigo de inmediato, ya que la fila inicia en 1(segunda posicion)
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();
                    String contenidoCelda = formatter.formatCellValue(cell);


                    //muestra el contenido de cada celda que vaya leyendo en el excel
                    System.out.println("contenido celda en iteracion: " + contenidoCelda);

                    //Revisa si el contenido de la celda viene vacio
                    if (!contenidoCelda.trim().equals("")) {


                        if (celda == 0) {
                            try {
                                celda++;

                                if (contenidoCelda.trim().isEmpty() || contenidoCelda == null) {
                                    break;
                                }


                                //Realiza la revision del item duplicado, pero busca en el procedimiento homologar_cod_cliente, trae resultados
                                //itera y luego retorna la cantidad para itemDuplicado
                                int itemDuplicado = getItemPedidoFacade().contarHomologarItemDuplicado(usuarioActual.getNit(), contenidoCelda);


                                //NUEVA PARTE
                                //Si el item duplicado es igual a cero significa que no encontro ningun regristro que es homologado cod cliente item
                                if (itemDuplicado == 0) {

                                    //Se procede a buscar por el codigo de barras
                                    itemDuplicado = getItemPedidoFacade().contarCodigoBarrasItem(contenidoCelda);

                                    //Se ajusta por codigo de barras
                                    porCodBarras = true;
                                }


                                //Si encuentra al menos uno, agrega el item
                                if (itemDuplicado <= 1) {

                                    //Si no se hizo por codigo de barras
                                    if (!porCodBarras) {
                                        //trae los datos homologados de tipo string
                                        setItemHomologado(getItemPedidoFacade().homologarItem(usuarioActual.getNit(), contenidoCelda));
                                    } else {
                                        //trae los datos referenciados al codigo de barras
                                        setItemHomologado(getItemPedidoFacade().itemPorCodBarras(contenidoCelda));
                                    }


                                    //Si no es nulo, pasa los datos a cada variable.
                                    if (getItemHomologado() != null) {
                                        codigoCliente = contenidoCelda;
                                        itemPatron = getItemHomologado().substring(0, 54);
                                        ext1 = getItemHomologado().substring(54, 100);
                                        ext2 = getItemHomologado().substring(100, 147);
                                        itemPedidoActual.setUnidadMedida(getItemHomologado().substring(147, 151).trim());


                                        for (String s : itemPedidoFacade.consultaDecimalesUnidadInventario(itemPedidoActual.getUnidadMedida())) {
                                            cantidadDecimales = Integer.parseInt(s);
                                        }
                                        itemEncontrado = true;
                                        porCodBarras = false;
                                    } else {
                                        mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El código " + contenidoCelda + " no se encuentra en nuestra base de datos";
                                        errores++;
                                        listaItemPedidoSelect.clear();
                                    }
                                } else {
                                    errores++;
                                    mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El código " + contenidoCelda + " se encuentra duplicado en la asignación de ítems, por favor comunicarse con su asesor comercial";
                                    listaItemPedidoSelect.clear();
                                }
                            } catch (Exception e) {
                                mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El código " + contenidoCelda + " no se encuentra en nuestra base de datos";
                                errores++;
                                listaItemPedidoSelect.clear();
                            }
                        }


                        //Revisa la siguiente columna que es de cantidad(und o decimales cuando es mts )
                        else {
                            try {
                                if (getItemHomologado() != null) {
                                    contarLinea++;
                                    celda = 0;
                                    fila++;
                                    nextRow = firstSheet.getRow(fila);
                                    try {

                                        if (contenidoCelda.trim().isEmpty() || contenidoCelda == null) {
                                            break;
                                        }

                                        //Cuando son unidades
                                        if (itemPedidoActual.getUnidadMedida().equals("UND")) {
                                            try {
                                                int cantidad = Integer.parseInt(contenidoCelda.trim());
                                                itemPedidoActual.setCantidad(new BigDecimal(cantidad));
                                            } catch (Exception e) {
                                                mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila) + " - La unidad de medida del ítem es UND, el valor " + contenidoCelda + " no es correcto";
                                                listaItemPedidoSelect.clear();
                                            }
                                        }

                                        //Cuando es por metros
                                        else {
                                            itemPedidoActual.setCantidad(new BigDecimal(contenidoCelda.trim()).setScale(cantidadDecimales, BigDecimal.ROUND_HALF_EVEN));
                                        }
                                    } catch (Exception e) {
                                        mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila) + " - El valor " + contenidoCelda + " no es correcto";
                                        listaItemPedidoSelect.clear();
                                    }


                                    //Añade el item pedido actual.
                                    addItemPedido();
                                    itemsCargadosPlano++;

                                    // Si hay errores en algun momento se vacea la lista para que solo cuente errores
                                    //Y para que no se agregue ningun item.
                                    if (errores != 0) {
                                        listaItemPedidoSelect.clear();
                                        itemsCargadosPlano--;
                                    }

                                } else {
                                    errores++;
                                }
                            } catch (Exception e) {
                                codigoCliente = "";
                                itemPatron = "";
                                ext1 = "";
                                ext2 = "";
                                itemPedidoActual = new T004ItemPedido();
                                errores++;
                                lineasErrores = lineasErrores + " | " + String.valueOf(fila);
                                listaItemPedidoSelect.clear();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            listaItemPedidoSelect.clear();
        }

        if (lineasErrores.equals("") && errores == 0) {
            lineasErrores = "<br/> <span style='margin-left:2.3em'> - No se han encontrado errores </span>";
        } else {
            lineasErrores = "<br/> <span style='margin-left:2.3em'> - Se han encontrado errores </span>";
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo procesado", "El archivo fue procesado con un total de: <br/> <span style='margin-left:0em'>- "
                + itemsCargadosPlano
                + " registros leídos." + lineasErrores);

        RequestContext.getCurrentInstance().showMessageInDialog(message);

        //Se evalua la cadena para mostrar mensaje en pantalla.
        if (mensajeErrores.equals("")) {
            cadenaCaracteresText1 = " No se encontraron errores: \n ------------------------------------------------------";
        } else {
            cadenaCaracteresText1 = "Errores encontrados: \n ------------------------------------------------------" + mensajeErrores;
        }


    }


    public void procesarXlsEcuador(String nombre) {
        int errores = 0;
        int contarLinea = 0;
        String lineasErrores = "";
        String mensajeErrores = "";
        int itemsCargadosPlano = 0;
        try {
            String rutaArchivoExcel = nombre;
            FileInputStream inputStream = new FileInputStream(new File(rutaArchivoExcel));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator iterator = firstSheet.iterator();
            DataFormatter formatter = new DataFormatter();
            int cantidadDecimales = 0;
            int celda = 0;
            int fila = 1;
            listaItemPedidoSelect = new ArrayList<>();
            boolean itemEncontrado = false;
            while (iterator.hasNext()) {
                Row nextRow = (Row) iterator.next();
                nextRow = firstSheet.getRow(fila);
                Iterator cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();
                    String contenidoCelda = formatter.formatCellValue(cell);
                    if (celda == 0) {
                        try {
                            celda++;
                            setItemHomologado(getItemPedidoFacade().consultarItemCodBarra(contenidoCelda.substring(2, contenidoCelda.length())));
                            if (getItemHomologado() != null) {
                                codigoCliente = contenidoCelda.substring(2, contenidoCelda.length());
                                itemPatron = getItemHomologado().substring(0, 54);
                                ext1 = getItemHomologado().substring(54, 100);
                                ext2 = getItemHomologado().substring(100, 147);
                                itemPedidoActual.setUnidadMedida(getItemHomologado().substring(147, 151).trim());
                                for (String s : itemPedidoFacade.consultaDecimalesUnidadInventario(itemPedidoActual.getUnidadMedida())) {
                                    cantidadDecimales = Integer.parseInt(s);
                                }
                                itemEncontrado = true;
                            } else {
                                mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El código " + contenidoCelda + " no se encuentra en nuestra base de datos";
                                errores++;
                            }
                        } catch (Exception e) {
                            mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El código " + contenidoCelda + " no se encuentra en nuestra base de datos";
                            errores++;
                        }
                    } else if (celda == 1) {
                        try {
                            celda++;
                            if (getItemHomologado() != null) {
                                try {
                                    if (itemPedidoActual.getUnidadMedida().equals("UND")) {
                                        try {
                                            int cantidad = Integer.parseInt(contenidoCelda.trim());
                                            itemPedidoActual.setCantidad(new BigDecimal(cantidad));
                                        } catch (Exception e) {
                                            mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - La unidad de medida del ítem es UND, el valor " + contenidoCelda + " no es correcto";
                                        }
                                    } else {
                                        itemPedidoActual.setCantidad(new BigDecimal(contenidoCelda.trim()).setScale(cantidadDecimales, BigDecimal.ROUND_HALF_EVEN));
                                    }
                                } catch (Exception e) {
                                    mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El valor " + contenidoCelda + " no es correcto";
                                    errores++;

                                }
                            } else {
                                errores++;
                            }
                        } catch (Exception e) {
                            codigoCliente = "";
                            itemPatron = "";
                            ext1 = "";
                            ext2 = "";
                            itemPedidoActual = new T004ItemPedido();
                            errores++;
                            lineasErrores = lineasErrores + " | " + String.valueOf(fila + 1);
                        }
                    } else if (celda == 2) {
                        try {
                            celda++;
                            if (getItemHomologado() != null) {
                                try {
                                    try {
                                        contenidoCelda = contenidoCelda.replaceAll(",", ".");
                                        itemPedidoActual.setPrecioUnitario(new BigDecimal(contenidoCelda.trim()).setScale(4, BigDecimal.ROUND_HALF_EVEN));
                                    } catch (Exception e) {
                                        mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El valor " + contenidoCelda + " no es correcto";
                                        codigoCliente = "";
                                        itemPatron = "";
                                        ext1 = "";
                                        ext2 = "";
                                        itemPedidoActual = new T004ItemPedido();
                                        errores++;
                                        lineasErrores = lineasErrores + " | " + String.valueOf(fila + 1);
                                    }
                                } catch (Exception e) {
                                    mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El valor " + contenidoCelda + " no es correcto";
                                }
                            } else {
                                errores++;
                            }
                        } catch (Exception e) {
                            codigoCliente = "";
                            itemPatron = "";
                            ext1 = "";
                            ext2 = "";
                            itemPedidoActual = new T004ItemPedido();
                            errores++;
                            lineasErrores = lineasErrores + " | " + String.valueOf(fila + 1);
                        }
                    } else if (celda == 3) {
                        try {
                            if (getItemHomologado() != null) {
                                celda++;
                                try {
                                    if (contenidoCelda.length() < 250) {
                                        itemPedidoActual.setNotasItem(contenidoCelda);
                                    } else {
                                        mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - Las notas del ítem deben tener un máximo de 250 caracteres";

                                        codigoCliente = "";
                                        itemPatron = "";
                                        ext1 = "";
                                        ext2 = "";
                                        itemPedidoActual = new T004ItemPedido();
                                        errores++;
                                        lineasErrores = lineasErrores + " | " + String.valueOf(fila + 1);
                                    }
                                } catch (Exception e) {
                                    mensajeErrores = mensajeErrores + " \n " + "Fila: " + (fila + 1) + " - El valor " + contenidoCelda + " no es correcto";
                                }
                                addItemPedido();
                                itemsCargadosPlano++;
                            } else {
                                errores++;
                            }
                        } catch (Exception e) {
                            codigoCliente = "";
                            itemPatron = "";
                            ext1 = "";
                            ext2 = "";
                            itemPedidoActual = new T004ItemPedido();
                            errores++;
                            lineasErrores = lineasErrores + " | " + String.valueOf(fila + 1);
                        }
                    } else if (celda == 4) {
                        celda++;
                    } else if (celda == 5) {
                        celda++;
                    } else if (celda == 6) {
                        celda++;
                    } else {
                        contarLinea++;
                        celda = 0;
                        fila++;
                        nextRow = firstSheet.getRow(fila);
                        contenidoCelda = "";
                        codigoCliente = "";
                        itemPatron = "";
                        ext1 = "";
                        ext2 = "";
                        itemPedidoActual = new T004ItemPedido();
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo procesado", "El archivo fue procesado con un total de: <br/> <span style='margin-left:0em'>- " + itemsCargadosPlano + " registros leídos. <br/> <span style='margin-left:2.3em'> - " + contarLinea + " filas procesadas. <br/> <span style='margin-left:2.3em'> - Errores encontrados en las filas " + lineasErrores + "</span>");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        cadenaCaracteresText1 = "Errores encontrados: \n ------------------------------------------------------" + mensajeErrores;
    }


    public String leerText1(String nombre) {
        File f;
        FileReader lectorArchivo;
        try {
            f = new File(nombre);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            String l = "";
            String aux = "";
            while (true) {
                aux = br.readLine();
                if (aux != null) {
                    try {
                        l = l + aux + "\n";
                    } catch (Exception e) {
                    }
                } else {
                    break;
                }

            }
            br.close();
            lectorArchivo.close();
            return l;
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }

        return null;
    }


    public void cargarPlanoUsuario(FileUploadEvent event) throws IOException {
        recargarPlano();
        UploadedFile file = event.getFile();
        byte[] data = IOUtils.toByteArray(file.getInputstream());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String newFileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.txt");
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            PedidoController a = new PedidoController();
            cadenaCaracteresText1 = a.leerText1(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.txt"));
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }


    //Carga del archivo de Excel por parte del usuario en el momento de seleccionar el archivo
    public void cargarHojaCalculoUsuario(FileUploadEvent event) throws IOException {
        //recargarPlano();
        UploadedFile file = event.getFile();
        byte[] data = IOUtils.toByteArray(file.getInputstream());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String newFileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.xlsx");
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            PedidoController a = new PedidoController();
            //cadenaCaracteresText1 = a.leerText1(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.txt"));
            cadenaCaracteresText1 = "";

            //Lee el archivo de Excel
            leerXls(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.xlsx"));
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }


    public void cargarHojaCalculoEcuador(FileUploadEvent event) throws IOException {
        //recargarPlano();
        UploadedFile file = event.getFile();
        byte[] data = IOUtils.toByteArray(file.getInputstream());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String newFileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.xlsx");
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            PedidoController a = new PedidoController();
            //cadenaCaracteresText1 = a.leerText1(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.txt"));
            cadenaCaracteresText1 = "";
            leerXlsEcuador(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.xlsx"));
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }


    //Cuando se da clic en el boton para procesar el archivo de Excell que se va a agregar.
    public void addPlano() throws IOException {
        try {
            //procesarText1(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.txt"));
            procesarXls(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.xlsx"));
            recargarPlano();
            recargarCampos();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al leer datos", "La estructura del archivo no fue reconocida");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }


    public void addPlanoEcuador() throws IOException {
        try {
            //procesarText1(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.txt"));
            procesarXlsEcuador(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.xlsx"));
            recargarPlano();
            recargarCampos();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al leer datos", "La estructura del archivo no fue reconocida");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }


    //mesages
    private void addErrorMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }


    private void addSuccessMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }


    //validar OC
    public void validarOC(FacesContext contex, UIComponent component, Object value) throws ValidatorException {
        T003Pedidos ocConsulta = getPedidoFacade().findByOc((String) value, usuarioActual.getNit());
        if (ocConsulta != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de OC repetido", "Numero de OC repetido"));
        } else {
            String oc = (String) value;
            pedidoActual.setOc(oc);
        }
    }


    //SOAP WEB SERVICE
    public void ejecutarWebService() throws RemoteException, Exception {
        mensajeConector = "";
        valorConector = 0;

        String idPedido = String.valueOf(pedidoActual.getIdPedido());
        String pvstrDatos = getPedidoFacade().obtenerConectorPedido(idPedido);

        // TODO Auto-generated method stub
        WSUNOEESoapProxy proxy = new WSUNOEESoapProxy();
        ShortHolder holder = new ShortHolder((short) 0);
        ImportarXMLResponseImportarXMLResult result = proxy.importarXML(pvstrDatos, holder);
        MessageElement[] elements = result.get_any();


        for (MessageElement element : elements) {
            mensajeConector = mensajeConector + element.getAsString();
            valorConector = element.getLength();
        }
    }


    //SINCRONIZAR ITEMS
    public void sincronizarPrecioEcuador() {
        for (T004ItemPedido item : listaItemPedidoSelect) {
            try {
                item.setPrecioUnitarioUnoee(new BigDecimal(getItemPedidoFacade().obtenerPrecioItemEcuador(item.getCodCliente(), "1792196841001", item.getUnidadMedida())));
                item.setFechaActualizacionPrecioUnoee(getItemPedidoFacade().obtenerFechaPrecioItemEcuador(item.getCodCliente(), "1792196841001", item.getUnidadMedida()));
                itemPedidoFacade.edit(item);
            } catch (Exception e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
            }
        }
    }

    public void sincronizarBodega() {
        for (T004ItemPedido item : listaItemPedidoSelect) {
            try {
                item.setIdBodega(obtenerBodegaItem(item.getCodItem()));
                itemPedidoFacade.edit(item);
            } catch (Exception e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
            }
        }
    }

    public String sincronizarItems() throws InterruptedException {
        try {
            String pedido = String.valueOf(pedidoActual.getIdPedido());
            getItemPedidoFacade().spEjecutarSincronizarItems(pedido, pedidoActual.getIdUsuario().getNit());
            //Thread.sleep(18000);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos procesados", "Los datos fueron ejecutados correctamente, verificar información");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            listaItemPedidoSelect = itemPedidoFacade.consultarItemPedido(pedidoActual);
            sincronizarBodega();
            if (pedidoActual.getIdUsuario().getNit().equals("1792196841001")) {
                sincronizarPrecioEcuador();
            }
            return "verError";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
            return "verError";
        }
    }


    //MOSTRAR TAPS
    //homologado = tiene su propio codigo de cliente.
    //no homologado = realiza la busqueda por codigo de barras.
    public void contarMostrarPanel() {
        contarItemHomologado = getItemPedidoFacade().contarHomologarItem(usuarioActual.getNit());
    }


    public boolean isMostrarPanel() {
        if (contarItemHomologado == 0) {
            System.out.println("retorna false");
            return false;
        } else {
            System.out.println("retorna true");
            return true;
        }
    }


    //Si el cliente tiene algun item asociado ITEM CLIENTE, no muestra el panel para buscar codigo EKA.
    public boolean isMostrarItemCliente() {
        if (getItemPedidoFacade().itemCliente(usuarioActual.getNit()).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isEcuador() {
        if (pedidoActual.getIdUsuario().getNit().equals("1792196841001")) {
            return true;
        } else {
            return false;
        }
    }


    //Email send
    public void sendMailCliente() throws FileNotFoundException {
        emailController = new EmailController();
        String asunto = "Su solicitud de pedido fue creada exitosamente";
        emailController.generarTemplate(p1CodigoPedido, p2UsuarioPedido, p3Oc, p4TablaItems, p5PuntoEnvio);
        String cuerpo = emailController.getTemplateCliente();
        emailController.sendMail(usuarioActual.getCorreo(), asunto, cuerpo);
    }


    public void sendMailAsesor() throws FileNotFoundException, SQLException {
        emailController = new EmailController();
        String asunto = "Pedido con errores";
        emailController.generarTemplateAsesor(p1CodigoPedido, p2UsuarioPedido, p6mensajeErrorWb, p4TablaItems, p5PuntoEnvio, p7razonSocialCliente);
        String cuerpo = emailController.getTemplateAsesor();
        emailController.sendMail(correoAsesor, asunto, cuerpo);
    }


    public void sendMailEcuador() throws FileNotFoundException {
        emailController = new EmailController();
        String asunto = "Solicitud de pedido fue creada exitosamente";
        emailController.generarTemplate(p1CodigoPedido, p2UsuarioPedido, p3Oc, p4TablaItems, p5PuntoEnvio);
        String cuerpo = emailController.getTemplateCliente();
        emailController.sendMail("exportaciones@ekazipper.com," + usuarioActual.getCorreo(), asunto, cuerpo);
    }


    //Se establecen datos para enviar por email
    public void parametrosMail() {
        p1CodigoPedido = String.valueOf(pedidoActual.getIdPedido());
        p2UsuarioPedido = pedidoActual.getIdUsuario().getNombre().toUpperCase();
        p3Oc = pedidoActual.getOc();
        p4TablaItems = "";
        for (T004ItemPedido item : listaItemPedidoSelect) {
            p4TablaItems +=
                    "<td class=\"tg-w6x5\">" + item.getCodItem() + "</b></td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getDescripcion() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getDescripcionExt1() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getDescripcionExt2() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getUnidadMedida() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getCantidad() + "</td>\n" +
                            "</tr>\n";
        }
        p5PuntoEnvio = "<p><strong> Punto de env&#237o: </strong>" + pedidoActual.getPuntoEnvio() + "<p>\n"
                + "<p><strong> Sucursal a facturar: </strong>" + pedidoActual.getSucursalFacturar() + "<p>\n"
                + "<p><strong> Sucursal a remisionar: </strong>" + pedidoActual.getSucursalRemisionar() + "<p>\n"
                + "<p><strong> Direcci&#243n de env&#237o: </strong>" + pedidoActual.getDireccion() + "<p>\n"
                + "<p><strong> Vendedor: </strong>" + pedidoActual.getTerceroVendedor() + "<p>\n"
                + "<p><strong> Contacto: </strong>" + pedidoActual.getContacto() + "<p>\n"
                + " </div>\n";
        p6mensajeErrorWb = pedidoActual.getMensajeWB();
        p7razonSocialCliente = pedidoActual.getIdUsuario().getNit() + " - " + pedidoActual.getIdUsuario().getCliente();
    }


    public void recargarConsultarPedidosUnoee()
            throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
        String fecha1 = dateFormat1.format(fechaParametro1);
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
        String fecha2 = dateFormat2.format(fechaParametro2);

        String query = "EXEC UnoEE.dbo.sp_pv_cons_pedido 1,'001',' ','',1,0," + rowIdCliente.trim() + ",0,0,'" + fecha1 + "','" + fecha2 + "',7,0,0,0,0,0,0,1,10092,'PORTALWEB-HISTORIALPEDIDOS',4300";
        listaPedidosUnoee = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (usuarioActual.getIdPuntoEnvio().equals("000")) {
                    pedidoUnoee = new PedidoUnoeeController();
                    pedidoUnoee.setRowid(rs.getInt("f_rowid"));
                    pedidoUnoee.setNumeroDocumento(rs.getString("f_nrodocto"));
                    pedidoUnoee.setRazonSocialCliente(rs.getString("f_cliente_fact_razon_soc"));
                    pedidoUnoee.setEstado(rs.getString("f_estado"));
                    pedidoUnoee.setNotas(rs.getString("f_notas"));
                    pedidoUnoee.setOrdenCompra(rs.getString("f_orden_compra"));
                    pedidoUnoee.setValorBrutoDocumento(rs.getBigDecimal("f_valor_bruto_docto"));
                    pedidoUnoee.setValorImpDocumento(rs.getBigDecimal("f_valor_imp_docto"));
                    pedidoUnoee.setValorNetoDocumento(rs.getBigDecimal("f_valor_neto_docto"));
                    pedidoUnoee.setDireccion1(rs.getString("f_direccion1"));
                    pedidoUnoee.setFecha(rs.getDate("f_fecha"));
                    pedidoUnoee.setFechaEntrega(rs.getDate("f_fecha_entrega"));
                    pedidoUnoee.setContacto(rs.getString("f_contacto"));
                    pedidoUnoee.setVendedor(rs.getString("f_vendedor_razon_soc"));
                    pedidoUnoee.setBarrio(rs.getString("f_desc_barrio"));
                    pedidoUnoee.setPuntoEnvio(rs.getString("f_desc_punto_envio"));
                    pedidoUnoee.setIdPuntoEnvio(rs.getString("f_punto_envio"));
                    pedidoUnoee.setSucursalFactura(rs.getString("f_cliente_resumen_factura"));
                    pedidoUnoee.setSucursalDespacho(rs.getString("f_cliente_resumen_despacho"));
                    listaPedidosUnoee.add(pedidoUnoee);
                } else if (usuarioActual.getIdPuntoEnvio().equals(rs.getString("f_punto_envio"))) {
                    pedidoUnoee = new PedidoUnoeeController();
                    pedidoUnoee.setRowid(rs.getInt("f_rowid"));
                    pedidoUnoee.setNumeroDocumento(rs.getString("f_nrodocto"));
                    pedidoUnoee.setRazonSocialCliente(rs.getString("f_cliente_fact_razon_soc"));
                    pedidoUnoee.setEstado(rs.getString("f_estado"));
                    pedidoUnoee.setNotas(rs.getString("f_notas"));
                    pedidoUnoee.setOrdenCompra(rs.getString("f_orden_compra"));
                    pedidoUnoee.setValorBrutoDocumento(rs.getBigDecimal("f_valor_bruto_docto"));
                    pedidoUnoee.setValorImpDocumento(rs.getBigDecimal("f_valor_imp_docto"));
                    pedidoUnoee.setValorNetoDocumento(rs.getBigDecimal("f_valor_neto_docto"));
                    pedidoUnoee.setDireccion1(rs.getString("f_direccion1"));
                    pedidoUnoee.setFecha(rs.getDate("f_fecha"));
                    pedidoUnoee.setFechaEntrega(rs.getDate("f_fecha_entrega"));
                    pedidoUnoee.setContacto(rs.getString("f_contacto"));
                    pedidoUnoee.setVendedor(rs.getString("f_vendedor_razon_soc"));
                    pedidoUnoee.setBarrio(rs.getString("f_desc_barrio"));
                    pedidoUnoee.setPuntoEnvio(rs.getString("f_desc_punto_envio"));
                    pedidoUnoee.setIdPuntoEnvio(rs.getString("f_punto_envio"));
                    pedidoUnoee.setSucursalFactura(rs.getString("f_cliente_resumen_factura"));
                    pedidoUnoee.setSucursalDespacho(rs.getString("f_cliente_resumen_despacho"));
                    listaPedidosUnoee.add(pedidoUnoee);
                }
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Recarga la consulta del item pedido.
    public void recargarConsultarItemsPedidosUnoee()
            throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        String query = "SELECT distinct [f431_rowid] ,[f431_rowid_pv_docto] ,t120.f120_id codItem ,t120.f120_descripcion descripcion ,t120.f120_id_unidad_inventario und ,CAST( LEFT(LTRIM(RTRIM(t121.f121_id_ext1_detalle)) + '    ', 4)as nvarchar(4))+ ' - '+LTRIM(RTRIM(t117.f117_descripcion)) ext1 ,CAST( LEFT(LTRIM(RTRIM(t121.f121_id_ext2_detalle)) + '    ', 5)as nvarchar(5))+ ' - '+LTRIM(RTRIM(t119.f119_descripcion)) ext2 ,t431.f431_cant1_pedida cantPedida ,t431.f431_precio_unitario_base precioUnd ,t431.f431_vlr_bruto valorBruto ,t431.f431_vlr_imp valorImp ,t431.f431_vlr_neto valorNeto , t431.f431_fecha_entrega fechaEntrega FROM [UnoEE].[dbo].[t431_cm_pv_movto] t431 inner join [UnoEE].[dbo].t121_mc_items_extensiones t121 on t121.f121_rowid = t431.f431_rowid_item_ext inner join [UnoEE].[dbo].t120_mc_items t120 on t120.f120_rowid = t121.f121_rowid_item inner join [UnoEE].[dbo].t117_mc_extensiones1_detalle t117 on t117.f117_id = t121.f121_id_ext1_detalle inner join [UnoEE].[dbo].t119_mc_extensiones2_detalle t119 on t119.f119_id = t121.f121_id_ext2_detalle where f431_rowid_pv_docto = " + pedidoUnoee.getRowid();
        listaPedidosUnoee = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                itemPedidoUnoee = new ItemPedidoUnoeeController();
                itemPedidoUnoee.setCodItem(rs.getString("codItem"));
                itemPedidoUnoee.setDescripcion(rs.getString("descripcion"));
                itemPedidoUnoee.setUnd(rs.getString("und"));
                itemPedidoUnoee.setExt1(rs.getString("ext1"));
                itemPedidoUnoee.setExt2(rs.getString("ext2"));
                itemPedidoUnoee.setCantPedida(rs.getBigDecimal("cantPedida"));
                itemPedidoUnoee.setPrecioUnd(rs.getBigDecimal("precioUnd"));
                itemPedidoUnoee.setValorBruto(rs.getBigDecimal("valorBruto"));
                itemPedidoUnoee.setValorImp(rs.getBigDecimal("valorImp"));
                itemPedidoUnoee.setValorNeto(rs.getBigDecimal("valorNeto"));
                itemPedidoUnoee.setFechaEntrega(rs.getDate("fechaEntrega"));
                listaItemPedidosUnoee.add(itemPedidoUnoee);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Datos asesor, se conecta a la base de datos de UnoEE
    public void obtenerDatosAsesorUnoEE(String idSucursal, String nit)
            throws SQLException {

        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        //Query que obtiene los datos del asesor.
        String query = "Select top 1 t200asesor.f200_nit nit_asesor, t015asesor.f015_email correo_asesor ,t200asesor.f200_razon_social nombre_asesor From UnoEE.dbo.t200_mm_terceros f inner join UnoEE.dbo.t204_mm_planes_criterios t204asesor on t204asesor.f204_id  = '005' left	join  UnoEE.dbo.t207_mm_criterios_clientes t207asesor on f207_id_cia = 1 and t207asesor.f207_rowid_tercero = f.f200_rowid  and  t207asesor.f207_id_sucursal = '" + idSucursal + "' and	 t207asesor.f207_id_plan_criterios = f204_id left	join  UnoEE.dbo.t206_mm_criterios_mayores t206asesor on f206_id_cia =1 		and	 t206asesor.f206_id_plan = f207_id_plan_criterios and	 t206asesor.f206_id = f207_id_criterio_mayor inner join  UnoEE.dbo.t200_mm_terceros t200asesor on t200asesor.f200_nit = convert(varchar,(convert(int, left (f206_descripcion, patindex('%[^0-9]%',f206_descripcion +' ')-1)))) inner join  UnoEE.dbo.t015_mm_contactos t015asesor on t200asesor.f200_rowid_contacto = t015asesor.f015_rowid where f.f200_nit = '" + nit + "'";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                nombreAsesor = rs.getString("nombre_asesor");
                correoAsesor = rs.getString("correo_asesor");
                nitAsesor = rs.getString("nit_asesor");
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Solicitar crear item
    public void prepareCrearItemEureka() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("http://eureka:8080/intranet/faces/procesarItemPedidoWeb.xhtml?id=" + itemPedidoActual.getIdItem());
    }


    //Realiza la descarga de estructura
    public DefaultStreamedContent getDescargarEstructura() {
        try {
            String newFileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/estructura.xlsx");
            return new DefaultStreamedContent(new FileInputStream(newFileName), "file/xlsx", "estructura.xlsx");
        } catch (Exception e) {
            return null;
        }
    }


    //Descarga la estructura de Ecuador.
    public DefaultStreamedContent getDescargarEstructuraEcu() {
        try {
            String newFileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/estructuraEcu.xlsx");
            return new DefaultStreamedContent(new FileInputStream(newFileName), "file/xlsx", "estructura.xlsx");
        } catch (Exception e) {
            return null;
        }
    }
}
