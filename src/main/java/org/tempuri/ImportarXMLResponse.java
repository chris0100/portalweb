/**
 * ImportarXMLResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class ImportarXMLResponse  implements java.io.Serializable {
    private org.tempuri.ImportarXMLResponseImportarXMLResult importarXMLResult;

    private short printTipoError;

    public ImportarXMLResponse() {
    }

    public ImportarXMLResponse(
           org.tempuri.ImportarXMLResponseImportarXMLResult importarXMLResult,
           short printTipoError) {
           this.importarXMLResult = importarXMLResult;
           this.printTipoError = printTipoError;
    }


    /**
     * Gets the importarXMLResult value for this ImportarXMLResponse.
     * 
     * @return importarXMLResult
     */
    public org.tempuri.ImportarXMLResponseImportarXMLResult getImportarXMLResult() {
        return importarXMLResult;
    }


    /**
     * Sets the importarXMLResult value for this ImportarXMLResponse.
     * 
     * @param importarXMLResult
     */
    public void setImportarXMLResult(org.tempuri.ImportarXMLResponseImportarXMLResult importarXMLResult) {
        this.importarXMLResult = importarXMLResult;
    }


    /**
     * Gets the printTipoError value for this ImportarXMLResponse.
     * 
     * @return printTipoError
     */
    public short getPrintTipoError() {
        return printTipoError;
    }


    /**
     * Sets the printTipoError value for this ImportarXMLResponse.
     * 
     * @param printTipoError
     */
    public void setPrintTipoError(short printTipoError) {
        this.printTipoError = printTipoError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImportarXMLResponse)) return false;
        ImportarXMLResponse other = (ImportarXMLResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.importarXMLResult==null && other.getImportarXMLResult()==null) || 
             (this.importarXMLResult!=null &&
              this.importarXMLResult.equals(other.getImportarXMLResult()))) &&
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
        if (getImportarXMLResult() != null) {
            _hashCode += getImportarXMLResult().hashCode();
        }
        _hashCode += getPrintTipoError();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImportarXMLResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ImportarXMLResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importarXMLResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ImportarXMLResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>ImportarXMLResponse>ImportarXMLResult"));
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
