/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

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
 *
 * @author Junior
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
   
    @Override
    public void run()
    {
       try {
               //El servidor recibe:
               ObjectInputStream ois = new ObjectInputStream(recibo.getInputStream());
               ObjectOutputStream salidaObjeto = new ObjectOutputStream(recibo.getOutputStream()); 
               //Mensaje que llega:
                mensaje = ois.readObject();
               //RESPUESTAS DEL NODO:
               //----------------------------------------------------------------------
               if (mensaje instanceof String){
                    String dato = (String)mensaje; 
                   switch(dato.split(":")[0]){
                       case "2":
                         DaoFinger almacen = new DaoFinger(); 
                         almacen.agregarRecurso(new Recurso(Integer.parseInt(dato.split(":")[1]),
                                 Integer.parseInt(dato.split(":")[2])));
                           System.out.println("Actualizando tabla Finger");
                       break;
                    }
               } 
               
               if (mensaje instanceof ArrayList){
                 Sistema.anillo = (ArrayList<String>)mensaje; 
                   System.out.println("Actualizando tabla de direcciones");
               } 
                 
               //Con este codigo es que responde el servidor:
               //salidaObjeto.writeObject("");
               
               //----------------------------------------------------------------------
               
               recibo.close();//Se finaliza la conexión con el cliente
           } catch (IOException | ClassNotFoundException ex) {
               System.out.println("Hay un error de Conexion! o Ocurrio un error en la solicitud recibida: " + mensaje);
               //Logger.getLogger(GestionSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
}