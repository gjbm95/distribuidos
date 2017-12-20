/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodistribuidos;

import Central.Controlador;
import Central.DaoCentral;
import Cliente.ControladorC;
import Cliente.Red.Envio;
import Dominio.Sistema;
import Servidor.ControladorS;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Junior
 */
public class ProyectoDistribuidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Sistema sistema = new Sistema(); 
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvendo al proyecto de distribuidos");
        System.out.println("Realizado por:");
        System.out.println("Carlos Valero");
        System.out.println("Garry Bruno");
        System.out.println("");
        System.out.println("Con que rol deseas entrar?");
        System.out.println("Presiona 1 para entrar como Cliente");
        System.out.println("Presiona 2 para entrar como Servidor");
        System.out.println("Presiona 3 para entrar como Servidor Fantasma(SOLO 1)");
        System.out.println("Presiona 4 para Salir");
        System.out.println("Ingresa tu opcion: ");
        int i = sc.nextInt();
        switch(i){
           
            case 1:{
            // LOGICA CLIENTE
            int cantidad = (int)Envio.enviardato("2:","center");
            Sistema.miPuerto=9092+100*cantidad;
            Sistema.miPuertoArchivo=9092+100*cantidad+1;
                System.out.println("Puerto asignado: "+ Sistema.miPuerto);
                System.out.println("Puerto asignado: "+ Sistema.miPuertoArchivo);
            new Cliente.Red.Recepcion().start();
            new Cliente.Red.RecepcionArchivo().start();
            Envio.enviardato("0:"+Sistema.ip+":"+Sistema.miPuerto,"center");
            Sistema.anillo = (ArrayList<String>)Envio.enviardato("4:","center");
            new ControladorC();
            break;
            }
            case 2: {
                //LOGICA SERVIDOR
                Sistema.miPuerto=9091;
                new Servidor.Red.Recepcion().start();
                new ControladorS();
                break;
            }
            case 3:{
            // LOGICA SERVIDOR FANTASMA
            Sistema.miPuerto=9090;
            new Central.Red.Recepcion().start();
                new Controlador();
            break;
            }
            case 4: {
            // SALIR
            
            break;
            }
            default:
                System.out.println("Opcion erronea, saliendo del programa...");
            }
        
    }
    
}
