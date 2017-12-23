package Cliente.Red;

import Dominio.Sistema;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
public class RecepcionArchivo extends Thread {
    
    public RecepcionArchivo()
    {

    }
    
    /**
     * Inicializa el hilo encargado del envio de archivos 
     */
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
