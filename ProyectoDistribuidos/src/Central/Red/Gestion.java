/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Central.Red;

import Dominio.Sistema;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author pepo
 */
public class Gestion extends Thread{
    
    private Sistema sistema; 
    private ServerSocket reves6;
    private Socket recibo;
    private int proceso =0; 
    private String mensaje; 
    
    public Gestion (Sistema sistema,ServerSocket reves6,Socket recibo,int proceso)
    {
        this.reves6 = reves6; 
        this.recibo = recibo;
        this.sistema = sistema; 
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
                mensaje = (String)ois.readObject();
               //RESPUESTAS DEL SERVIDOR:
               //----------------------------------------------------------------------
               //Condiciones de Garry:
//                 String [] operacion = mensaje.split(":");  
//                 if (operacion[0].equals("1"))
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).registrarusuario(operacion[1],operacion[2],operacion[3]));
//                 if (operacion[0].equals("2"))
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).iniciarsesion(operacion[1],operacion[2]));
//                 if (operacion[0].equals("5"))
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).solicitadescargaA(operacion[1])); 
//                 if (operacion[0].equals("8"))
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).registrarTransaccion(operacion[1],operacion[2],operacion[3],operacion[4]));
//                 if (operacion[0].equals("9")) 
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).registroServidor(operacion[1],Integer.parseInt(operacion[2])));  
//                 if (operacion[0].equals("12"))  
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).registrarlibros(operacion[2],Integer.parseInt(operacion[1])));
//                 if (operacion[0].equals("13"))
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).solicitaLibro(operacion[1]));  
//                 if (operacion[0].equals("14"))  
//                   salidaObjeto.writeObject(new Controlador_Garry(sistema).registrarlibros2(operacion[2],Integer.parseInt(operacion[1])));
//                 //----------------------------------------------------------------------
//               //Condiciones de Aquiles:
//                 if (operacion[0].equals("3")) 
//                   salidaObjeto.writeObject(new Controlador_Aquiles(sistema).todoslosAutores(operacion[1]));
//                 if (operacion[0].equals("4"))                  
//                   salidaObjeto.writeObject(new Controlador_Aquiles(sistema).todoslosGeneros(operacion[1]));
//                 if (operacion[0].equals("10"))  
//                   salidaObjeto.writeObject(new Controlador_Aquiles(sistema).LibrosDescargados()); 
//                 if (operacion[0].equals("11"))  
//                   salidaObjeto.writeObject(new Controlador_Aquiles(sistema).ClienteFieles(operacion[1]));
                 
               //Con este codigo es que responde el servidor:
               // salidaObjeto.writeObject("");
               
               //----------------------------------------------------------------------
               
               recibo.close();//Se finaliza la conexión con el cliente
           } catch (IOException | ClassNotFoundException ex) {
               System.out.println("Hay un error de Conexion! o Ocurrio un error en la solicitud recibida: " + mensaje);
               //Logger.getLogger(GestionSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
}
