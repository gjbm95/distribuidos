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
 *
 * @author Junior
 */
public class VistaC extends Thread {

    public void run() {
        boolean continuar = true;
        int opcion = 1;
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
            opcion = sc.nextInt();

            switch (opcion) {

                case 1: {
                    System.out.println("Introduzca el nombre del recurso que desea buscar: ");
                     sc = new Scanner(System.in);
                    String recurso = sc.nextLine();
                    int hash = recurso.hashCode();
                    ControladorC.buscarRecurso(hash);
                    break;
                }
                case 2: {
                    //LOGICA ESTADO DE RECURSOS
                    ControladorC.verEstadoRecurso();
                    break;
                }
                case 3: {
                    //LOGICA AGREGAR ARCHIVOS
                    System.out.println("Ingrese el nombre del archivo:");
                    sc = new Scanner(System.in);
                    String archivo = sc.nextLine();
                    ControladorC.agregarArchivo(archivo);
                    break;
                }
                case 4: {
                    //RECURSOS OFRECIDOS
                    ControladorC.verRecursos();
                    break;
                }
                case 5: {
                    //ESTADO DE RESPUESTAS
                    ControladorC.verEstadoRespuestas();
                    break;
                }
                case 6: {
                    //VER LAS CANTIDAD DE DESCARGAS DE LOS RECURSOS OFRECIDOS
                    ControladorC.verNDescargas();
                    break;
                }
                case 7: {
                    // LOGICA SALIR
                    Envio.enviardato("1:"+Sistema.ip+":"+Sistema.miPuerto,"center");
                    continuar = false;
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Opcion erronea, saliendo del programa...");
                    continuar = false;
                }

            }
        
       }
    }
    
    
}
