package org.tempuri;

import java.rmi.RemoteException;

import javax.xml.rpc.holders.ShortHolder;

import org.apache.axis.message.MessageElement;

public class Test {

	public void test() throws RemoteException {

		System.out.println("**********************");

		String pvstrDatos = "<?xml version='1.0' encoding='utf-8'?>\n"
				+ "			<Importar>\n"
				+ "			<NombreConexion>UNOEE_2</NombreConexion>\n"
				+ "			<IdCia>1</IdCia>\n"
				+ "			<Usuario>lccabalr</Usuario>\n"
				+ "			<Clave>Juniorking478#</Clave>\n"
				+ "			<Datos>\n"
				+ "<Linea>000000100000001001</Linea>\n"
				+ "<Linea>00000020121000200110023259                                                  045                 00002               1        20190130</Linea>\n"
				+ "<Linea>000000399990001001</Linea></Datos>\n" + "</Importar>";

		// TODO Auto-generated method stub
		WSUNOEESoapProxy proxy = new WSUNOEESoapProxy();
		ShortHolder holder = new ShortHolder((short) 0);
		ImportarXMLResponseImportarXMLResult result = proxy.importarXML(
				pvstrDatos, holder);

		 
		
		MessageElement[] elements = result.get_any();

		for (MessageElement element : elements) {
			
			System.out.println(element.getElementName());
		}

	}

}
