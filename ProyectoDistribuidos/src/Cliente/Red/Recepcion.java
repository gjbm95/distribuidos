package Cliente.Red;
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
public class Recepcion extends Thread{ 
    
    public Recepcion()
    {
        
    }
    
    /**
     * Inicializa el hilo que se encarga de la recepcion de datos en forma 
     * de objetos serializados.
     */
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
