/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lccabalr
 */
@Cacheable(false)
@Entity
@Table(name = "t004_item_pedido")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "T004ItemPedido.findAll", query = "SELECT t FROM T004ItemPedido t")
        , @NamedQuery(name = "T004ItemPedido.findByIdItem", query = "SELECT t FROM T004ItemPedido t WHERE t.idItem = :idItem")
        , @NamedQuery(name = "T004ItemPedido.findByCodCliente", query = "SELECT t FROM T004ItemPedido t WHERE t.codCliente = :codCliente")
        , @NamedQuery(name = "T004ItemPedido.findByCodItem", query = "SELECT t FROM T004ItemPedido t WHERE t.codItem = :codItem")
        , @NamedQuery(name = "T004ItemPedido.findByDescripcion", query = "SELECT t FROM T004ItemPedido t WHERE t.descripcion = :descripcion")
        , @NamedQuery(name = "T004ItemPedido.findByIdExt1Detalle", query = "SELECT t FROM T004ItemPedido t WHERE t.idExt1Detalle = :idExt1Detalle")
        , @NamedQuery(name = "T004ItemPedido.findByIdExt2Detalle", query = "SELECT t FROM T004ItemPedido t WHERE t.idExt2Detalle = :idExt2Detalle")
        , @NamedQuery(name = "T004ItemPedido.findByDescripcionExt1", query = "SELECT t FROM T004ItemPedido t WHERE t.descripcionExt1 = :descripcionExt1")
        , @NamedQuery(name = "T004ItemPedido.findByDescripcionExt2", query = "SELECT t FROM T004ItemPedido t WHERE t.descripcionExt2 = :descripcionExt2")
        , @NamedQuery(name = "T004ItemPedido.findByDescripcionCompletaItem", query = "SELECT t FROM T004ItemPedido t WHERE t.descripcionCompletaItem = :descripcionCompletaItem")
        , @NamedQuery(name = "T004ItemPedido.findByCantidad", query = "SELECT t FROM T004ItemPedido t WHERE t.cantidad = :cantidad")
        , @NamedQuery(name = "T004ItemPedido.findByUnidadMedida", query = "SELECT t FROM T004ItemPedido t WHERE t.unidadMedida = :unidadMedida")
        , @NamedQuery(name = "T004ItemPedido.findByIdPedido", query = "SELECT t FROM T004ItemPedido t WHERE t.idPedido = :idPedido")
        , @NamedQuery(name = "T004ItemPedido.findByPrecioUnitario", query = "SELECT t FROM T004ItemPedido t WHERE t.precioUnitario = :precioUnitario")})
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "execItemCliente",
                procedureName = "dbo.sp_lista_item_cliente",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit")
                }
        ),
        @NamedStoredProcedureQuery(
                name = "execHomologarItem",
                procedureName = "dbo.sp_homologar_cod_cliente",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codigo")
                }
        ),
        @NamedStoredProcedureQuery(
                name = "execConsultarItemCodBarra",
                procedureName = "dbo.sp_consultar_cod_barra_item",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codigo")
                }
        ),
        @NamedStoredProcedureQuery(
                name = "execContarHomologarItem",
                procedureName = "dbo.sp_contar_item_homologado",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit")
                }
        )
        ,
        @NamedStoredProcedureQuery(
                name = "execListaExt1Q",
                procedureName = "dbo.sp_obtener_ext1",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codItem"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit")
                }
        )
        ,
        @NamedStoredProcedureQuery(
                name = "execListaExt2Q",
                procedureName = "dbo.sp_obtener_ext2",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codItem"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "ext1"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit")
                }
        )
        ,
        @NamedStoredProcedureQuery(
                name = "execEjecutarSincronizarItems",
                procedureName = "dbo.sp_sincronizar_item",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "idPedido"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit")
                }
        )
        ,
        @NamedStoredProcedureQuery(
                name = "execObtenerPrecio",
                procedureName = "dbo.sp_obtener_precio_item_ecuador",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codItem"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "unidadMedia")
                }
        )
        ,
        @NamedStoredProcedureQuery(
                name = "execObtenerFechaPrecio",
                procedureName = "dbo.sp_obtener_fecha_precio_item_ecuador",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codItem"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nit"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "unidadMedia")
                }
        ),
        //execObtenerItemCodBarras  PARTE NUEVA
        @NamedStoredProcedureQuery(
                name = "execObtenerItemCodBarras",
                procedureName = "dbo.sp_obtener_item_cod_barras",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "codigo_barras"),

                }
        )
}
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "execListaUsuarioNuevoERP",
                query = "SELECT CAST( LEFT(LTRIM(RTRIM(f117_id)) + '    ', 4)as nvarchar(4)) + ' -'+LTRIM(RTRIM(f117_descripcion)) FROM UnoEE.dbo.t117_mc_extensiones1_detalle WHERE f117_id_extension1 = #tipoExt"
        )
        ,
        @NamedNativeQuery(
                name = "execListaExt1",
                query = "SELECT CAST( LEFT(LTRIM(RTRIM(f117_id)) + '    ', 4)as nvarchar(4)) + ' -'+LTRIM(RTRIM(f117_descripcion)) FROM UnoEE.dbo.t117_mc_extensiones1_detalle WHERE f117_id_extension1 = #tipoExt ORDER BY f117_id ASC "
        ),
        @NamedNativeQuery(
                name = "execListaExt2",
                query = "SELECT LTRIM(RTRIM(f119_id)) + ' -' + LTRIM(RTRIM(f119_descripcion)) FROM UnoEE.dbo.t119_mc_extensiones2_detalle WHERE f119_id_extension2 = #tipoExt ORDER BY f119_id ASC "
        ),
        @NamedNativeQuery(
                name = "execListaItemPatron",
                query = "SELECT CAST( RIGHT('0000000'+convert(varchar,isnull( f120_id ,'')),7)as nvarchar(7)) +' | ' + CAST( LEFT(f120_descripcion + '                                        ', 40)as nvarchar(40)) FROM UnoEE.dbo.t120_mc_items t120 inner join UnoEE.dbo.t149_mc_tipo_inv_serv t149 ON t149.f149_id = t120.f120_id_tipo_inv_serv where t120.f120_id_tipo_inv_serv like '%.ex%' and t120.f120_ind_venta = 1 ORDER BY f120_id ASC "
        ),
        @NamedNativeQuery(
                name = "execListaTipoInventario",
                query = "SELECT  CAST( RIGHT('0000000000'+convert(varchar,isnull( f149_id ,'')),10)as nvarchar(10))  + CAST( RIGHT('  '+convert(varchar,isnull( f149_id_extension1 ,'')),2)as nvarchar(2))  + CAST( RIGHT('  '+convert(varchar,isnull( f149_id_extension2 ,'')),2)as nvarchar(2))  +f149_descripcion FROM UnoEE.dbo.t149_mc_tipo_inv_serv WHERE f149_id like '%.ex%'"
        ),
        @NamedNativeQuery(
                name = "execListaItemRepetido",
                query = "SELECT f120_id,f121_id_ext1_detalle ,f121_id_ext2_detalle FROM [UnoEE].[dbo].[t121_mc_items_extensiones] t121 inner join [UnoEE].[dbo].[t120_mc_items] t120 on t120.f120_rowid = t121.f121_rowid_item where T120.f120_id = #codigoItem and t121.f121_id_ext1_detalle = #ext1 and t121.f121_id_ext2_detalle = #ext2 and t121.f121_ind_estado = 1"
        ),
        @NamedNativeQuery(
                name = "execListaItemInactivo",
                query = "SELECT f120_id,f121_id_ext1_detalle ,f121_id_ext2_detalle FROM [UnoEE].[dbo].[t121_mc_items_extensiones] t121 inner join [UnoEE].[dbo].[t120_mc_items] t120 on t120.f120_rowid = t121.f121_rowid_item where T120.f120_id = #codigoItem and t121.f121_id_ext1_detalle = #ext1 and t121.f121_id_ext2_detalle = #ext2 and t121.f121_ind_estado = 0"
        ),
        @NamedNativeQuery(
                name = "execHomologarItem2",
                query = "dbo.sp_homologar_cod_cliente"
        ),
        @NamedNativeQuery(
                name = "execUnidadInventario",
                query = "SELECT [f120_id_unidad_inventario] FROM [UnoEE].[dbo].[t120_mc_items] where f120_id = #codItem"
        ),
        @NamedNativeQuery(
                name = "execDecimalesUnidadInventario",
                query = "SELECT TOP 1 CAST( LEFT([f101_decimales] + ' ', 1)as nvarchar(1)) FROM [UnoEE].[dbo].[t101_mc_unidades_medida] where f101_id = #unidad"
        ),
        @NamedNativeQuery(
                name = "execDescripcionLabelExt1",
                query = "SELECT [f116_descripcion] FROM [UnoEE].[dbo].[t116_mc_extensiones1] where f116_id = #id"
        ),
        @NamedNativeQuery(
                name = "execDescripcionLabelExt2",
                query = "SELECT [f118_descripcion] FROM [UnoEE].[dbo].[t118_mc_extensiones2] where f118_id = #id"
        ),
        @NamedNativeQuery(
                name = "execTopItemPedido",
                query = "select id_item ,cod_item, descripcion, descripcion_ext1, descripcion_ext2, cantidad , unidad_medida from(SELECT TOP 10 max(id_item) as id_item ,cod_item, descripcion, descripcion_ext1, descripcion_ext2, sum(cantidad) cantidad , unidad_medida FROM t004_item_pedido t004 inner join t003_pedidos t003 on t004.id_pedido = t003.id_pedido inner join  t001_usuario t001 on t003.id_usuario = t001.id_usuario where t001.nit = #nit and t003.estado = 1 GROUP BY  cod_item, descripcion , descripcion_ext1, descripcion_ext2, unidad_medida ORDER BY sum(cantidad) DESC) a",
                resultClass = T004ItemPedido.class
        ),
        @NamedNativeQuery(
                name = "execTipoExtensiones",
                query = "select top 1 f120_id_extension1+f120_id_extension2 from [UnoEE].[dbo].t120_mc_items where f120_id = #idItem"
        )
})


