/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.controllers;

import com.portalweb.entities.T001Usuario;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

/**
 *
 * @author Lccabalr
 */
@Named(value = "certificadoController")
@SessionScoped
public class CertificadoController implements Serializable {
    private String dircia;
    private String ciudadcia;
    private String razon;
    private String tipoid;
    private String ident;
    private String dirtercero;
    private String ciudadtercero;
    private String auxiliar;
    private String idaux;
    private BigDecimal tasa;
    private int tasabase;
    private BigDecimal base;
    private BigDecimal neto;
    private int bimestre;
    private Date desde;
    private Date hasta;
    private String firma;
    private String cargo;
    private BigDecimal totalneto;
    private int agrupapor;
    private String ciudad_practica;
    private String ciudad_consigna;
    private String f_email;
    private String f_nombre_archivo;
    private BigDecimal totalabonocta;
    private BigDecimal total_monto;
    private BigDecimal total_base;
    private BigDecimal total_retenido;
    private Date fechaExpedicion = new Date();
    private String numero_letras;
    
    Calendar cal= Calendar.getInstance();
    private int anioGabable = cal.get(Calendar.YEAR);
    private String mesInicio;
    private String mesFin;
    private String tipoCertificado;
    private String ciudadPracticoParam;
    private String ciudadConsignoParam;
    private String tituloCertificado;
    private int rowid_tercero;
    private T001Usuario usuarioActual;
    
    private List<CertificadoController> listaCertificadoRetencionIva = new ArrayList<>();
    
    private CertificadoController certificadoRetencionIvaActual;
    
    public CertificadoController() {
    }

    public String getDircia() {
        return dircia;
    }

    public void setDircia(String dircia) {
        this.dircia = dircia;
    }

    public String getCiudadcia() {
        return ciudadcia;
    }

