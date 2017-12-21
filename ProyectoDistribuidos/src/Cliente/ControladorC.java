/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Cliente.Red.Envio;
import Cliente.Red.RealizarDescarga;
import Dominio.Recurso;
import Dominio.Sistema;
import java.io.File;

/**
 *
 * @author Junior
 */
public class ControladorC {

    public ControladorC() {
         new VistaC().run();
    }
    
      
    public static void buscarRecurso(int valor){
      
        String destino = seleccionarNodo(valor); 
        if (!destino.equals("")){
         String data = "3:"+Integer.toString(valor);
         int ubicacion = (int) Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[1]));
         new RealizarDescarga(obtenerIp(ubicacion),obtenerPuerto(ubicacion)+1,valor).start(); 
        } 
    }
    
    public static void agregarArchivo(String nombre){
      File f = new File("canciones");
      File[] ficheros = f.listFiles();
      for (int x=0;x<ficheros.length;x++){
        if(ficheros[x].getName().equals(nombre)){
          DaoC interno = new DaoC(); 
          interno.agregarRecurso(new Recurso(nombre.hashCode(),nombre,
                  ficheros[x].getPath(),Sistema.ip,Sistema.ip.hashCode()));
        }
      }
      String destino = seleccionarNodo(nombre.hashCode()); 
      if (!destino.equals("")){
         String data = "2:"+Integer.toString(nombre.hashCode())+":"+Math.abs(Sistema.ip.hashCode());
         Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[1]));
      } 
    } 
    
    public static String seleccionarNodo(int recurso){
        int cercania =0;
        String seleccion =""; 
        int iteracion =0;
        for (String direccion : Sistema.anillo)
         {
             if (iteracion==0) 
             cercania = Math.abs(recurso-Integer.parseInt(direccion.split(":")[1]));
             if (Sistema.miPuerto!=Integer.parseInt(direccion.split(":")[2])) 
             if (Math.abs(recurso-Integer.parseInt(direccion.split(":")[1]))<=cercania)
                 {
                    cercania = Math.abs(recurso-Integer.parseInt(direccion.split(":")[1]));
                    seleccion = direccion.split(":")[0] + ":" +direccion.split(":")[2];
                 }
             iteracion++;
         }
        return seleccion;     
    } 
    
    public static String obtenerIp(int hash){
       String direccion; 
         for(String ip : Sistema.anillo) 
         {
           if(hash==Integer.parseInt(ip.split(":")[1]))
            {
              return ip.split(":")[0];
            }
         }
       return null; 
    } 

    public static int obtenerPuerto(int hash){
       int puerto; 
         for(String ip : Sistema.anillo) 
         {
           if(hash==Integer.parseInt(ip.split(":")[1]))
            {
              return Integer.parseInt(ip.split(":")[2]);
            }
         }
       return 0; 
    }

}
