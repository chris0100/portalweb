/**
 * SiesaWEBContabilizarResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class SiesaWEBContabilizarResponse  implements java.io.Serializable {
    private short siesaWEBContabilizarResult;

    public SiesaWEBContabilizarResponse() {
    }

    public SiesaWEBContabilizarResponse(
           short siesaWEBContabilizarResult) {
           this.siesaWEBContabilizarResult = siesaWEBContabilizarResult;
    }


    /**
     * Gets the siesaWEBContabilizarResult value for this SiesaWEBContabilizarResponse.
     * 
     * @return siesaWEBContabilizarResult
     */
    public short getSiesaWEBContabilizarResult() {
        return siesaWEBContabilizarResult;
    }


    /**
     * Sets the siesaWEBContabilizarResult value for this SiesaWEBContabilizarResponse.
     * 
     * @param siesaWEBContabilizarResult
     */
    public void setSiesaWEBContabilizarResult(short siesaWEBContabilizarResult) {
        this.siesaWEBContabilizarResult = siesaWEBContabilizarResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SiesaWEBContabilizarResponse)) return false;
        SiesaWEBContabilizarResponse other = (SiesaWEBContabilizarResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.siesaWEBContabilizarResult == other.getSiesaWEBContabilizarResult();
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
        _hashCode += getSiesaWEBContabilizarResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SiesaWEBContabilizarResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">SiesaWEBContabilizarResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siesaWEBContabilizarResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SiesaWEBContabilizarResult"));
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
