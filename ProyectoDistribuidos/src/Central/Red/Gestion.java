package Central.Red;

import Central.DaoCentral;
import Dominio.Sistema;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

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
public class Gestion extends Thread{

    private ServerSocket reves6;
    private Socket recibo;
    private int proceso =0; 
    private String mensaje; 
    private DaoCentral dao;
    private int conexiones=0; 
    
    public Gestion (ServerSocket reves6,Socket recibo,int proceso)
    {
        this.reves6 = reves6; 
        this.recibo = recibo;
        this.proceso = proceso;
        this.dao= new DaoCentral();
    }
    /**
     * Este hilo permite la recepcion de cada una de las solicitudes de los nodos 
     * que conforman al anillo. Este permite registrar a un nodo en su tabla o 
     * eliminarlos. Asi como difundir su lista de nodos registrados. 
     */
    @Override
    public void run()
    {
       try {
               //El servidor recibe:
               ObjectInputStream ois = new ObjectInputStream(recibo.getInputStream());
               ObjectOutputStream salidaObjeto = new ObjectOutputStream(recibo.getOutputStream()); 
               //Mensaje que llega:
                mensaje = (String)ois.readObject();
                DaoCentral almacen = new DaoCentral(); 
               //Preparo respuesta:
                Object respuesta = null;
               //RESPUESTAS DEL SERVIDOR:
               //----------------------------------------------------------------------
               switch(mensaje.split(":")[0]){
                    case "0":
                    almacen.agregarNodo(mensaje.split(":")[1],mensaje.split(":")[2]);
                    distribuirUsuarios(almacen.obtenerIps());
                    break;
                    case"1":
                    almacen.eliminarNodo(mensaje.split(":")[1]); 
                    distribuirUsuarios(almacen.obtenerIps());
                    break;
                    case"2":
                    conexiones = conexiones + almacen.numeroNodos()+1;  
                    respuesta = conexiones; 
                    System.out.println("Cantidad de nodos es : " + (int)respuesta);
                    break;
                    case"3":
                    respuesta = almacen.obtenerIps(); 
                    break;
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
    /**
     * Este metodo permite la difucion de datos acerca de los nodos que se 
     * encuentran dentro del anillo. 
     * @param almacen 
     */
    private void distribuirUsuarios(ArrayList<String> almacen){
        for (String direccion : almacen){ 
          Envio.enviardato(DaoCentral.construirTabla(direccion,almacen),direccion.split(":")[0],
                  Integer.parseInt(direccion.split(":")[2]));
        } 
    
    }

}
