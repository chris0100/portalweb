/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
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
@Cacheable(false)
@Entity
@Table(name = "t003_pedidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T003Pedidos.findAll", query = "SELECT t FROM T003Pedidos t")
    , @NamedQuery(name = "T003Pedidos.findByIdPedido", query = "SELECT t FROM T003Pedidos t WHERE t.idPedido = :idPedido")
    , @NamedQuery(name = "T003Pedidos.findByCodPedido", query = "SELECT t FROM T003Pedidos t WHERE t.codPedido = :codPedido")
    , @NamedQuery(name = "T003Pedidos.findByNota", query = "SELECT t FROM T003Pedidos t WHERE t.nota = :nota")
    , @NamedQuery(name = "T003Pedidos.findByEstado", query = "SELECT t FROM T003Pedidos t WHERE t.estadoWB = :estadoWB order by t.fechaPedido desc")
    , @NamedQuery(name = "T003Pedidos.findByEstadoAsesor", query = "SELECT t FROM T003Pedidos t WHERE t.estadoWB = :estadoWB and t.terceroAsesor = :terceroAsesor order by t.fechaPedido desc")
    , @NamedQuery(name = "T003Pedidos.findByEstadoNitWB", query = "SELECT t FROM T003Pedidos t WHERE t.idUsuario.nit = :nit and t.estadoWB = :estadoWB order by t.fechaPedido desc")
    , @NamedQuery(name = "T003Pedidos.findByFechaPedido", query = "SELECT t FROM T003Pedidos t WHERE t.fechaPedido = :fechaPedido")
    , @NamedQuery(name = "T003Pedidos.findByTotalBruto", query = "SELECT t FROM T003Pedidos t WHERE t.totalBruto = :totalBruto")
    , @NamedQuery(name = "T003Pedidos.findByDescuentos", query = "SELECT t FROM T003Pedidos t WHERE t.descuentos = :descuentos")
    , @NamedQuery(name = "T003Pedidos.findByImpuestos", query = "SELECT t FROM T003Pedidos t WHERE t.impuestos = :impuestos")
    , @NamedQuery(name = "T003Pedidos.findByTotal", query = "SELECT t FROM T003Pedidos t WHERE t.total = :total")
    , @NamedQuery(name = "T003Pedidos.findBySucursalRemisionar", query = "SELECT t FROM T003Pedidos t WHERE t.sucursalRemisionar = :sucursalRemisionar")
    , @NamedQuery(name = "T003Pedidos.findBySucursalFacturar", query = "SELECT t FROM T003Pedidos t WHERE t.sucursalFacturar = :sucursalFacturar")
    , @NamedQuery(name = "T003Pedidos.findByPuntoEnvio", query = "SELECT t FROM T003Pedidos t WHERE t.puntoEnvio = :puntoEnvio")
    , @NamedQuery(name = "T003Pedidos.findByTerceroVendedor", query = "SELECT t FROM T003Pedidos t WHERE t.terceroVendedor = :terceroVendedor")
    , @NamedQuery(name = "T003Pedidos.findByContacto", query = "SELECT t FROM T003Pedidos t WHERE t.contacto = :contacto")
    , @NamedQuery(name = "T003Pedidos.findByDireccion", query = "SELECT t FROM T003Pedidos t WHERE t.direccion = :direccion")
    , @NamedQuery(name = "T003Pedidos.findByNit", query = "SELECT t FROM T003Pedidos t WHERE t.idUsuario.nit = :nit order by t.fechaPedido desc")
    , @NamedQuery(name = "T003Pedidos.findByNitEstado", query = "SELECT t FROM T003Pedidos t WHERE t.idUsuario.nit = :nit and t.estado = :estado order by t.fechaPedido desc")
    , @NamedQuery(name = "T003Pedidos.findByOc", query = "SELECT t FROM T003Pedidos t WHERE t.oc = :oc and t.idUsuario.nit =:nit")})
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
            name = "execObtenerPuntoEnvio", 
            procedureName = "dbo.sp_obtener_punto_envio", 
            parameters = { 
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit"), 
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codigo"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "punto") 
            }    
        ),
        @NamedStoredProcedureQuery(
            name = "execObtenerConectorPedido", 
            procedureName = "dbo.sp_conector_pedido", 
            parameters = { 
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "idPedido")
            }  
        ),
        @NamedStoredProcedureQuery(
            name = "execObtenerPedidoUnoee",  
            procedureName = "UnoEE.dbo.sp_pv_cons_pedido 1,'001','PC ','',1,0,0,0,0,'20190206','20190227',7,0,0,0,0,0,0,1,10092,'consultaWeb',4300 "   
        )
    }
)
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execListaPuntoEnvio1",
    query = "select f215_id + ' - ' +f215_descripcion + ' - ' +isnull(f013_descripcion,'') from [UnoEE].[dbo].t215_mm_puntos_envio_cliente inner join [UnoEE].[dbo].t200_mm_terceros t200 on t200.f200_rowid = [UnoEE].[dbo].t215_mm_puntos_envio_cliente.f215_rowid_tercero left outer join [UnoEE].[dbo].t015_mm_contactos	on	f215_rowid_contacto = f015_rowid left outer join [UnoEE].[dbo].t011_mm_paises	on	f015_id_pais = f011_id left outer join [UnoEE].[dbo].t012_mm_deptos on f015_id_depto = f012_id and f015_id_pais = f012_id_pais left outer join [UnoEE].[dbo].t013_mm_ciudades	on	f015_id_ciudad = f013_id and f015_id_pais = f013_id_pais and f015_id_depto = f013_id_depto where t200.f200_nit = #nit and f215_id_sucursal = #sucursal and f215_ind_estado = 1"
    )
    ,
    @NamedNativeQuery(
    name = "execRowIdNit",
    query = "select top 1 convert(varchar(20),f200_rowid) from [UnoEE].[dbo].t200_mm_terceros where f200_nit = #nit"
    )
})

