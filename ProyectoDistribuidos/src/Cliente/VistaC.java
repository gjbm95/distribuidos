/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Cliente.Red.Envio;
import Cliente.Red.ReciboArchivo;
import Dominio.Sistema;
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
public class VistaC extends Thread {
    /**
     * Este metodo es un hilo que se encarga de ejecutar el menu principal de 
     * la aplicacion y permite al usuario ejecutar cada una de las funcionalidades
     * que ofrece. 
     */ 
    public void run() {
        boolean continuar = true;
        String opcion ="";
        Scanner sc = new Scanner(System.in);
        while (continuar) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Bienvendo Cliente");
            System.out.println("----------------------------------------------------------");
            System.out.println("MENU - Que deseas hacer?");
            System.out.println("----------------------------------------------------------");
            System.out.println("Presiona 1 para buscar un recurso");
            System.out.println("Presiona 2 para ver el estado de solicitudes");
            System.out.println("Presiona 3 para agregar archivos");
            System.out.println("Presiona 4 para ver Recursos ofrecidos");
            System.out.println("Presiona 5 para ver el estado de respuestas");
            System.out.println("Presiona 6 para ver el numero de descargas por archivo");
            System.out.println("Presiona 7 para salir");
            System.out.println("----------------------------------------------------------");
            System.out.println("Ingresa tu opcion: ");
            opcion = sc.nextLine();

            switch (opcion) {

                case "1": {
                    //LOGICA PARA LA BUSQUEDA DE ARCHIVOS
                    System.out.println("Introduzca el nombre del recurso que desea buscar: ");
                     sc = new Scanner(System.in);
                    String recurso = sc.nextLine();
                    int hash = Math.abs(recurso.hashCode());
                    ControladorC.buscarRecurso(hash);
                    break;
                }
                case "2": {
                    //LOGICA ESTADO DE RECURSOS
                    ControladorC.verEstadoRecurso();
                    break;
                }
                case "3": {
                    //LOGICA AGREGAR ARCHIVOS
                    System.out.println("Ingrese el nombre del archivo:");
                    sc = new Scanner(System.in);
                    String archivo = sc.nextLine();
                    ControladorC.agregarArchivo(archivo);
                    break;
                }
                case "4": {
                    //RECURSOS OFRECIDOS
                    ControladorC.verRecursos();
                    break;
                }
                case "5": {
                    //ESTADO DE RESPUESTAS
                    ControladorC.verEstadoRespuestas();
                    break;
                }
                case "6": {
                    //VER LAS CANTIDAD DE DESCARGAS DE LOS RECURSOS OFRECIDOS
                    ControladorC.verNDescargas();
                    break;
                }
                case "7": {
                    // LOGICA SALIR
                    Envio.enviardato("1:"+Sistema.ip+":"+Sistema.miPuerto,"center");
                    continuar = false;
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Opcion erronea");
                }

            }
        
       }
    }
    
    
}
