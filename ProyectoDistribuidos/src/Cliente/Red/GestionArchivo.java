/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import Cliente.DaoC;
import Dominio.Recurso;
import Dominio.Sistema;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class GestionArchivo extends Thread {
    
    DataInputStream input;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    ServerSocket server; 
    Socket connection;
    int in;
    byte[] byteArray;
    //Fichero a transferir
    String solicitud= null;
    String ip; 
    String libro;
    
    public GestionArchivo() 
    {
    
    
    }
    public GestionArchivo(ServerSocket server, Socket connection)
    {
        this.connection = connection;
        this.server = server;
    }
    
    
        public void run ()
            {
               try{    
                     int id=0;
                     ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
                     solicitud = (String)ois.readObject();
                     String [] dt = solicitud.split(":");
                     DaoC almacen = new DaoC();
                     Recurso recurso = almacen.obtenerRecurso(dt[1].hashCode());
                     File localFile = new File(recurso.getRuta());
                     bis = new BufferedInputStream(new FileInputStream(localFile));
                     bos = new BufferedOutputStream(connection.getOutputStream());       
                     //Enviamos el nombre del fichero
                     DataOutputStream dos=new DataOutputStream(connection.getOutputStream());
                     dos.writeUTF(localFile.getName()+":"+Integer.toString((int)localFile.length()));
                     //Enviamos el fichero
                     byteArray = new byte[(int)localFile.length()];             
                     //Mando:
                     int k=0;
                     while ((in = bis.read(byteArray)) != -1){       
                      bos.write(byteArray,0,in);
                      k+=in;
                     }
                      // Se cierra la conexion

                      bis.close();
                      bos.close();
                    }catch ( Exception e ) {
                      System.out.println("Error de Envio!");
                      Logger.getLogger(GestionArchivo.class.getName()).log(Level.SEVERE, null, e);
                    }
            }
}
