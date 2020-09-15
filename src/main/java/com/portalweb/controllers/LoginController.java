/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.controllers;


import com.portalweb.entities.T001Usuario;
import com.portalweb.entities.T002Rol;
import com.portalweb.entities.T004ItemPedido;
import com.portalweb.sessions.T001UsuarioFacade;
import com.portalweb.sessions.T003PedidosFacade;
import com.portalweb.sessions.T004ItemPedidoFacade;
import java.io.Serializable;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@Named(value = "loginController")
@javax.enterprise.context.SessionScoped
public class LoginController implements Serializable {

    private static final Logger log = Logger.getLogger(LoginController.class.getName());
    private String nit;
    private String username;
    private String password;
    private String sucursal;
    private String puntoEnvio;
    private String razonSocial;
    private T001Usuario usuario;
    private T001Usuario usuarioActual;
    private List<T002Rol> listaRol = null;
    @EJB
    private T001UsuarioFacade usuarioFacade;
    @EJB
    private T003PedidosFacade pedidoFacade;
    @EJB
    private T004ItemPedidoFacade itemPedidoFacade;
    private List<T004ItemPedido> listaItemPedido;
    private LineChartModel dateModel;
    
    private List<String> listaSucursal = new ArrayList<>();
    private BarChartModel barModel1;
    
    private ItemPedidoUnoeeController itemPedidoUnoee;
    private List<ItemPedidoUnoeeController> listaItemPedidosUnoee = new ArrayList<>();
    private PedidoUnoeeController pedidoUnoee;
    private List<PedidoUnoeeController> listaPedidosUnoee = new ArrayList<>();
    private int contarTabla = 0;
    
    private UsuarioController usuarioController;
    private List<String> listaPuntoEnvio= new ArrayList<>();
    private String tipoUsuario;
    

            
    /*public LoginController() {
     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }*/

    
    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public T001Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(T001Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsername() {
        return username;
    }

    private T001UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }


