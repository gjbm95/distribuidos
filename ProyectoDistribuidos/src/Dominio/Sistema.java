/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class Sistema {
    public static String ip = obtenerIP(); 
    public static String ipserver ="localhost"; 
    public static String ipcenter =obtenerIPCentral(); 
    public static int miPuerto = 9092;
    public static int puertocenter = 9090; 
    public static int puertoserver = 9091;
    public static int miPuertoArchivo = 9095;
    public static int puertocenterArchivo = 9093; 
    public static int puertoserverArchivo = 9094;
    public static ArrayList<String> anillo;

    
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
}