public class T003Pedidos implements Serializable {

    @Size(max = 150)
    @Column(name = "tercero_asesor")
    private String terceroAsesor;

    @Column(name = "estadoWB")
    private Short estadoWB;

    @Size(max = 1073741823)
    @Column(name = "mensajeWB")
    private String mensajeWB;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pedido")
    private Integer idPedido;
    @Size(max = 10)
    @Column(name = "cod_pedido")
    private String codPedido;
    @Size(max = 1073741823)
    @Column(name = "nota")
    private String nota;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "fecha_pedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_bruto")
    private BigDecimal totalBruto;
    @Column(name = "descuentos")
    private BigDecimal descuentos;
    @Column(name = "impuestos")
    private BigDecimal impuestos;
    @Column(name = "total")
    private BigDecimal total;
    @Size(max = 100)
    @Column(name = "sucursal_remisionar")
    private String sucursalRemisionar;
    @Size(max = 100)
    @Column(name = "sucursal_facturar")
    private String sucursalFacturar;
    @Size(max = 150)
    @Column(name = "punto_envio")
    private String puntoEnvio;
    @Size(max = 150)
    @Column(name = "tercero_vendedor")
    private String terceroVendedor;
    @Size(max = 100)
    @Column(name = "contacto")
    private String contacto;
    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 50)
    @Column(name = "oc")
    private String oc;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private T001Usuario idUsuario;
    @OneToMany(mappedBy = "idPedido")
    private List<T004ItemPedido> t004ItemPedidoList;

    public T003Pedidos() {
    }

    public T003Pedidos(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Short getEstado() {
        return estado;
    }
     public String getEstadoString() {
         if(estado==0){
             return "EN BORRADOR";
         }
         else{
             return "APROBADO";
         }
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public BigDecimal getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(BigDecimal totalBruto) {
        this.totalBruto = totalBruto;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getSucursalRemisionar() {
        return sucursalRemisionar;
    }

    public void setSucursalRemisionar(String sucursalRemisionar) {
        this.sucursalRemisionar = sucursalRemisionar;
    }

    public String getSucursalFacturar() {
        return sucursalFacturar;
    }

    public void setSucursalFacturar(String sucursalFacturar) {
        this.sucursalFacturar = sucursalFacturar;
    }

    public String getPuntoEnvio() {
        return puntoEnvio;
    }

    public void setPuntoEnvio(String puntoEnvio) {
        this.puntoEnvio = puntoEnvio;
    }

    public String getTerceroVendedor() {
        return terceroVendedor;
    }

    public void setTerceroVendedor(String terceroVendedor) {
        this.terceroVendedor = terceroVendedor;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getOc() {
        return oc;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }

    public T001Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(T001Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<T004ItemPedido> getT004ItemPedidoList() {
        return t004ItemPedidoList;
    }

    public void setT004ItemPedidoList(List<T004ItemPedido> t004ItemPedidoList) {
        this.t004ItemPedidoList = t004ItemPedidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPedido != null ? idPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T003Pedidos)) {
            return false;
        }
        T003Pedidos other = (T003Pedidos) object;
        if ((this.idPedido == null && other.idPedido != null) || (this.idPedido != null && !this.idPedido.equals(other.idPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clientesEka.entities.T003Pedidos[ idPedido=" + idPedido + " ]";
    }

    public String getMensajeWB() {
        return mensajeWB;
    }

    public void setMensajeWB(String mensajeWB) {
        this.mensajeWB = mensajeWB;
    }

    public Short getEstadoWB() {
        return estadoWB;
    }

    public void setEstadoWB(Short estadoWB) {
        this.estadoWB = estadoWB;
    }

    public String getTerceroAsesor() {
        return terceroAsesor;
    }

    public void setTerceroAsesor(String terceroAsesor) {
        this.terceroAsesor = terceroAsesor;
    }
    
}
