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
                         Integer.parseInt(dato.split(":")[2]),dato.split(":")[3],Integer.parseInt(dato.split(":")[4]))); 
                         System.out.println("Actualizando tabla de Recursos");
                         respuesta = "6:"+Sistema.iphash+":"+Integer.toString(Sistema.miPuerto);
                       break;
                       case "3":
                         //Aqui se informa sobre la ubicacion de un recurso en un nodo
                         System.out.println("Retornando ubicación de recurso");
                         almacen = new DaoFinger(); 
                         Recurso archivo = almacen.obtenerRecurso(Integer.parseInt(dato.split(":")[1]));
                         if(archivo!=null){
                         respuesta = archivo.getCodigoprop()+":"+archivo.getPropietario()+":"+Integer.toString(archivo.getPuerto()); 
                         }
                       break;
                       case"4":
                         ControladorC.resetearAlmacen();
                         ControladorC.recargandoRecursos();      
                       break; 
                       case "8":
                         //Aqui rebota la informacion de la ubicacion del archivo
                           System.out.println(dato);
                         String destino = ControladorC.seleccionarNodo(Integer.parseInt(dato.split(":")[1]));
                         if (!destino.equals("")){
                             System.out.println("Redireccionando recurso en el siguiente nodo");
                             String data = "2:"+dato.split(":")[1]+":"+dato.split(":")[2]+":"+dato.split(":")[3]+":"+dato.split(":")[4];
                             respuesta  = Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[2]));
                         }else if (Sistema.tablafinger.size()==1){
                             System.out.println("Registrando en tabla de recursos");
                             almacen = new DaoFinger(); 
                             almacen.agregarRecurso(new Recurso(Integer.parseInt(dato.split(":")[1]),
                             Integer.parseInt(dato.split(":")[2]),dato.split(":")[3],Integer.parseInt(dato.split(":")[4]))); 
                             respuesta = "6:"+Sistema.iphash+":"+Integer.toString(Sistema.miPuerto);
                         }else if(destino.equals(""))
                          {
                            System.out.println("Redireccionando recurso");
                            String parametros = Sistema.tablafinger.get(Sistema.tablafinger.size()-1);
                            respuesta = (String)Envio.enviardato(dato,parametros.split(":")[0],Integer.parseInt(parametros.split(":")[2]));
                          }
                       break;
                       case "7":
                         //Aqui rebota la informacion de la ubicacion del archivo
                         String destino2 = ControladorC.seleccionarNodo(Integer.parseInt(dato.split(":")[1]));
                         if (!destino2.equals("")){
                             System.out.println("Informando sobre ubicacion de recurso");
                             respuesta = destino2;
                         }else if (Sistema.tablafinger.size()==1){
                             //Aqui se informa sobre la ubicacion de un recurso en un nodo
                             System.out.println("Informando sobre ubicación de recurso");
                             almacen = new DaoFinger(); 
                             Recurso archivo2 = almacen.obtenerRecurso(Integer.parseInt(dato.split(":")[1]));
                             if(archivo2!=null){
                             respuesta = archivo2.getPropietario()+":"+archivo2.getCodigoprop()+":"+Integer.toString(archivo2.getPuerto()); 
                             }
                         }else if (destino2.equals("")){
                             System.out.println("Redireccionando Peticion");
                             String parametros = Sistema.tablafinger.get(Sistema.tablafinger.size()-1);
                             respuesta = (String)Envio.enviardato(dato,parametros.split(":")[0],Integer.parseInt(parametros.split(":")[2]));
                         }
                           
                       break;
                    }
               } 
               //Se encarga de recibir las actualizaciones de la conformacion del  anillo
               if (mensaje instanceof ArrayList){
                 Sistema.tablafinger = (ArrayList<String>)mensaje; 
                   System.out.println("Actualizando tabla finger de direccionamiento");
                   //ControladorC.limpiarFinger();
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