    public List<T002Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<T002Rol> listaRol) {
        this.listaRol = listaRol;
    }

    public List<String> getListaSucursal() {
        return listaSucursal;
    }

    public void setListaSucursal(List<String> listaSucursal) {
        this.listaSucursal = listaSucursal;
    }


    public boolean isAuthenticated() {
        return getRequest().getUserPrincipal() != null;
    }

    public Principal getPrincipal() {
        return getRequest().getUserPrincipal();
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public void setDateModel(LineChartModel dateModel) {
        this.dateModel = dateModel;
    }

    public BarChartModel getBarModel1() {
        return barModel1;
    }

    public void setBarModel1(BarChartModel barModel1) {
        this.barModel1 = barModel1;
    }
    
    

    public T003PedidosFacade getPedidoFacade() {
        return pedidoFacade;
    }

    public void setPedidoFacade(T003PedidosFacade pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

    public T004ItemPedidoFacade getItemPedidoFacade() {
        return itemPedidoFacade;
    }

    public void setItemPedidoFacade(T004ItemPedidoFacade itemPedidoFacade) {
        this.itemPedidoFacade = itemPedidoFacade;
    }

    public List<T004ItemPedido> getListaItemPedido() {
        return listaItemPedido;
    }

    public void setListaItemPedido(List<T004ItemPedido> listaItemPedido) {
        this.listaItemPedido = listaItemPedido;
    }

    public int getContarTabla() {
        return contarTabla;
    }

    public void setContarTabla(int contarTabla) {
        this.contarTabla = contarTabla;
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

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public List<String> getListaPuntoEnvio() {
        return listaPuntoEnvio;
    }

    public void setListaPuntoEnvio(List<String> listaPuntoEnvio) {
        this.listaPuntoEnvio = listaPuntoEnvio;
    }

    public String getPuntoEnvio() {
        return puntoEnvio;
    }

    public void setPuntoEnvio(String puntoEnvio) {
        this.puntoEnvio = puntoEnvio;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private HttpServletRequest getRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Object request = externalContext.getRequest();
        return request instanceof HttpServletRequest ? (HttpServletRequest) request : null;
    }

    private String getLogueado() {
        return getPrincipal().getName();
    }
    
     public T001Usuario getUserLogueado() {  
        return getUsuarioFacade().findByUsername(getLogueado());
    }
     
      
     
    public boolean isAdmin() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("ADMIN")){
                return true;
            }           
        }
        return false;
    }
    public boolean isServicioClienteJefe() {
        if(isAuthenticated() == true){
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
                if(rol.getIdRol().equals("JSER")){
                    return true;
                }           
            }
        }
        return false;
    }
    
    //Revisa si el servicio esta dado para Cliente
    public boolean isServicioCliente() {
        if(isAuthenticated() == true){
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
                if(rol.getIdRol().equals("SER")){
                    return true;
                }           
            }
        }
        return false;
    }
    
    
    public boolean isCliente() {
        if(isAuthenticated() == true){
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
               if(rol.getIdRol().equals("CLI")){
                   return true;
               }           
           }
        }
        return false;
    }
    
    
     public boolean isProveedor() {
        if(isAuthenticated() == true){
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
                if(rol.getIdRol().equals("PRO")){
                    return true;
                }           
            }
        }
        return false;
    }
     public boolean isItemUnoee() {
        if(isAuthenticated() == true){
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
                if(rol.getIdRol().equals("ITE")){
                    return true;
                }           
            }
        }
        return false;
    }
     
     
     
     public boolean isPedidoEcuador() {
        if(isAuthenticated() == true){
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
                if(rol.getIdRol().equals("PE")){
                    return true;
                }           
            }
        }
        return false;
    }
    
     
     
    public void recargarSucursal(){
        listaSucursal = new ArrayList<>();
        sucursal = "";
        listaPuntoEnvio = new ArrayList<>();
        puntoEnvio = "";
        listaSucursal = getUsuarioFacade().listaSucursalNit(nit);
    }
    
    
    
    
    
    public void recargarPuntoEnvio(){
        listaPuntoEnvio = getUsuarioFacade().listaPuntoEnvio(nit, sucursal.substring(0, 3));
    }
    
    
    
    
    
    public void validarCorreoEditar(FacesContext contex, UIComponent component, Object value)throws ValidatorException{
        T001Usuario usuarioConsulta=getUsuarioFacade().findByCorreoEditar((String)value,usuarioActual.getCorreo());
        if(usuarioConsulta != null){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "correo repetido","El correo ya se encuentra registrado"));   
        }else{
            String correo=(String) value;
            usuarioActual.setCorreo(correo); 
        }
    }
    
    
    
    
    
    public void validarCorreo(FacesContext contex, UIComponent component, Object value)throws ValidatorException{
        T001Usuario usuarioConsulta=getUsuarioFacade().findByCorreo((String)value);
        if(usuarioConsulta != null){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username repetido","El correo ya se encuentra registrado"));   
        }else{
            String correo=(String) value;
            usuarioActual.setCorreo(correo); 
        }
    }
    
    
    
    
    
    public String addUsuario() throws ServletException {
        try {
            if(usuarioActual.getCondicionesRestricciones()== false){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar datos de acceso", "Debes aceptar las Condiciones y Restricciones");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }
            usuarioActual.setNit(nit);
            usuarioActual.setContrasena(password);            
            usuarioActual.setCliente(getRazonSocial());
            List<T002Rol> listaRolCrear = new ArrayList<>();
            listaRolCrear.add(new T002Rol(tipoUsuario));
            usuarioActual.setT002RolList(listaRolCrear);
            if(tipoUsuario.equals("CLI")){
                int tamanoSucursal = sucursal.length();
                int tamanoPuntoEnvio = puntoEnvio.length();
                usuarioActual.setIdSucursal(sucursal.substring(0, 3));
                usuarioActual.setDescripcionSucursal(sucursal.substring(6, tamanoSucursal));
                usuarioActual.setIdPuntoEnvio(puntoEnvio.substring(0, 3));
                usuarioActual.setDescripcionPuntoEnvio(puntoEnvio.substring(6, tamanoPuntoEnvio));
                usuarioActual.setUsername(usuarioActual.getIdSucursal()+usuarioActual.getIdPuntoEnvio()+usuarioActual.getNit());
                usuarioActual.setIntentosBloqueo((short)3);
                getUsuarioFacade().create(usuarioActual);
                username = usuarioActual.getUsername();
                getRequest().login(username, password);
                usuario = getUserLogueado();           
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                limpiar();
                return prepareDashboard();
            }else{
                usuarioActual.setIdSucursal(null);
                usuarioActual.setDescripcionSucursal(null);
                usuarioActual.setIdPuntoEnvio(null);
                usuarioActual.setDescripcionPuntoEnvio(null);
                usuarioActual.setUsername(usuarioActual.getNit());
                usuarioActual.setIntentosBloqueo((short)3);
                getUsuarioFacade().create(usuarioActual);
                username = usuarioActual.getUsername();
                getRequest().login(username, password);
                usuario = getUserLogueado();           
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                limpiar();
                return prepareDashboard();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar usuario"  , "Error al registrar usuario"));
            return null;
        }
    }
    
    
    
    
    public String loginCliente() throws ServletException, SQLException {
        String entidadDinamicaHabilitar= "";
        tipoUsuario = "CLI";
        try {
            
            if(sucursal==null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debes ingresar la sucursal"  , "Para iniciar sesión debes ingresar la sucursal."));
                return null;
            }
            
            List<String> entidadDinamica = getUsuarioFacade().validarEntidadDinamicaCliente(nit, sucursal.substring(0, 3));
            
            for (String s : entidadDinamica){
                entidadDinamicaHabilitar= s;
            }
            
            
            // Si la entidad dinamica habilitar es Si procede a entrar al sistema 
            if(entidadDinamicaHabilitar.equals("Si")){
                System.out.println("el nit es:" + nit);
                usuarioActual = getUsuarioFacade().findByNitSucursalPuntoEnvio(nit, sucursal.substring(0, 3),puntoEnvio.substring(0, 3) );
                username = sucursal.substring(0, 3)+puntoEnvio.substring(0, 3)+nit;
                getRequest().login(username, password);
                usuario = getUserLogueado();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                limpiar();
                return prepareDashboard();
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Su cuenta no se encuentra habilitada" , "Por favor comunicarse con su asesor comercial"));
                return null;
            } 
            
        } catch (ServletException ex) {
            username = sucursal.substring(0, 3)+puntoEnvio.substring(0, 3)+nit;
            List<String> razonSocial = getUsuarioFacade().nitContraseñaUsuarioNuevo(nit,password);
            List<String> nitConsulta = getUsuarioFacade().findByValidarUsername(username);
            List<String> entidadDinamica = getUsuarioFacade().validarEntidadDinamicaCliente(nit, sucursal.substring(0, 3));
            for (String s : entidadDinamica){
                entidadDinamicaHabilitar= s;
            }
            if(nitConsulta.isEmpty()){
                if(entidadDinamicaHabilitar.equals("Si")){
                    if(razonSocial.isEmpty()){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los datos ingresados no coinciden con ninguna cuenta."  , "Intentalo de nuevo"));
                        return null;
                    }else{
                        usuarioActual = new T001Usuario();
                        for (String s : razonSocial){
                            setRazonSocial(s);
                        }
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido","Bienvenido"));
                        return "registroUsuarioNuevo";
                    }
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Su cuenta no se encuentra habilitada"  , "Por favor comunicarse con su asesor comercial"));
                    return null;
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña esta errada. Si no logras acceder podrías restablecer tu contraseña de usuario en el enlace de ¿Contraseña olvidada?"  , ""));
                return null;
            }
        }
    }
    
    
    
    public String loginProveedor() throws ServletException, SQLException {
        boolean entidadDinamicaHabilitar= true;
        tipoUsuario = "PRO";
        try {
            // ENTIDAD DINAMICA PORTAL WEB - PROVEEDOR 
            /*List<String> entidadDinamica = getUsuarioFacade().validarEntidadDinamicaProveedor(nit, "100");
            for (String s : entidadDinamica){
                entidadDinamicaHabilitar= s;
            }*/
            if(entidadDinamicaHabilitar){
                usuarioActual = getUsuarioFacade().findByUsername(nit);
                username = nit;
                getRequest().login(username, password);
                usuario = getUserLogueado();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                limpiar();
                return prepareDashboard();
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Su cuenta no se encuentra habilitada, por favor comunicarse con su asesor comercial" , "No se encuentra habilitado, por favor comunicate con el area comercial"));
                return null;
            } 
        } catch (ServletException ex) {
            username = nit;
            List<String> razonSocial = getUsuarioFacade().nitContraseñaUsuarioNuevoProveedor(nit,password);
            List<String> nitConsulta = getUsuarioFacade().findByValidarUsername(username);
            List<String> entidadDinamica = getUsuarioFacade().validarEntidadDinamicaProveedor(nit, "100");
            /*for (String s : entidadDinamica){
                entidadDinamicaHabilitar= s;
            }*/
            if(nitConsulta.isEmpty()){
                if(entidadDinamicaHabilitar){
                    if(razonSocial.isEmpty()){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los datos ingresados no coinciden con ninguna cuenta."  , "Intentalo de nuevo."));
                        return null;
                    }else{
                        usuarioActual = new T001Usuario();
                        for (String s : razonSocial){
                            setRazonSocial(s);
                        }
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido","Bienvenido"));
                        return "registroUsuarioNuevo";
                    }
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Su cuenta no se encuentra habilitada."  , "Por favor comunicarse con su asesor comercial."));
                    return null;
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña esta errada."," Si no logras acceder podrías restablecer tu contraseña de usuario en el enlace de ¿Contraseña olvidada?"));
                return null;
            }
        }
    }
    //login admin
    public String loginAdmin() throws SQLException {
        tipoUsuario = "SER";
        try {
            //Login via the Servlet Context
            getRequest().login(username, password);
            usuario = getUserLogueado();
            //System.out.println(usuario);
            setUsuario(usuario);
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
            if(usuario.getPrimerLogin()){
                return "cambiarContrasena";
            }else{
                return prepareDashboard(); 
            }
        } catch (ServletException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede iniciar sesión"  , "Los datos ingresados no son correctos, por favor intentalo nuevamente"));
            return null;
        }
    }
    
    
    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Has cerrado sesión correctamente", "Gracias por usar nuestros servicios, hasta pronto"));
            return "/index";
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            return "/AccessDenied";
        }
    }
    
    public String updateUsuario() {
        try {
            usuario.setPrimerLogin(false);
            getUsuarioFacade().edit(usuario);
            addSuccessMessage("Usuario editado", "los datos fueron actualizados exitosamente");
            return prepareDashboard();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    
    private void limpiar() {
        username = "";
        password = "";
        sucursal = "";
        puntoEnvio = "";
        nit = "";
    }
    
    public String recuperarPassword(){
        return "recuperarPassword";
    }
    
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
    
     
     //dashboard
    //Crea todo lo concerniente al grafico de pedidos en una serie de fechas
    private void createDateModel() {
        dateModel = new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        
        LineChartSeries uno = new LineChartSeries();
        uno.setLabel(usuarioActual.getNit()+" - "+usuarioActual.getCliente());
        
        //Se itera en la lista ya cargada para usar un contador que por mayor iteraccion va guaradando en la BD
        for (PedidoUnoeeController items : listaPedidosUnoee) { 
            fecha1 = dateFormat1.format(items.getFecha());
            for (PedidoUnoeeController items2 : listaPedidosUnoee) { 
                fecha2 = dateFormat2.format(items2.getFecha());
                if(fecha1.equals(fecha2)){
                    cont++;
                } 
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        
        //Asigna valores para ajuste de fechas.
        String fechaMinString;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -14);
        
        Date quitarDias = cal.getTime();
        
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        
        String fechaMaxString;
        
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        
        Date ponerDias = cal.getTime();
        
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        
        
        
        dateModel.setSeriesColors("1a5493");
        dateModel.addSeries(uno);
        dateModel.setLegendPosition("w");
        dateModel.getAxis(AxisType.Y).setLabel("Cantidades");
        
        //Establece los ejes de las fechas
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel.setAnimate(true);
        dateModel.getAxes().put(AxisType.X, axis);
        dateModel.setExtender("customExtender");
        Axis xAxis = dateModel.getAxis(AxisType.X);
        xAxis.setTickInterval("1 day");
        Axis yAxis = dateModel.getAxis(AxisType.Y);
        yAxis.setTickInterval("5");
        yAxis.setMin(0);
    }
    
    
    
    
     private void createBarModel() {
        barModel1 = initBarModel();
        barModel1.setLegendPosition("ne");
        barModel1.setSeriesColors("f39c12,dd4b39,00c0ef,605ca8,f39c12,dd4b39,00c0ef,605ca8,dd4b39,f39c12");
        barModel1.setExtender("customExtender");
        Axis xAxis = barModel1.getAxis(AxisType.X);
        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        for (T004ItemPedido cantidadMaximaBar : getItemPedidoFacade().topPedidos(usuarioActual.getNit())) { 
            int cantidadDecimal = cantidadMaximaBar.getCantidad().intValue();
            String itervalo = String.valueOf((cantidadDecimal+100)/1);
            yAxis.setTickInterval(itervalo);
            break;
        }
    }    
    
    private BarChartModel initBarModel() {
        int pasarItem = 1; 
        BarChartModel model = new BarChartModel();
        ChartSeries uno = new ChartSeries();
        ChartSeries dos = new ChartSeries();
        ChartSeries tres = new ChartSeries();
        ChartSeries cuatro = new ChartSeries();
        ChartSeries cinco = new ChartSeries();
        ChartSeries seis = new ChartSeries();
        ChartSeries siete = new ChartSeries();
        ChartSeries ocho = new ChartSeries();
        ChartSeries nueve = new ChartSeries();
        ChartSeries diez = new ChartSeries();
        for (ItemPedidoUnoeeController items2 : listaItemPedidosUnoee) {
            int max = 500000;
            switch(pasarItem){
                case 1: uno.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            uno.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        uno.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 2: dos.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            dos.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        dos.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;     
                case 3: tres.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            tres.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        tres.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 4: cuatro.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            cuatro.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        cuatro.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 5: cinco.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            cinco.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        cinco.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 6: seis.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            seis.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        seis.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 7: siete.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            siete.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        siete.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 8: ocho.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            ocho.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        ocho.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 9: nueve.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            nueve.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        nueve.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;  
                case 10: diez.setLabel(items2.getDescripcion());
                        if(items2.getCantPedida().intValue()>max){
                            diez.set("Items pedidos ",max);
                            pasarItem ++;
                            break;    
                        }
                        diez.set("Items pedidos ",items2.getCantPedida());
                        pasarItem ++;
                        break;
            }
            
        }
        model.addSeries(uno);
        model.addSeries(dos);
        model.addSeries(tres);
        model.addSeries(cuatro);
        model.addSeries(cinco);
        model.addSeries(seis);
        model.addSeries(siete);
        model.addSeries(ocho);
        model.addSeries(nueve);
        model.addSeries(diez);
        return model;
    }
    
    //Recarga todas las listas para usar en el DashBoard
    public String prepareDashboard() throws SQLException{
        if(tipoUsuario.equals("CLI")){
            if(usuarioActual.getIdPuntoEnvio().equals("000")){
               recargarConsultarItemsPedidosUnoee();
               recargarConsultarPedidosUnoee();  
            }
            else{
               recargarConsultarItemsPedidosUnoeePuntoEnvio();
               recargarConsultarPedidosUnoeePuntoEnvio();
            } 
            createDateModel();
            //createBarModel();
        }
        return "/usuario/dashboard";
    }
    
    
    //Llena de datos la lista de item pedidos de UNOEE (los primeros 10)
    public void recargarConsultarItemsPedidosUnoee()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_PortalWeb;user=sa;password=3K4$.2015$#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "exec BD_PortalWeb.dbo.sp_top_item_pedido '"+usuarioActual.getNit()+"','"+usuarioActual.getIdSucursal()+"'";
        listaItemPedidosUnoee= new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            
            //pasa los datos resultantes del query a un objeto de tipo itempedidounoee
            while (rs.next()) {
                itemPedidoUnoee = new ItemPedidoUnoeeController();
                itemPedidoUnoee.setCodItem(rs.getString("codItem"));
                itemPedidoUnoee.setDescripcion(rs.getString("descripcion"));
                itemPedidoUnoee.setUnd(rs.getString("und"));
                itemPedidoUnoee.setExt1(rs.getString("ext1"));
                itemPedidoUnoee.setExt2(rs.getString("ext2"));
                itemPedidoUnoee.setCantPedida(rs.getBigDecimal("cantPedida"));
                listaItemPedidosUnoee.add(itemPedidoUnoee);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    
    //Carga lista de pedidos unoEE
    public void recargarConsultarPedidosUnoee()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE_2;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "exec BD_PortalWeb.dbo.sp_fecha_pedido '"+usuarioActual.getNit()+"','"+usuarioActual.getIdSucursal()+"'";
        listaPedidosUnoee = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                pedidoUnoee = new PedidoUnoeeController();
                pedidoUnoee.setRowid(rs.getInt("f_rowid"));
                pedidoUnoee.setFecha(rs.getDate("f_fecha"));
                listaPedidosUnoee.add(pedidoUnoee);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    
    //Carga lista de item pedidos por punto de envio
    public void recargarConsultarItemsPedidosUnoeePuntoEnvio()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_PortalWeb;user=sa;password=3K4$.2015$#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "exec BD_PortalWeb.dbo.sp_top_item_pedido_punto_envio '"+usuarioActual.getNit()+"','"+usuarioActual.getIdSucursal()+"','"+usuarioActual.getIdPuntoEnvio()+"'";
        listaItemPedidosUnoee= new ArrayList<>();
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
                listaItemPedidosUnoee.add(itemPedidoUnoee);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    
    //Carga lista de pedidos unoee por punto de envio
    public void recargarConsultarPedidosUnoeePuntoEnvio()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE_2;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "exec BD_PortalWeb.dbo.sp_fecha_pedido_punto_envio '"+usuarioActual.getNit()+"','"+usuarioActual.getIdSucursal()+"','"+usuarioActual.getIdPuntoEnvio()+"'";
        listaPedidosUnoee = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                pedidoUnoee = new PedidoUnoeeController();
                pedidoUnoee.setRowid(rs.getInt("f_rowid"));
                pedidoUnoee.setFecha(rs.getDate("f_fecha"));
                listaPedidosUnoee.add(pedidoUnoee);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    
    //Me envia a 
    public String prepareAyuda() {
       return "/ayuda";
    }
}
