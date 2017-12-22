/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import Dominio.Recurso;
import Dominio.Sistema;
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
        String [] d =null;
        Recurso re = new Recurso();
         try{          
                    System.out.println("Iniciando proceso de descarga de archivo");
                     // Se abre una conexion con Servidor Socket
                     Socket cliente = new Socket (ip,puerto);
                     ObjectOutputStream salidaObjeto = new ObjectOutputStream(cliente.getOutputStream());
                     //Solicito el archivo:
                     salidaObjeto.writeObject("4:"+nombre);
                     bis = new BufferedInputStream(cliente.getInputStream());
                     DataInputStream dis=new DataInputStream(cliente.getInputStream());
                     //Recibimos el nombre del fichero
                     file = dis.readUTF();
                     d = file.split(":");                    
                     d[0] = d[0].substring(d[0].indexOf('\\')+1,d[0].length());
                     re.setNombre(d[0]);
                     re.setId(d[0].hashCode());
                     re.setEstado("Descargando...");
                     re.setTamanototal(Integer.parseInt(d[1]));
                     Sistema.agregarRecibo(re);
                     //La data recibida, vendran en paquetes de 1024 bytes. 
                     receivedData = new byte[1024];
                     //Para guardar fichero recibido
                     bos = new BufferedOutputStream(new FileOutputStream("Descargas\\"+d[0]));
                     int l =0;
                     boolean partido=true;
                     //Se manejan los datos acerca del libro recibido
                     
                      System.out.println("Tamano total: "+re.getTamanototal());
                     while ((in = bis.read(receivedData)) != -1){     
                        bos.write(receivedData,0,in);  
                        re.setTamano(re.getTamano()+in);
                        if(partido && l>(re.getTamanototal()/2)){
                            System.out.println("El archivo ha alcanzado el 50%");
                            bos.close();
                            partido=false;
                            bos = new BufferedOutputStream(new FileOutputStream("Descargas\\"+d[0],true));
                        }
                            
                        l+=in;
                     }
                     //Se cierra la conexion con el servidor de descarga. 
                     bos.close();
                     dis.close();
                     System.out.println("Finalizando proceso de descarga de archivo");
                     aumentarReporte(nombre);
                     Sistema.estadoRecibo(d[0].hashCode(),"Descarga Completa");
                     }catch (Exception e ) {
                         System.out.println("La descarga del archivo "+d[0]+" ha fallado");
                         re.setEstado("Fallido");
                         System.err.println(e);
                    }
    
    }
     
     public void aumentarReporte(int id){
         for (Recurso r : Sistema.recursos)
        {
            if (r.getId()==id)
             {
               r.setDescargas(r.getDescargas()+1);
             }
        }
     }
     
     public boolean reanudarDescarga(int nombre){
       for (Recurso r : Sistema.recibiendo )
       {
          if ((r.getId()==nombre)&&(r.getEstado().equals("Fallido")))
          {
            return true;
          }
       }
      return false; 
     } 
}
