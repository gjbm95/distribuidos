package Dominio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class Sistema {
    
    //------------------------------------------------------------
    //Aspecto de Red: 
    //-----------------------------------------------------------
    public static String ip = obtenerIP(); 
    public static int iphash = 0;
    public static String ipserver ="localhost"; 
    public static String ipcenter =obtenerIPCentral(); 
    public static int miPuerto = 9092;
    public static int puertocenter = 9090; 
    public static int puertoserver = 9091;
    public static int miPuertoArchivo = 9095;
    public static int puertocenterArchivo = 9093; 
    public static int puertoserverArchivo = 9094;
    public static ArrayList<String> anillo;
    public static String antecesor;
    public static String sucesor;
    //------------------------------------------------------------
    //Aspectos de Reportes y Estadisticas: 
    //------------------------------------------------------------
    public static ArrayList<Recurso> recursos = new ArrayList<Recurso>();
    public static ArrayList<Recurso> recibiendo = new ArrayList<Recurso>();
    public static ArrayList<Recurso> enviando = new ArrayList<Recurso>();
    //------------------------------------------------------------
    
    private static String obtenerIP(){
        FileReader f=null;
        String direccion="";
        try {
            String cadena;
            f = new FileReader("red.txt");
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                direccion =cadena;
            }   b.close();
            return direccion;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return direccion;
    }
    
    private static String obtenerIPCentral(){
        FileReader f=null;
        String direccion="";
        try {
            String cadena;
            f = new FileReader("redcentral.txt");
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                direccion =cadena;
            }   b.close();
            return direccion;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return direccion;
    }
    
    public static void agregarRecibo(Recurso r){
      recibiendo.add(r);
    }
    
    public static void eliminarRecibo(int id){
        for (Recurso r : recibiendo)
        {
           if (r.getId()==id)
           {
              recibiendo.remove(r);
              break;
           }
        }
    }
    
    public static void estadoRecibo(int id,String estado){
        for (Recurso r : recibiendo)
        {
           if (r.getId()==id)
           {
              r.setEstado(estado);
           }
        }
    }
    
    public static void agregarEnvio(Recurso r){
      enviando.add(r);
    }
    
    public static void eliminarEnvio(int id){
       for (Recurso r : enviando)
        {
           if (r.getId()==id)
           {
              enviando.remove(r);
              break;
           }
        }
    }
    
    public static void estadoEnvio(int id,String estado){
        for (Recurso r : enviando)
        {
           if (r.getId()==id)
           {
              r.setEstado(estado);
           }
        }
    }
}