    public void setCiudadcia(String ciudadcia) {
        this.ciudadcia = ciudadcia;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getTipoid() {
        return tipoid;
    }

    public void setTipoid(String tipoid) {
        this.tipoid = tipoid;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getDirtercero() {
        return dirtercero;
    }

    public void setDirtercero(String dirtercero) {
        this.dirtercero = dirtercero;
    }

    public String getCiudadtercero() {
        return ciudadtercero;
    }

    public void setCiudadtercero(String ciudadtercero) {
        this.ciudadtercero = ciudadtercero;
    }

    public String getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(String auxiliar) {
        this.auxiliar = auxiliar;
    }

    public String getIdaux() {
        return idaux;
    }

    public void setIdaux(String idaux) {
        this.idaux = idaux;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public int getTasabase() {
        return tasabase;
    }

    public void setTasabase(int tasabase) {
        this.tasabase = tasabase;
    }

    public BigDecimal getBase() {
        return base;
    }

    public void setBase(BigDecimal base) {
        this.base = base;
    }

    public BigDecimal getNeto() {
        return neto;
    }

    public void setNeto(BigDecimal neto) {
        this.neto = neto;
    }

    public int getBimestre() {
        return bimestre;
    }

    public void setBimestre(int bimestre) {
        this.bimestre = bimestre;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(BigDecimal totalneto) {
        this.totalneto = totalneto;
    }

    public int getAgrupapor() {
        return agrupapor;
    }

    public void setAgrupapor(int agrupapor) {
        this.agrupapor = agrupapor;
    }

    public String getCiudad_practica() {
        return ciudad_practica;
    }

    public void setCiudad_practica(String ciudad_practica) {
        this.ciudad_practica = ciudad_practica;
    }

    public String getCiudad_consigna() {
        return ciudad_consigna;
    }

    public void setCiudad_consigna(String ciudad_consigna) {
        this.ciudad_consigna = ciudad_consigna;
    }

    public String getF_email() {
        return f_email;
    }

    public void setF_email(String f_email) {
        this.f_email = f_email;
    }

    public String getF_nombre_archivo() {
        return f_nombre_archivo;
    }

    public void setF_nombre_archivo(String f_nombre_archivo) {
        this.f_nombre_archivo = f_nombre_archivo;
    }

    public BigDecimal getTotalabonocta() {
        return totalabonocta;
    }

    public void setTotalabonocta(BigDecimal totalabonocta) {
        this.totalabonocta = totalabonocta;
    }

    public List<CertificadoController> getListaCertificadoRetencionIva() {
        return listaCertificadoRetencionIva;
    }

    public void setListaCertificadoRetencionIva(List<CertificadoController> listaCertificadoRetencionIva) {
        this.listaCertificadoRetencionIva = listaCertificadoRetencionIva;
    }

    public CertificadoController getCertificadoRetencionIvaActual() {
        return certificadoRetencionIvaActual;
    }

    public void setCertificadoRetencionIvaActual(CertificadoController certificadoRetencionIvaActual) {
        this.certificadoRetencionIvaActual = certificadoRetencionIvaActual;
    }

    public BigDecimal getTotal_monto() {
        return total_monto;
    }

    public void setTotal_monto(BigDecimal total_monto) {
        this.total_monto = total_monto;
    }

    public BigDecimal getTotal_base() {
        return total_base;
    }

    public void setTotal_base(BigDecimal total_base) {
        this.total_base = total_base;
    }

    public BigDecimal getTotal_retenido() {
        return total_retenido;
    }

    public void setTotal_retenido(BigDecimal total_retenido) {
        this.total_retenido = total_retenido;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public int getAnioGabable() {
        return anioGabable;
    }

    public void setAnioGabable(int anioGabable) {
        this.anioGabable = anioGabable;
    }

    public String getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(String mesInicio) {
        this.mesInicio = mesInicio;
    }

    public String getMesFin() {
        return mesFin;
    }

    public void setMesFin(String mesFin) {
        this.mesFin = mesFin;
    }

    public String getNumero_letras() {
        return numero_letras;
    }

    public void setNumero_letras(String numero_letras) {
        this.numero_letras = numero_letras;
    }

    public String getTipoCertificado() {
        return tipoCertificado;
    }

    public void setTipoCertificado(String tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public String getCiudadPracticoParam() {
        return ciudadPracticoParam;
    }

    public void setCiudadPracticoParam(String ciudadPracticoParam) {
        this.ciudadPracticoParam = ciudadPracticoParam;
    }

    public String getCiudadConsignoParam() {
        return ciudadConsignoParam;
    }

    public void setCiudadConsignoParam(String ciudadConsignoParam) {
        this.ciudadConsignoParam = ciudadConsignoParam;
    }

    public String getTituloCertificado() {
        return tituloCertificado;
    }

    public void setTituloCertificado(String tituloCertificado) {
        this.tituloCertificado = tituloCertificado;
    }

    public int getRowid_tercero() {
        return rowid_tercero;
    }

    public void setRowid_tercero(int rowid_tercero) {
        this.rowid_tercero = rowid_tercero;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    
    public String prepareParametros(){
       tipoCertificado = "PCI";
       return "/usuario/proveedor/certificados/parametros";
    }
    public String prepareParametrosRetencion(){
        return "/usuario/proveedor/retencionFuente/parametros";
    }
    public void recargarConsultarIvaUnoEE()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "exec sp_certificado_retencion 1,2,1,'PCI','2367',0,"+getRowid_tercero()+",$0.0000,"+getAnioGabable()+","+getMesInicio()+","+getMesFin()+",'YM',NULL,NULL,'Cali','Cali',0,0,0,' '";
        listaCertificadoRetencionIva = new ArrayList<>();
        total_monto = new BigDecimal(0);
        total_base = new BigDecimal(0);
        total_retenido = new BigDecimal(0);
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                certificadoRetencionIvaActual = new CertificadoController();
                certificadoRetencionIvaActual.setDircia(rs.getString("dircia"));
                certificadoRetencionIvaActual.setCiudadcia(rs.getString("ciudadcia"));
                certificadoRetencionIvaActual.setRazon(rs.getString("razon"));
                certificadoRetencionIvaActual.setTipoid(rs.getString("tipoid"));
                certificadoRetencionIvaActual.setIdent(rs.getString("ident"));
                certificadoRetencionIvaActual.setDirtercero(rs.getString("dirtercero"));
                certificadoRetencionIvaActual.setCiudadtercero(rs.getString("ciudadtercero"));
                certificadoRetencionIvaActual.setAuxiliar(rs.getString("auxiliar"));
                certificadoRetencionIvaActual.setIdaux(rs.getString("idaux"));
                certificadoRetencionIvaActual.setTasa(rs.getBigDecimal("tasa"));
                certificadoRetencionIvaActual.setTasabase(rs.getInt("tasabase"));
                certificadoRetencionIvaActual.setBase(rs.getBigDecimal("base"));
                certificadoRetencionIvaActual.setNeto(rs.getBigDecimal("neto"));
                certificadoRetencionIvaActual.setBimestre(rs.getInt("bimestre"));
                certificadoRetencionIvaActual.setFirma(rs.getString("firma"));
                certificadoRetencionIvaActual.setCargo(rs.getString("cargo"));
                certificadoRetencionIvaActual.setTotalneto(rs.getBigDecimal("totalneto"));
                certificadoRetencionIvaActual.setAgrupapor(rs.getInt("agrupapor"));
                certificadoRetencionIvaActual.setCiudad_practica(rs.getString("ciudad_practica"));
                certificadoRetencionIvaActual.setCiudad_consigna(rs.getString("ciudad_consigna"));
                certificadoRetencionIvaActual.setF_email(rs.getString("f_email"));
                certificadoRetencionIvaActual.setF_nombre_archivo(rs.getString("f_nombre_archivo"));
                certificadoRetencionIvaActual.setTotalabonocta(rs.getBigDecimal("totalabonocta"));
                //SUMA PARA CALCULAR EL MONTO TOTAL
                total_monto = (total_monto).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VL BASE
                total_base = (total_base).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VR- RETENIDO
                total_retenido =  (total_retenido).add(certificadoRetencionIvaActual.getNeto());
                try {
                    certificadoRetencionIvaActual.setDesde(rs.getDate("desde"));
                    certificadoRetencionIvaActual.setHasta(rs.getDate("hasta"));
                } catch (Exception e) {
                    certificadoRetencionIvaActual.setDesde(null);
                    certificadoRetencionIvaActual.setHasta(null);
                }
                listaCertificadoRetencionIva.add(certificadoRetencionIvaActual);
            }
            for (CertificadoController encabezado : listaCertificadoRetencionIva) { 
                setRazon(encabezado.getRazon());
                setTipoid(encabezado.getTipoid());
                setIdent(encabezado.getIdent());
                setDirtercero(encabezado.getDirtercero());
                setCiudadtercero(encabezado.getCiudadtercero());
                setCiudad_practica(encabezado.getCiudad_practica());
                setCiudad_consigna(encabezado.getCiudad_consigna());
                setAuxiliar(encabezado.getAuxiliar());
                setFirma(encabezado.getFirma());
                setCargo(encabezado.getCargo());
                break;
            }
            mesInicio=mesTexto(Integer.valueOf(mesInicio));
            mesFin=mesTexto(Integer.valueOf(mesFin));
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void recargarConsultarRetencion()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "exec sp_certificado_retencion 1,0,2,'"+getTipoCertificado()+"','23',0,"+getRowid_tercero()+",$0.0000,"+getAnioGabable()+","+0+","+0+",'YM',NULL,NULL,'"+getCiudadPracticoParam()+"','"+getCiudadConsignoParam()+"',0,0,0,' '";
        listaCertificadoRetencionIva = new ArrayList<>();
        total_monto = new BigDecimal(0);
        total_base = new BigDecimal(0);
        total_retenido = new BigDecimal(0);
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                certificadoRetencionIvaActual = new CertificadoController();
                certificadoRetencionIvaActual.setDircia(rs.getString("dircia"));
                certificadoRetencionIvaActual.setCiudadcia(rs.getString("ciudadcia"));
                certificadoRetencionIvaActual.setRazon(rs.getString("razon"));
                certificadoRetencionIvaActual.setTipoid(rs.getString("tipoid"));
                certificadoRetencionIvaActual.setIdent(rs.getString("ident"));
                certificadoRetencionIvaActual.setDirtercero(rs.getString("dirtercero"));
                certificadoRetencionIvaActual.setCiudadtercero(rs.getString("ciudadtercero"));
                certificadoRetencionIvaActual.setAuxiliar(rs.getString("auxiliar"));
                certificadoRetencionIvaActual.setIdaux(rs.getString("idaux"));
                certificadoRetencionIvaActual.setTasa(rs.getBigDecimal("tasa"));
                certificadoRetencionIvaActual.setTasabase(rs.getInt("tasabase"));
                certificadoRetencionIvaActual.setBase(rs.getBigDecimal("base"));
                certificadoRetencionIvaActual.setNeto(rs.getBigDecimal("neto"));
                certificadoRetencionIvaActual.setBimestre(rs.getInt("bimestre"));
                certificadoRetencionIvaActual.setFirma(rs.getString("firma"));
                certificadoRetencionIvaActual.setCargo(rs.getString("cargo"));
                certificadoRetencionIvaActual.setTotalneto(rs.getBigDecimal("totalneto"));
                certificadoRetencionIvaActual.setAgrupapor(rs.getInt("agrupapor"));
                certificadoRetencionIvaActual.setCiudad_practica(rs.getString("ciudad_practica"));
                certificadoRetencionIvaActual.setCiudad_consigna(rs.getString("ciudad_consigna"));
                certificadoRetencionIvaActual.setF_email(rs.getString("f_email"));
                certificadoRetencionIvaActual.setF_nombre_archivo(rs.getString("f_nombre_archivo"));
                certificadoRetencionIvaActual.setTotalabonocta(rs.getBigDecimal("totalabonocta"));
                //SUMA PARA CALCULAR EL MONTO TOTAL
                total_monto = (total_monto).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VL BASE
                total_base = (total_base).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VR- RETENIDO
                total_retenido =  (total_retenido).add(certificadoRetencionIvaActual.getNeto());
                try {
                    certificadoRetencionIvaActual.setDesde(rs.getDate("desde"));
                    certificadoRetencionIvaActual.setHasta(rs.getDate("hasta"));
                } catch (Exception e) {
                    certificadoRetencionIvaActual.setDesde(null);
                    certificadoRetencionIvaActual.setHasta(null);
                }
                listaCertificadoRetencionIva.add(certificadoRetencionIvaActual);
            }
            for (CertificadoController encabezado : listaCertificadoRetencionIva) { 
                setRazon(encabezado.getRazon());
                setTipoid(encabezado.getTipoid());
                setIdent(encabezado.getIdent());
                setDirtercero(encabezado.getDirtercero());
                setCiudadtercero(encabezado.getCiudadtercero());
                setCiudad_practica(encabezado.getCiudad_practica());
                setCiudad_consigna(encabezado.getCiudad_consigna());
                setFirma(encabezado.getFirma());
                setCargo(encabezado.getCargo());
                setTotalabonocta(encabezado.getTotalabonocta());
                break;
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public boolean getBimestreTable(){
        if(certificadoRetencionIvaActual.getBimestre()==0){
            return false;
        }else{
            return true;
        }
    }
    public String mesTexto(int mes){
        switch (mes){
            case 1:
                return "ENERO";

            case 2:
                return "FEBRERO";
           
            case 3:
                return "MARZO";

            case 4:
                return "ABRIL";
            
            case 5:
                return "MAYO";

            case 6:
                return "JUNIO";
            
            case 7:
                return "JULIO";

            case 8:
                return "AGOSTO";
            
            case 9:
                return "SEPTIEMBRE";

            case 10:
                return "OCTUBRE";
            
            case 11:
                return "NOVIEMBRE";

            case 12:
                return "DICIEMBRE";
            
            default: 
                return "NO";   
        }
    }
    public String getConvertirNumero(){
        ConvertNumero num = new ConvertNumero();
        double aDouble = total_retenido.doubleValue();
        String numero = Double.toString(aDouble);
        return num.Convertir(numero, true);
    }
    public void createPDF() throws SQLException{
        obtenerRowidTercero();
        if(getTipoCertificado().equals("PCI")){
            createPDFIva();
        }else if(getTipoCertificado().equals("PCC")){
            tituloCertificado = "CERTIFICADO DE RETENCIÓN EN LA FUENTE";
            ciudadConsignoParam = "Cali";
            ciudadPracticoParam = "Cali";
            createPDFRetencion();
        }else if(getTipoCertificado().equals("PIC")){
            tituloCertificado = "CERTIFICADO DE RETENCIÓN EN LA FUENTE - CALI";
            ciudadConsignoParam = "Cali";
            ciudadPracticoParam = "Cali";
            createPDFRetencion();
        }else if(getTipoCertificado().equals("PIY")){
            tituloCertificado = "CERTIFICADO DE RETENCIÓN EN LA FUENTE - YUMBO";
            ciudadConsignoParam = "Yumbo";
            ciudadPracticoParam = "Yumbo";
            createPDFRetencion();
        }
    }
    public void createPDFIva() throws SQLException{
        int inicio = Integer.valueOf(mesInicio);
        int fin = Integer.valueOf(mesInicio)+1;
        setMesFin(String.valueOf(fin));
        Calendar calMesActual= Calendar.getInstance();
        int mesFinActual = calMesActual.get(Calendar.MONTH);
        Calendar calAnioActual= Calendar.getInstance();
        int anioActual = calAnioActual.get(Calendar.YEAR);
        if(fin<inicio){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la generación del documento", "La fecha final no puede ser menor que la fecha de inicio");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }else if(anioGabable > anioActual ){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la generación del documento", "El documento no se encuentra disponible, el año es mayor al año actual");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }else if(fin>mesFinActual && anioGabable == anioActual){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la generación del documento", "El documento no se encuentra disponible");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }else{
            recargarConsultarIvaUnoEE();
            if(listaCertificadoRetencionIva.isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la generación del documento", "No se le aplico retención en el periodo seleccionado.");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else{
                    FacesContext facesContext = FacesContext.getCurrentInstance(); 
                    ExternalContext externalContext = facesContext.getExternalContext(); 
                    HttpSession session = (HttpSession) externalContext.getSession(true); 

                try {
                    String url ="http" + "://" + "192.168.160.181" + ":" + "8080" + "/portalweb" + "/faces/usuario/proveedor/certificados/pdfIva.xhtml;jsessionid="+ session.getId()+"?pdf=true"; 
                    //String url ="http" + "://" + "localhost" + ":" + "8080" + "/PortalWeb" + "/faces/usuario/proveedor/certificados/pdfIva.xhtml;jsessionid="+ session.getId()+"?pdf=true"; 
                    ITextRenderer renderer = new ITextRenderer();
                    renderer.setDocument(new URL(url).toString());
                    renderer.layout();
                    PDFEncryption pdfEncryption  = new PDFEncryption();
                    String password= usuarioActual.getNit();
                    pdfEncryption.setUserPassword(password.getBytes());
                    renderer.setPDFEncryption(pdfEncryption);
                    HttpServletResponse response=(HttpServletResponse) externalContext.getResponse();
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=\"print-file.pdf\"");
                    OutputStream outputStream=response.getOutputStream();
                    renderer.createPDF(outputStream);
                    
                } catch (Exception ex) {
                     Logger.getLogger(CertificadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                facesContext.responseComplete();
            }
        }
    }
     public void createPDFRetencion() throws SQLException{
        Calendar calAnioActual= Calendar.getInstance();
        int anioActual = calAnioActual.get(Calendar.YEAR);
        if(anioGabable >= anioActual ){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la generación del documento", "El documento no se encuentra disponible");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }else{
            recargarConsultarRetencion();
            if(listaCertificadoRetencionIva.isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la generación del documento", "No se le aplico retención en el periodo seleccionado.");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else{
                FacesContext facesContext= FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                HttpSession session= (HttpSession) externalContext.getSession(true);
                                
                try {
                    String url;
                    url="http" + "://" + "192.168.160.181" + ":" + "8080" + "/portalweb" + "/faces/usuario/proveedor/certificados/pdfRetenciones.xhtml;jsessionid=" + session.getId()+"?pdf=true";
                    ITextRenderer renderer = new ITextRenderer();
                    renderer.setDocument(new URL(url).toString());
                    renderer.layout();
                    PDFEncryption pdfEncryption  = new PDFEncryption();
                    String password= usuarioActual.getNit();
                    pdfEncryption.setUserPassword(password.getBytes());
                    renderer.setPDFEncryption(pdfEncryption);
                    HttpServletResponse response=(HttpServletResponse) externalContext.getResponse();
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=\"print-file.pdf\"");
                    OutputStream outputStream=response.getOutputStream();
                    renderer.createPDF(outputStream);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                facesContext.responseComplete();
            }
        }
    }
     
     public boolean getCertificadoIva(){
        return tipoCertificado.equals("PCI");
    }
    public void obtenerRowidTercero() throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "select top 1 f200_rowid id_tercero from UnoEE.dbo.t200_mm_terceros where f200_nit = '"+getUsuarioActual().getNit()+"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                rowid_tercero = rs.getInt("id_tercero");
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    @Override
    public String toString() {
        return "";
    }    
}
