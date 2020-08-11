/**
 * ImportarXML.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class ImportarXML  implements java.io.Serializable {
    private java.lang.String pvstrDatos;

    private short printTipoError;

    public ImportarXML() {
    }

    public ImportarXML(
           java.lang.String pvstrDatos,
           short printTipoError) {
           this.pvstrDatos = pvstrDatos;
           this.printTipoError = printTipoError;
    }


    /**
     * Gets the pvstrDatos value for this ImportarXML.
     * 
     * @return pvstrDatos
     */
    public java.lang.String getPvstrDatos() {
        return pvstrDatos;
    }


    /**
     * Sets the pvstrDatos value for this ImportarXML.
     * 
     * @param pvstrDatos
     */
    public void setPvstrDatos(java.lang.String pvstrDatos) {
        this.pvstrDatos = pvstrDatos;
    }


    /**
     * Gets the printTipoError value for this ImportarXML.
     * 
     * @return printTipoError
     */
    public short getPrintTipoError() {
        return printTipoError;
    }


    /**
     * Sets the printTipoError value for this ImportarXML.
     * 
     * @param printTipoError
     */
    public void setPrintTipoError(short printTipoError) {
        this.printTipoError = printTipoError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImportarXML)) return false;
        ImportarXML other = (ImportarXML) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pvstrDatos==null && other.getPvstrDatos()==null) || 
             (this.pvstrDatos!=null &&
              this.pvstrDatos.equals(other.getPvstrDatos()))) &&
            this.printTipoError == other.getPrintTipoError();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getPvstrDatos() != null) {
            _hashCode += getPvstrDatos().hashCode();
        }
        _hashCode += getPrintTipoError();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImportarXML.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ImportarXML"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pvstrDatos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "pvstrDatos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printTipoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "printTipoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
