package Cliente.Red;
import Cliente.ControladorC;
import Cliente.DaoFinger;
import Dominio.Recurso;
import Dominio.Sistema;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
public class Gestion extends Thread {
    ServerSocket reves6;
    Socket recibo;
    int proceso =0; 
    Object mensaje; 
    
   public Gestion (ServerSocket reves6,Socket recibo,int proceso)
    {
        this.reves6 = reves6; 
        this.recibo = recibo;
        this.proceso = proceso;
    }
   
    /**
     * Este hilo se encarga de la recepcion de datos enviados desde el servidor 
     * fantasma y los otros nodos. Gestiona las actualizaciones de tablas finger
     * y el envio de referencias acerca de la ubicacion de otros recursos. 
     */
    @Override
    public void run()
    {
       try {
               //El servidor recibe:
               ObjectInputStream ois = new ObjectInputStream(recibo.getInputStream());
               ObjectOutputStream salidaObjeto = new ObjectOutputStream(recibo.getOutputStream()); 
               //Mensaje que llega:
                mensaje = ois.readObject();
                 DaoFinger almacen;
                 Object respuesta = null; 
               //RESPUESTAS DEL NODO:
               //----------------------------------------------------------------------
               if (mensaje instanceof String){
                    String dato = (String)mensaje; 
                   switch(dato.split(":")[0]){
                       case "2":
                         //Aqui se actualiza la tabla finger cuando se requiera. 
                         almacen = new DaoFinger(); 
                         almacen.agregarRecurso(new Recurso(Integer.parseInt(dato.split(":")[1]),
                         Integer.parseInt(dato.split(":")[2]))); 
                         System.out.println("Actualizando tabla Finger");
                       break;
                       case "3":
                         //Aqui se informa sobre la ubicacion de un recurso en un nodo
                         almacen = new DaoFinger(); 
                         Recurso archivo = almacen.obtenerRecurso(Integer.parseInt(dato.split(":")[1]));
                         if(archivo!=null)
                         respuesta = archivo.getCodigoprop(); 
                       break;
                    }
               } 
               //Se encarga de recibir las actualizaciones de la conformacion del  anillo
               if (mensaje instanceof ArrayList){
                 Sistema.anillo = (ArrayList<String>)mensaje; 
                   System.out.println("Actualizando tabla de direcciones (Antecesor y Sucesor)");
                   System.out.println("Esto es lo que hay");
                   for(String r : Sistema.anillo)
                   {
                       System.out.println(r);
                   }
                   ControladorC.recargandoRecursos();
                   ControladorC.limpiarFinger();
               } 
                 
               //Con este codigo es que responde el servidor:
               salidaObjeto.writeObject(respuesta);
               
               //----------------------------------------------------------------------
               
               recibo.close();//Se finaliza la conexión con el cliente
           } catch (IOException | ClassNotFoundException ex) {
               System.out.println("Hay un error de Conexion! o Ocurrio un error en la solicitud recibida: " + mensaje);
               //Logger.getLogger(GestionSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
}