public class T004ItemPedido implements Serializable {

    @Column(name = "unoee_item")
    private Short unoeeItem;

    @Column(name = "unoee_precios")
    private Short unoeePrecios;
    @Column(name = "unoee_portafolio")
    private Short unoeePortafolio;
    @Column(name = "unoee_listas_materiales")
    private Short unoeeListasMateriales;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private BigDecimal cantidad;

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_item")
    private Integer idItem;


    @Size(max = 100)
    @Column(name = "cod_cliente")
    private String codCliente;


    @Column(name = "cod_item")
    private Integer codItem;


    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;


    @Size(max = 20)
    @Column(name = "id_ext1_detalle")
    private String idExt1Detalle;


    @Size(max = 20)
    @Column(name = "id_ext2_detalle")
    private String idExt2Detalle;


    @Size(max = 60)
    @Column(name = "descripcion_ext1")
    private String descripcionExt1;


    @Size(max = 60)
    @Column(name = "descripcion_ext2")
    private String descripcionExt2;


    @Size(max = 100)
    @Column(name = "descripcion_completa_item")
    private String descripcionCompletaItem;


    @Size(max = 10)
    @Column(name = "unidad_medida")
    private String unidadMedida;


    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;


    @Column(name = "precio_unitario_unoee")
    private BigDecimal precioUnitarioUnoee;


