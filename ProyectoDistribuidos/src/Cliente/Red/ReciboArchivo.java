/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Junior
 */
public class ReciboArchivo {
    DataOutputStream output;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    byte[] receivedData;
    int in;
    String file;
    
    
    
     public void descargarArchivo(String ip, int puerto,int nombre){
       try{          
                    // Se abre una conexion con Servidor Socket
                     Socket cliente = new Socket (ip,puerto);
                     ObjectOutputStream salidaObjeto = new ObjectOutputStream(cliente.getOutputStream());
                     //Solicito el archivo:
                     salidaObjeto.writeObject("3:"+Integer.toString(nombre));
                     bis = new BufferedInputStream(cliente.getInputStream());
                     DataInputStream dis=new DataInputStream(cliente.getInputStream());
                     //Recibimos el nombre del fichero
                     file = dis.readUTF();
                     String [] d = file.split(":");                    
                     d[0] = d[0].substring(d[0].indexOf('\\')+1,d[0].length());
                     //La data recibida, vendran en paquetes de 1024 bytes. 
                     receivedData = new byte[1024];
                     //Para guardar fichero recibido
                     bos = new BufferedOutputStream(new FileOutputStream("Descargas\\"+d[0]));
                     int l =0;
                     //Se manejan los datos acerca del libro recibido
                     while ((in = bis.read(receivedData)) != -1){     
                        bos.write(receivedData,0,in);           
                        l+=in;
                     }
                     //Se cierra la conexion con el servidor de descarga. 
                     bos.close();
                     dis.close();
                     }catch (Exception e ) {
                         System.err.println(e);
                    }
    
    }
}