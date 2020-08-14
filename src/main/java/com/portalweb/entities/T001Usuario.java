    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.entities;

import com.portalweb.controller.util.DigestUtil;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lccabalr
 */
@Entity
@Table(name = "t001_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T001Usuario.findAll", query = "SELECT t FROM T001Usuario t")
    , @NamedQuery(name = "T001Usuario.findByIdUsuario", query = "SELECT t FROM T001Usuario t WHERE t.idUsuario = :idUsuario")
    , @NamedQuery(name = "T001Usuario.findByNombre", query = "SELECT t FROM T001Usuario t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "T001Usuario.findByCorreo", query = "SELECT t FROM T001Usuario t WHERE t.correo = :correo")
    , @NamedQuery(name = "T001Usuario.findByCorreoRecuperarContra", query = "SELECT t FROM T001Usuario t WHERE t.correo = :correo and t.nit = :nit and t.idSucursal = :idSucursal and t.idPuntoEnvio = :idPuntoEnvio")
    , @NamedQuery(name = "T001Usuario.findByCorreoRecuperarContraProveedor", query = "SELECT t FROM T001Usuario t WHERE t.correo = :correo and t.username = :username")
    , @NamedQuery(name = "T001Usuario.findByToken", query = "SELECT t FROM T001Usuario t WHERE t.token = :token")
    , @NamedQuery(name = "T001Usuario.findByContrasena", query = "SELECT t FROM T001Usuario t WHERE t.contrasena = :contrasena")
    , @NamedQuery(name = "T001Usuario.findByNit", query = "SELECT t FROM T001Usuario t WHERE t.nit = :nit")
    , @NamedQuery(name = "T001Usuario.findByUsername", query = "SELECT t FROM T001Usuario t WHERE t.username = :username")
    , @NamedQuery(name = "T001Usuario.findBySucursar", query = "SELECT t FROM T001Usuario t WHERE t.idSucursal = :idSucursal")
    , @NamedQuery(name = "T001Usuario.findByCliente", query = "SELECT t FROM T001Usuario t WHERE t.cliente = :cliente")
    , @NamedQuery(name = "T001Usuario.findByNitSucursal", query = "SELECT t FROM T001Usuario t WHERE t.idSucursal = :idSucursal and t.nit = :nit")
    , @NamedQuery(name = "T001Usuario.findByNitSucursalPuntoEnvio", query = "SELECT t FROM T001Usuario t WHERE t.idSucursal = :idSucursal and t.nit = :nit and t.idPuntoEnvio = :idPuntoEnvio")
    , @NamedQuery(name = "T001Usuario.findByPrimerLogin", query = "SELECT t FROM T001Usuario t WHERE t.primerLogin = :primerLogin")})

@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execValidarNitUsuarioNuevo",
    query = "SELECT top 1 f200_nit from [UnoEE].[dbo].[t200_mm_terceros] where f200_nit = #nit"
    )
    , 
    @NamedNativeQuery(
    name = "execValidarContrasenaUsuarioNuevo",
    query = "SELECT top 1 f200_nit from [UnoEE].[dbo].[t200_mm_terceros] where f200_nit = #contrasena"
    )
    , 
    @NamedNativeQuery(
    name = "execRazonSocialUsuarioNuevo",
    query = "SELECT top 1 f200_razon_social from [UnoEE].[dbo].[t200_mm_terceros] where f200_nit = #nit and f200_nit = #contrasena"
    )
    ,
    @NamedNativeQuery(
    name = "execSucursalNit",   
    query = "SELECT [f201_id_sucursal]+' - '+[f201_descripcion_sucursal] FROM [UnoEE].[dbo].[t201_mm_clientes] t201 inner join [UnoEE].[dbo].[t200_mm_terceros] t200 on t200.f200_rowid = t201.f201_rowid_tercero where t200.f200_nit = #nit and t200.f200_ind_estado = 1 "
    )
    , 
    @NamedNativeQuery(
    name = "execEntidadDinamicaLoginCliente",   
    query = "SELECT c.f753_dato_texto FROM [UnoEE].[dbo].[t201_mm_clientes] b inner join [UnoEE].[dbo].t200_mm_terceros x  on x.f200_rowid = b.f201_rowid_tercero inner join  [UnoEE].[dbo].t753_mm_movto_entidad_columna c on c.f753_rowid_movto_entidad=b.f201_rowid_movto_entidad inner join [UnoEE].[dbo].t743_mm_entidad_atributo d on c.f753_rowid_entidad_atributo=d.f743_rowid inner join [UnoEE].[dbo].t742_mm_entidad e on d.f743_rowid_entidad=e.f742_rowid where  e.f742_etiqueta='HABILITAR PORTAL WEB' and d.f743_id='HABILITAR PORTAL WEB' and x.f200_nit = #nit and b.f201_id_sucursal = #sucursal order by c.f753_fecha_ts_actualizacion desc")
    ,
    @NamedNativeQuery(
    name = "execEntidadDinamicaLoginProveedor",   
    query = "SELECT c.f753_dato_texto FROM [UnoEE].[dbo].[t202_mm_proveedores] b inner join [UnoEE].[dbo].t200_mm_terceros x  on x.f200_rowid = b.f202_rowid_tercero  inner join  [UnoEE].[dbo].t753_mm_movto_entidad_columna c on c.f753_rowid_movto_entidad=b.f202_rowid_movto_entidad inner join [UnoEE].[dbo].t743_mm_entidad_atributo d on c.f753_rowid_entidad_atributo=d.f743_rowid  inner join [UnoEE].[dbo].t742_mm_entidad e on d.f743_rowid_entidad=e.f742_rowid where  e.f742_etiqueta='HABILITAR PORTAL WEB' and d.f743_id='HABILITAR PORTAL WEB' and x.f200_nit = #nit and b.f202_id_sucursal = #sucursal order by c.f753_fecha_ts_actualizacion desc")
    ,
    @NamedNativeQuery(
    name = "execListaPuntoEnvio",
    query = "select f215_id + ' - ' +f215_descripcion + ' - ' +isnull(f013_descripcion,'') from [UnoEE].[dbo].t215_mm_puntos_envio_cliente inner join [UnoEE].[dbo].t200_mm_terceros t200 on t200.f200_rowid = [UnoEE].[dbo].t215_mm_puntos_envio_cliente.f215_rowid_tercero left outer join [UnoEE].[dbo].t015_mm_contactos on	f215_rowid_contacto = f015_rowid left outer join [UnoEE].[dbo].t011_mm_paises	on	f015_id_pais = f011_id left outer join [UnoEE].[dbo].t012_mm_deptos on f015_id_depto = f012_id and f015_id_pais = f012_id_pais left outer join [UnoEE].[dbo].t013_mm_ciudades	on	f015_id_ciudad = f013_id and f015_id_pais = f013_id_pais and f015_id_depto = f013_id_depto where t200.f200_nit = #nit and f215_id_sucursal = #sucursal and f215_ind_estado = 1"
    )
        ,
    @NamedNativeQuery(
    name = "execRazonSocialUsuarioNuevoProveedor",
    query = "SELECT top 1 f200_razon_social from [UnoEE].[dbo].[t200_mm_terceros] where f200_nit = #nit and f200_nit = #contrasena"
    )
    
})
public class T001Usuario implements Serializable {

