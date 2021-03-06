package Cliente;

import Cliente.Red.Envio;
import Cliente.Red.RealizarDescarga;
import Dominio.Recurso;
import Dominio.Sistema;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
public class ControladorC {

    public ControladorC() {
         new VistaC().run();
    }
    
    /**
     * Metodo encargado de la buqueda de un archivo determinado y de la ejecucion 
     * de la descarga del archivo en el nodo correspondiente. 
     * @param valor 
     */   
    public static void buscarRecurso(int valor){
      
        String destino = seleccionarNodo(valor); 
        if (!destino.equals("")){
         String data = "3:"+Integer.toString(valor);
         Object obj =  Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[2]));
         if (obj!=null){ 
         String ubicacion =(String)obj;    
         new RealizarDescarga(ubicacion.split(":")[1],Integer.parseInt(ubicacion.split(":")[2])+1,valor).start(); 
         }else{ 
             destino="";
             //Si no lo consigo en el otro, me reviso a mi mismo
             if (destino.equals("")){
                Recurso r = new DaoFinger().obtenerRecurso(valor);
                if (r!=null)
                destino = r.getCodigoprop()+":"+r.getPropietario()+":"+Integer.toString(r.getPuerto()); 
             }
             if (destino.equals("")){
                Recurso r = new DaoC().obtenerRecurso(valor);
                if (r!=null)
                destino = r.getCodigoprop()+":"+r.getPropietario()+":"+Integer.toString(r.getPuerto()); 
             }
             if (destino.equals(""))
              System.out.println("Recurso no encontrado!");
             else 
              new RealizarDescarga(destino.split(":")[1],Integer.parseInt(destino.split(":")[2])+1,valor).start();
         
           }
        }else if (destino.equals(""))
          {
            String parametros = Sistema.tablafinger.get(Sistema.tablafinger.size()-1);
            String data = "7:"+Integer.toString(valor);
            String ubicacionfinal = (String)Envio.enviardato(data,parametros.split(":")[0],Integer.parseInt(parametros.split(":")[2]));
            String data2 = "3:"+Integer.toString(valor);
            Object obj = null; 
             if (ubicacionfinal!=null) 
             obj =  Envio.enviardato(data2,ubicacionfinal.split(":")[0],Integer.parseInt(ubicacionfinal.split(":")[2]));
             
             if (obj!=null){ 
             //int ubicacion = Integer.parseInt(((String)obj).split(":")[1]);
             new RealizarDescarga(((String)obj).split(":")[1],Integer.parseInt(((String)obj).split(":")[2])+1,valor).start(); 
             }else{
                System.out.println("Recurso no encontrado!"); 
             }
          }
            
    }
    
    public static int sacarHash(String nombre){
          String hashoriginal = Integer.toString((int)Math.abs(nombre.hashCode())); 
          return Integer.parseInt(hashoriginal.substring(0,2));
    }
    
    /**
     * Metodo que se encarga de agregar un archivo manualmente a la lista de recursos 
     * de un nodo determinado. 
     * @param nombre 
     */
    public static void agregarArchivo(String nombre){
      File f = new File("canciones"+Sistema.miPuerto);
      File[] ficheros = f.listFiles();
      for (int x=0;x<ficheros.length;x++){
        if(ficheros[x].getName().equals(nombre)){
          DaoC interno = new DaoC(); 
          interno.agregarRecurso(new Recurso(Math.abs(nombre.hashCode()),nombre,
                  ficheros[x].getPath(),Sistema.ip,Sistema.miPuerto,Math.abs(Sistema.ip.hashCode())));
        }
      }
      String destino = seleccionarNodo(Math.abs(nombre.hashCode())); 
      if (!destino.equals("")){
         String data = "2:"+Integer.toString(Math.abs(nombre.hashCode()))+":"+Math.abs(Sistema.ip.hashCode());
         Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[1]));
      } 
    } 
    
    /**
     * Metodo que se encarga de la seleccionar el nodo cuyo hash sea el mas 
     * cercano al hash del archivo solicitado bien sea para descargarlo o bien sea 
     * para informarle a ese nodo cercano que guarde la referencia en su tabla finger. 
     * @param recurso
     * @return 
     */
    public static String seleccionarNodo(int recurso){
        String seleccion =""; 
        for (String direccion : Sistema.tablafinger)
         {    
              if(recurso<Math.abs(Integer.parseInt(direccion.split(":")[1]))){
                 seleccion = direccion;
              }
                           
         }    
        return seleccion;     
    } 
    
    
    /**
     * Devuelve la ip de un nodo, dado un hash 
     * @param hash
     * @return 
     */
    public static String obtenerIp(int hash){
       String direccion; 
         for(String ip : Sistema.tablafinger) 
         {
           if(hash==Integer.parseInt(ip.split(":")[1]))
            {
              return ip.split(":")[0];
            }
         }
       return null; 
    } 
   /**
    * Devuelve el puerto de un nodo dado un hash. 
    * @param hash
    * @return 
    */
    public static int obtenerPuerto(int hash){
         for(String ip : Sistema.tablafinger) 
         {
           if(hash==Integer.parseInt(ip.split(":")[1]))
            {
              return Integer.parseInt(ip.split(":")[2]);
            }
         }
       return 0; 
    }
    /**
     * Obtener numero de nodos del Anillo 
     */
    public static int obtenerNumeroNodos(){
      return Sistema.tablafinger.size();
    }
    /**
     * Obtener Hash de ip actual 
     * 
     */
    public static String ipactual(){
       for (String direcciones : Sistema.tablafinger){
          if ((Integer.parseInt(direcciones.split(":")[2])==Sistema.miPuerto)&&(Integer.parseInt(direcciones.split(":")[1])==((Sistema.ip).hashCode()+Math.pow(2,Sistema.tablafinger.size()-1)))); 
            return Integer.toString((int) Math.abs((Sistema.ip).hashCode()+Math.pow(2,Sistema.tablafinger.size()-1)));
       } 
        return null; 
    }
    /**
     * Verifica si un archivo determinado se encuentra registrado en la memoria 
     * de un nodo. 
     * @param recurso
     * @return 
     */
    public static boolean estaRegistrado(Recurso recurso){
        for (Recurso r :Sistema.recursos)
           {
             if(recurso.getId()==r.getId()) 
              return true; 
           }
      return false; 
    }
    
    private static boolean soyelprimero(){
        
       for(String nodo : Sistema.tablafinger)
       {
          if (Sistema.iphash==Integer.parseInt(nodo.split(":")[1]))
              return true; 
       }
      return false; 
    }
    
    
    /**
     * Este metodo se encarga de cargar en memoria informacion acerca de los recursos 
     * propios que posee un nodo determinado para que posteriormente le envie a los nodos 
     * cuyas ips tienen los hash mas cercanos a ellos y los almanece en las tablas fingers 
     * correspondientes. 
     */
    public static void recargandoRecursos(){
      File f = new File("canciones"+Sistema.miPuerto);
      f.mkdir();
      File[] ficheros = f.listFiles();
      DaoC interno = new DaoC(); 
      interno.crearXML();
      for (int x=0;x<ficheros.length;x++){
          
          String hashoriginal = Integer.toString((int)Math.abs(ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")).hashCode())); 
          int archivohash = Integer.parseInt(hashoriginal.substring(0,2));
          Recurso r = new Recurso(archivohash,ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")),
                  ficheros[x].getPath(),Sistema.ip,Sistema.miPuerto,Sistema.iphash);
          interno.agregarRecurso(r); 
           if (!estaRegistrado(r))
             Sistema.recursos.add(r);
          
           if (soyelprimero()){
             String data = "2:"+archivohash+":"+Sistema.iphash+":"+Sistema.ip+":"+Sistema.miPuerto;
             //String data = "2:"+Integer.toString(Math.abs(ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")).hashCode()))+":"+ipactual();
             Envio.enviardato(data,Sistema.ip,Sistema.miPuerto);
           }else {
                  String destino = seleccionarNodo(archivohash); 
                  if (destino.equals(""))
                  {
                    String parametros = Sistema.tablafinger.get(Sistema.tablafinger.size()-1);
                    String data = "8:"+archivohash+":"+Sistema.iphash+":"+Sistema.ip+":"+Sistema.miPuerto;
                    String ubicacionfinal = (String) Envio.enviardato(data,parametros.split(":")[0],Integer.parseInt(parametros.split(":")[2]));
                    System.out.println("El nodo: " + ubicacionfinal.split(":")[1] + " se quedo con los recursos");
                     
                  }else if (!destino.equals("")){
                     String data = "2:"+archivohash+":"+Sistema.iphash+":"+Sistema.ip+":"+Sistema.miPuerto;
                     Envio.enviardato(data,destino.split(":")[0],Integer.parseInt(destino.split(":")[2]));
                  } 
           }
      }
    }
    
    
    /**
     * Este metodo se encarga de eliminar de la tabla finger de un nodo determinado 
     * informacion acerca de un nodo que ya no se encuentre dentro del anillo. 
     */
    public static void limpiarFinger(){
        int i=0;
        int captura =0;
           for (Recurso r : new DaoFinger().obtenerRecursos()){
               i=0;
               for (String direccion : Sistema.tablafinger){                        
                       if(Integer.parseInt(direccion.split(":")[1])==r.getCodigoprop()) 
                        i++; 
               } 
               if (i==0)
               {
                   new DaoFinger().eliminarRecurso(r.getId());
               }
           }
    }
    /**
     * Borra la tabla de recursos para actualizar
     */
    public static void resetearAlmacen(){
       ArrayList<Recurso> recursos = new DaoFinger().obtenerRecursos();
       for(Recurso r : recursos){
          new DaoFinger().eliminarRecurso(r.getId());
       }
    }
    
    /**
     * Este metodo permite mostrar por pantalla los recursos que tiene un nodo localmente. 
     */
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
    /** 
     * Este metodo permite calcular la cantidad de veces que un recurso fue enviado.
     * @param id
     * @return 
     */
    public static int cantidadD(int id){
        int d=0; 
        for (Recurso r  : Sistema.enviando)
        {
          if (Math.abs(r.getNombre().substring(0,r.getNombre().lastIndexOf(".")).hashCode())==id)
              d++;
        }
      return d; 
    }
    /**
     * Este metodo permite visualizar la cantidad de veces que fue enviado cada recurso. 
     */
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
    /**
     * Este metodo permite visualizar el estado en el que se encuentran los recursos 
     * que se estan descargando. 
     */
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
     /**
      * Este metodo permite visualizar el estado de los recursos que se encuetran 
      * enviandose a otros nodos .
      */
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
     
     /**
      * Este metodo permite visualizar la tabla finger 
      * enviandose a otros nodos .
      */
     public static void verFinger(){
        System.out.println("Tabla Finger");
        System.out.println("----------------------------------------------------------");
          int i =1;
          for(String r : Sistema.tablafinger){
          System.out.println("I: "+i+" | Sucesor: "+r.split(":")[1]);
          i++;
          }
        System.out.println("----------------------------------------------------------");
        System.out.println("Presione una tecla para continuar...");
      Scanner pauser = new Scanner (System.in);
      pauser.nextLine();  
    
    }

}






//                 if (iteracion==0){ 
//                  cercania = (int) Math.abs(recurso-(Integer.parseInt(direccion.split(":")[1])+Math.pow(2,obtenerNumeroNodos()-1)));
//                  iteracion++;
//                 }
//                 if (Math.abs(recurso-(Integer.parseInt(direccion.split(":")[1])+Math.pow(2,obtenerNumeroNodos()-1)))<=cercania)
//                 {
//                    cercania = Math.abs(recurso-Integer.parseInt(direccion.split(":")[1]));
//                    seleccion = direccion.split(":")[0] + ":" +direccion.split(":")[2];
//                 }  