/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import Dominio.Sistema;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Junior
 */
public class RecepcionArchivo extends Thread {
    
    public RecepcionArchivo()
    {

    }
    
    public void run () 
    {
       //Solicitudes concurrentes:       
           try {
                ServerSocket reves6 = new ServerSocket(Sistema.miPuertoArchivo);
                while (true)
                  {   
                      Socket recibo = reves6.accept();              
                      new EnvioArchivo(reves6,recibo).start();
                   }
               } catch (IOException ex) {
                   //Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
               }  

        
             
    }
}