    @Column(name = "condiciones_restricciones")
    private Boolean condicionesRestricciones;

    @Size(max = 300)
    @Column(name = "descripcion_punto_envio")
    private String descripcionPuntoEnvio;
    
    @Size(max = 10)
    @Column(name = "id_punto_envio")
    private String idPuntoEnvio;

    @Column(name = "intentos_bloqueo")
    private Short intentosBloqueo;

    @Column(name = "hora_token")
    @Temporal(TemporalType.TIME)
    private Date horaToken;

    @Size(max = 50)
    @Column(name = "token")
    private String token;
    
    @Column(name = "estado_token")
    private Boolean estadoToken;

    @Size(max = 20)
    @Column(name = "username")
    private String username;
    
    @Size(max = 10)
    @Column(name = "id_sucursal")
    private String idSucursal;

   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 100)
    @Column(name = "correo")
    private String correo;
    
    @Size(max = 200)
    @Column(name = "contrasena")
    private String contrasena;
    
    @Size(max = 20)
    @Column(name = "nit")
    private String nit;
    
    @Size(max = 300)
    @Column(name = "descripcion_sucursal")
    private String descripcionSucursal;
    
    @Size(max = 300)
    @Column(name = "cliente")
    private String cliente;
    
    @Column(name = "primer_login")
    private Boolean primerLogin;
    
    
    @JoinTable(name = "usuario_has_roles", joinColumns = {
        @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")}, inverseJoinColumns = {
        @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")})
    @ManyToMany
    private List<T002Rol> t002RolList;
    
    @OneToMany(mappedBy = "idUsuario")
    private List<T003Pedidos> t003PedidosList;

    public T001Usuario() {
    }

    public T001Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        try {
            this.contrasena = DigestUtil.generateDigest(contrasena);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(T001Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(T001Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDescripcionSucursal() {
        return descripcionSucursal;
    }

    public void setDescripcionSucursal(String descripcionSucursal) {
        this.descripcionSucursal = descripcionSucursal;
    }

    

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Boolean getPrimerLogin() {
        return primerLogin;
    }

    public void setPrimerLogin(Boolean primerLogin) {
        this.primerLogin = primerLogin;
    }


    //Obtiene la lista de roles
    @XmlTransient
    public List<T002Rol> getT002RolList() {
        return t002RolList;
    }

    public void setT002RolList(List<T002Rol> t002RolList) {
        this.t002RolList = t002RolList;
    }

    @XmlTransient
    public List<T003Pedidos> getT003PedidosList() {
        return t003PedidosList;
    }

    public void setT003PedidosList(List<T003Pedidos> t003PedidosList) {
        this.t003PedidosList = t003PedidosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T001Usuario)) {
            return false;
        }
        T001Usuario other = (T001Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEstadoToken() {
        return estadoToken;
    }

    public void setEstadoToken(Boolean estadoToken) {
        this.estadoToken = estadoToken;
    }

    

    public Date getHoraToken() {
        return horaToken;
    }

    public void setHoraToken(Date horaToken) {
        this.horaToken = horaToken;
    }

    public Short getIntentosBloqueo() {
        return intentosBloqueo;
    }

    public void setIntentosBloqueo(Short intentosBloqueo) {
        this.intentosBloqueo = intentosBloqueo;
    }

    public String getDescripcionPuntoEnvio() {
        return descripcionPuntoEnvio;
    }

    public void setDescripcionPuntoEnvio(String descripcionPuntoEnvio) {
        this.descripcionPuntoEnvio = descripcionPuntoEnvio;
    }

    public String getIdPuntoEnvio() {
        return idPuntoEnvio;
    }

    public void setIdPuntoEnvio(String idPuntoEnvio) {
        this.idPuntoEnvio = idPuntoEnvio;
    }

    public Boolean getCondicionesRestricciones() {
        return condicionesRestricciones;
    }

    public void setCondicionesRestricciones(Boolean condicionesRestricciones) {
        this.condicionesRestricciones = condicionesRestricciones;
    }
    
}
