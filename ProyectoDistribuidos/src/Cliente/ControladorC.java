/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Cliente.Red.Envio;
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
      Envio.enviardato("3:"+valor,"server");
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

}
