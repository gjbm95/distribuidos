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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class Recepcion extends Thread{ 
    
    public Recepcion()
    {
        
    }
    
    public void run () 
    {
      try {     
        ServerSocket reves = new ServerSocket(Sistema.miPuerto);
       //Contador de procesos; 
        int i=1;
       //Solicitudes concurrentes:
       while (true)
       {        
                  Socket recibo = reves.accept();
                  new Gestion(reves,recibo,i).start();
                  i++;
       }  
        } catch (IOException ex) {
                   Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
               }       
    }
}
