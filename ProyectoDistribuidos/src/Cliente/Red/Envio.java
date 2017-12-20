/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import Dominio.Sistema;
import Servidor.Red.*;
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
 *
 * @author Junior
 */
public class Envio {
    
    DataOutputStream output;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    byte[] receivedData;
    int in;
    
    
     public static Object enviardato(Object dato){
         try {
               ObjectOutputStream salidaObjeto;      
               //Se colocan los datos del servidor central (Direccion IP y Puerto).
               Socket reves = new Socket (Sistema.ipserver,Sistema.puertoserver);
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
