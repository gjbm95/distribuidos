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
            System.out.println("Bienvendo Cliente");
            System.out.println("");
            System.out.println("Que deseas hacer?");
            System.out.println("Presiona 1 para buscar un recurso");
            System.out.println("Presiona 2 para ver el estado de solicitudes");
            System.out.println("Presiona 3 para salir");
            System.out.println("Ingresa tu opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {

                case 1: {
                    System.out.println("Introduzca el nombre del recurso que desea buscar: ");
                    String recurso= sc.next();
                    int hash = recurso.hashCode();
                    //new ReciboArchivo().descargarArchivo(Sistema.ipserver,Sistema.puertoserver,hash);
                    break;
                }
                case 2: {
                    //LOGICA ESTADO DE RECURSOS
                    break;
                }
                case 3: {
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
