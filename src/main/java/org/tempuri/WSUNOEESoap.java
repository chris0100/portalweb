/**
 * WSUNOEESoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface WSUNOEESoap extends java.rmi.Remote {
    public boolean crearConexionXML(java.lang.String pvstrxmlConexion) throws java.rmi.RemoteException;
    public org.tempuri.EjecutarConsultaXMLResponseEjecutarConsultaXMLResult ejecutarConsultaXML(java.lang.String pvstrxmlParametros) throws java.rmi.RemoteException;
    public java.lang.String leerEsquemaParametros(java.lang.String pvstrxmlParametros) throws java.rmi.RemoteException;
    public org.tempuri.ImportarXMLResponseImportarXMLResult importarXML(java.lang.String pvstrDatos, javax.xml.rpc.holders.ShortHolder printTipoError) throws java.rmi.RemoteException;
    public void inicializarVariablesImportacion() throws java.rmi.RemoteException;
    public short siesaWEBContabilizar(java.lang.String pvstrParametros) throws java.rmi.RemoteException;
}
