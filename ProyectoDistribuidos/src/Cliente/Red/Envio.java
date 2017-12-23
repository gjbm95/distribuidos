/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import Dominio.Sistema;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Universidad Catolica Andres Bello
 * Facultad de Ingenieria
 * Escuela de Ingenieria Informatica 
 * Sistemas Distribuidos 
 * ----------------------------------
 * Integrantes: 
 * --------------
 * Garry Bruno 
 * Carlos Valero
 */
public class Envio {
    
    DataOutputStream output;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    byte[] receivedData;
    int in;
    
     /**
      * Este metodo se encarga de enviar datos en forma de objetos serializados 
      * bien sea al servidor fantasma o a otro nodo del anillo
      * @param dato
      * @param tiposerver
      * @return 
      */
     public static Object enviardato(Object dato,String tiposerver){
         try {
               ObjectOutputStream salidaObjeto;      
               //Se colocan los datos del que funge como servidor (Direccion IP y Puerto).
               Socket reves = null;
               if(tiposerver.equals("server")) 
               reves = new Socket (Sistema.ipserver,Sistema.puertoserver);
               if(tiposerver.equals("client")) 
               reves = new Socket (Sistema.ip,Sistema.miPuerto);
               if(tiposerver.equals("center")) 
               reves = new Socket (Sistema.ipcenter,Sistema.puertocenter);
               salidaObjeto = new ObjectOutputStream(reves.getOutputStream());
               //El cliente manda: 
               salidaObjeto.writeObject(dato);
               //El cliente recibe: 
               ObjectInputStream ois = new ObjectInputStream(reves.getInputStream());
               Object respuesta = ois.readObject();
               //Se cierra la conexion. 
               reves.close();
               return respuesta;
          } catch (IOException ex) {
              //Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
          } catch (ClassNotFoundException ex) {
               //Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
          }
         return null;
     }
     
     /**
      * Este metodo se encarga de enviar datos en forma de objetos serializados 
      * a un nodo (fantasma o de anillo) que cuya ip y puerto se conoce perfectamente. 
      * @param dato
      * @param ip
      * @param puerto
      * @return 
      */
     public static Object enviardato(Object dato,String ip, int puerto){
         try {
               ObjectOutputStream salidaObjeto;      
               //Se colocan los datos del servidor central (Direccion IP y Puerto).
               Socket reves = null;
               reves = new Socket (ip,puerto);
               salidaObjeto = new ObjectOutputStream(reves.getOutputStream());
               //El cliente manda: 
               salidaObjeto.writeObject(dato);
               //El cliente recibe: 
               ObjectInputStream ois = new ObjectInputStream(reves.getInputStream());
               Object respuesta = ois.readObject();
               //Se cierra la conexion. 
               reves.close();
               return respuesta;
          } catch (IOException ex) {
              //Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
          } catch (ClassNotFoundException ex) {
               //Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
          }
         return null;
     }
    
}
