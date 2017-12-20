/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Central.Red;

import Dominio.Sistema;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pepo
 */
public class Recepcion extends Thread {
    private Sistema sistema;
    
    public Recepcion(Sistema sistema){
    this.sistema=sistema;
    }
    
    public void run () 
    {
      try {     
        ServerSocket reves = new ServerSocket(sistema.getMiPuerto());
       //Contador de procesos; 
        int i=1;
       //Solicitudes concurrentes:
       while (true)
       {        
                  Socket recibo = reves.accept();
                  new Central.Red.Gestion(sistema,reves,recibo,i).start();
                  i++;
       }  
        } catch (IOException ex) {
                   Logger.getLogger(Cliente.Red.Recepcion.class.getName()).log(Level.SEVERE, null, ex);
               }       
    }
}
