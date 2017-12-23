package Cliente.Red;
import Dominio.Sistema;
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
public class RealizarDescarga extends Thread
{
        String ip;
        int Puerto;
        int nombre;

    public RealizarDescarga(String ip, int Puerto, int nombre) {
        this.ip = ip;
        this.Puerto = Puerto;
        this.nombre = nombre;
    }
        
        /**
         * Inicializa el hilo encargado de la descarga del archivo.
         */
        public void run()
        { 
           new ReciboArchivo().descargarArchivo(ip,Puerto,nombre);
        }
}
 

