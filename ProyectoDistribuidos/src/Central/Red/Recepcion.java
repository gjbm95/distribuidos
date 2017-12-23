package Central.Red;

import Dominio.Sistema;
import java.io.IOException;
import java.net.ServerSocket;
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
public class Recepcion extends Thread {
    
    public Recepcion(){
    }
    /**
     * Este metodo permite la ejecucion del hilo que se encarga de recibiir las 
     * solicitudes de los nodos que se encuentran en el anillo. 
     */
    public void run () 
    {
      try {     
        ServerSocket reves = new ServerSocket(Sistema.puertocenter);
       //Contador de procesos; 
        int i=1;
       //Solicitudes concurrentes:
       while (true)
       {        
                  Socket recibo = reves.accept();
                  new Central.Red.Gestion(reves,recibo,i).start();
                  i++;
       }  
        } catch (IOException ex) {
                   Logger.getLogger(Cliente.Red.Recepcion.class.getName()).log(Level.SEVERE, null, ex);
               }       
    }
}
