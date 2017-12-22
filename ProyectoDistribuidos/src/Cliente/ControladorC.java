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
import java.util.Scanner;

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
         Object obj =  Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[1]));
         if (obj!=null){ 
         int ubicacion = (int)obj;
         new RealizarDescarga(obtenerIp(ubicacion),obtenerPuerto(ubicacion)+1,valor).start(); 
         }else 
           System.out.println("Recurso no encontrado!");
        } 
    }
    
    public static void agregarArchivo(String nombre){
      File f = new File("canciones"+Sistema.miPuerto);
      File[] ficheros = f.listFiles();
      for (int x=0;x<ficheros.length;x++){
        if(ficheros[x].getName().equals(nombre)){
          DaoC interno = new DaoC(); 
          interno.agregarRecurso(new Recurso(Math.abs(nombre.hashCode()),nombre,
                  ficheros[x].getPath(),Sistema.ip,Math.abs(Sistema.ip.hashCode())));
        }
      }
      String destino = seleccionarNodo(Math.abs(nombre.hashCode())); 
      if (!destino.equals("")){
         String data = "2:"+Integer.toString(Math.abs(nombre.hashCode()))+":"+Math.abs(Sistema.ip.hashCode());
         Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[1]));
      } 
    } 
    
    public static String seleccionarNodo(int recurso){
        int cercania =0;
        String seleccion =""; 
        int iteracion =0;
        for (String direccion : Sistema.anillo)
         {
                 if (iteracion==0){ 
                  cercania = Math.abs(recurso-Integer.parseInt(direccion.split(":")[1]));
                     iteracion++;
                 }
                 if (Math.abs(recurso-Integer.parseInt(direccion.split(":")[1]))<=cercania)
                 {
                    cercania = Math.abs(recurso-Integer.parseInt(direccion.split(":")[1]));
                    seleccion = direccion.split(":")[0] + ":" +direccion.split(":")[2];
                 }
         
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
    
    public static boolean estaRegistrado(Recurso recurso){
        for (Recurso r :Sistema.recursos)
           {
             if(recurso.getId()==r.getId()) 
              return true; 
           }
      return false; 
    }
    
    public static void recargandoRecursos(){
      File f = new File("canciones"+Sistema.miPuerto);
      f.mkdir();
      File[] ficheros = f.listFiles();
      DaoC interno = new DaoC(); 
      interno.crearXML();
      for (int x=0;x<ficheros.length;x++){
          Recurso r = new Recurso(Math.abs(ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")).hashCode()),ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")),
                  ficheros[x].getPath(),Sistema.ip,Math.abs(Sistema.ip.hashCode()));
          interno.agregarRecurso(r); 
           if (!estaRegistrado(r))
             Sistema.recursos.add(r);
          String destino = seleccionarNodo(Math.abs(ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")).hashCode())); 
          if (!destino.equals("")){
             String data = "2:"+Integer.toString(Math.abs(ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")).hashCode()))+":"+Math.abs(Sistema.ip.hashCode());
             Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[1]));
          } 
      }
    }
    
    public static void limpiarFinger(){
        int i=0;
        int captura =0;
           for (Recurso r : new DaoFinger().obtenerRecursos()){
               i=0;
               for (String direccion : Sistema.anillo){                        
                       if(Integer.parseInt(direccion.split(":")[1])==r.getCodigoprop()) 
                        i++; 
               } 
               if (i==0)
               {
                   new DaoFinger().eliminarRecurso(r.getId());
               }
           }

    }
    
    public static void verRecursos(){
        System.out.println("Recursos Ofrecidos");
        System.out.println("----------------------------------------------------------");
          for(Recurso r : new DaoC().obtenerRecursos()){
              System.out.println("Nombre: "+r.getNombre());
          }
        System.out.println("----------------------------------------------------------");
        System.out.println("Presione una tecla para continuar...");
      Scanner pauser = new Scanner (System.in);
      pauser.nextLine();  
    
    }
    
    public static int cantidadD(int id){
        int d=0; 
        for (Recurso r  : Sistema.enviando)
        {
          if (Math.abs(r.getNombre().substring(0,r.getNombre().lastIndexOf(".")).hashCode())==id)
              d++;
        }
      return d; 
    }
    
    public static void verNDescargas(){
        System.out.println("Cantidad de Descargas por Recursos");
        System.out.println("----------------------------------------------------------");
          for(Recurso r : Sistema.recursos){
              System.out.println("");
              System.out.println("Nombre: "+r.getNombre()+" | Nº Descargas: "+cantidadD(r.getId()));
          }
        System.out.println("----------------------------------------------------------");
        System.out.println("Presione una tecla para continuar...");
      Scanner pauser = new Scanner (System.in);
      pauser.nextLine();  
    
    }
    
    public static void verEstadoRecurso(){
        System.out.println("Estado de recursos");
        System.out.println("----------------------------------------------------------");
          for(Recurso r : Sistema.recibiendo){
              System.out.println("Nombre: "+r.getNombre()+" | Estado: "+r.getEstado() + " | Tamaño total:"+ r.getTamanototal() + " | Tamaño recibido: "+r.getTamano());
          }
        System.out.println("----------------------------------------------------------");
        System.out.println("Presione una tecla para continuar...");
      Scanner pauser = new Scanner (System.in);
      pauser.nextLine();  
    
    }
    
     public static void verEstadoRespuestas(){
        System.out.println("Estado de Respuestas");
        System.out.println("----------------------------------------------------------");
          for(Recurso r : Sistema.enviando){
          System.out.println("Nombre: "+r.getNombre()+" | Estado: "+r.getEstado() + " | Tamaño total:"+ r.getTamanototal() );
          }
        System.out.println("----------------------------------------------------------");
        System.out.println("Presione una tecla para continuar...");
      Scanner pauser = new Scanner (System.in);
      pauser.nextLine();  
    
    }

}
