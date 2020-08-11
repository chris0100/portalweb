/**
 * LeerEsquemaParametrosResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class LeerEsquemaParametrosResponse  implements java.io.Serializable {
    private java.lang.String leerEsquemaParametrosResult;

    public LeerEsquemaParametrosResponse() {
    }

    public LeerEsquemaParametrosResponse(
           java.lang.String leerEsquemaParametrosResult) {
           this.leerEsquemaParametrosResult = leerEsquemaParametrosResult;
    }


    /**
     * Gets the leerEsquemaParametrosResult value for this LeerEsquemaParametrosResponse.
     * 
     * @return leerEsquemaParametrosResult
     */
    public java.lang.String getLeerEsquemaParametrosResult() {
        return leerEsquemaParametrosResult;
    }


    /**
     * Sets the leerEsquemaParametrosResult value for this LeerEsquemaParametrosResponse.
     * 
     * @param leerEsquemaParametrosResult
     */
    public void setLeerEsquemaParametrosResult(java.lang.String leerEsquemaParametrosResult) {
        this.leerEsquemaParametrosResult = leerEsquemaParametrosResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LeerEsquemaParametrosResponse)) return false;
        LeerEsquemaParametrosResponse other = (LeerEsquemaParametrosResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.leerEsquemaParametrosResult==null && other.getLeerEsquemaParametrosResult()==null) || 
             (this.leerEsquemaParametrosResult!=null &&
              this.leerEsquemaParametrosResult.equals(other.getLeerEsquemaParametrosResult())));
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
        if (getLeerEsquemaParametrosResult() != null) {
            _hashCode += getLeerEsquemaParametrosResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LeerEsquemaParametrosResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">LeerEsquemaParametrosResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leerEsquemaParametrosResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LeerEsquemaParametrosResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