    @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
    @ManyToOne
    private T003Pedidos idPedido;


    @Size(max = 250)
    @Column(name = "notas_item")
    private String notasItem;


    @Size(max = 10)
    @Column(name = "id_bodega")
    private String idBodega;


    @Size(max = 10)
    @Column(name = "id_motivo")
    private String idMotivo;


    @Column(name = "fecha_actualizacion_precio_unoee")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacionPrecioUnoee;

    public T004ItemPedido() {
    }

    public T004ItemPedido(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public Integer getCodItem() {
        return codItem;
    }

    public void setCodItem(Integer codItem) {
        this.codItem = codItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdExt1Detalle() {
        return idExt1Detalle;
    }

    public void setIdExt1Detalle(String idExt1Detalle) {
        this.idExt1Detalle = idExt1Detalle;
    }

    public String getIdExt2Detalle() {
        return idExt2Detalle;
    }

    public void setIdExt2Detalle(String idExt2Detalle) {
        this.idExt2Detalle = idExt2Detalle;
    }

    public String getDescripcionExt1() {
        return descripcionExt1;
    }

    public void setDescripcionExt1(String descripcionExt1) {
        this.descripcionExt1 = descripcionExt1;
    }

    public String getDescripcionExt2() {
        return descripcionExt2;
    }

    public void setDescripcionExt2(String descripcionExt2) {
        this.descripcionExt2 = descripcionExt2;
    }

    public String getDescripcionCompletaItem() {
        return descripcionCompletaItem;
    }

    public void setDescripcionCompletaItem(String descripcionCompletaItem) {
        this.descripcionCompletaItem = descripcionCompletaItem;
    }


    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public T003Pedidos getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(T003Pedidos idPedido) {
        this.idPedido = idPedido;
    }

    public String getLabelBOD() {
        switch (getIdBodega()) {
            case "001":
                return "001";

            default:
                return "NO";
        }
    }

    public String getLabelLM() {
        switch (getUnoeeListasMateriales()) {
            case 1:
                return "SI";

            case 0:
                return "NO";

            default:
                return "NO";
        }
    }

    public String getLabelLP() {
        switch (getUnoeePrecios()) {
            case 1:
                return "SI";

            case 0:
                return "NO";

            default:
                return "NO";
        }
    }

    public String getLabelPOR() {
        switch (getUnoeePortafolio()) {
            case 1:
                return "SI";

            case 0:
                return "NO";

            default:
                return "NO";
        }
    }

    public String getLabelItem() {
        switch (getUnoeeItem()) {
            case 1:
                return "SI";

            case 0:
                return "NO";

            default:
                return "NO";
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItem != null ? idItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T004ItemPedido)) {
            return false;
        }
        T004ItemPedido other = (T004ItemPedido) object;
        if ((this.idItem == null && other.idItem != null) || (this.idItem != null && !this.idItem.equals(other.idItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clientesEka.entities.T004ItemPedido[ idItem=" + idItem + " ]";
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Short getUnoeePrecios() {
        return unoeePrecios;
    }

    public void setUnoeePrecios(Short unoeePrecios) {
        this.unoeePrecios = unoeePrecios;
    }

    public Short getUnoeePortafolio() {
        return unoeePortafolio;
    }

    public void setUnoeePortafolio(Short unoeePortafolio) {
        this.unoeePortafolio = unoeePortafolio;
    }

    public Short getUnoeeListasMateriales() {
        return unoeeListasMateriales;
    }

    public void setUnoeeListasMateriales(Short unoeeListasMateriales) {
        this.unoeeListasMateriales = unoeeListasMateriales;
    }

    public Short getUnoeeItem() {
        return unoeeItem;
    }

    public void setUnoeeItem(Short unoeeItem) {
        this.unoeeItem = unoeeItem;
    }

    public String getNotasItem() {
        return notasItem;
    }

    public void setNotasItem(String notasItem) {
        this.notasItem = notasItem;
    }

    public BigDecimal getPrecioUnitarioUnoee() {
        return precioUnitarioUnoee;
    }

    public void setPrecioUnitarioUnoee(BigDecimal precioUnitarioUnoee) {
        this.precioUnitarioUnoee = precioUnitarioUnoee;
    }

    public String getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
    }

    public String getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(String idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Date getFechaActualizacionPrecioUnoee() {
        return fechaActualizacionPrecioUnoee;
    }

    public void setFechaActualizacionPrecioUnoee(Date fechaActualizacionPrecioUnoee) {
        this.fechaActualizacionPrecioUnoee = fechaActualizacionPrecioUnoee;
    }


}
