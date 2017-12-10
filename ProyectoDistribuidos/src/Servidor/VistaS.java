/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.Scanner;

/**
 *
 * @author Junior
 */
public class VistaS extends Thread {

    public void run() {

        boolean continuar = true;
        int opcion = 1;
        Scanner sc = new Scanner(System.in);
        while (continuar) {
            System.out.println("Bienvendo Servidor");
            System.out.println("");
            System.out.println("Que deseas hacer?");
            System.out.println("Presiona 1 para ver Recursos ofrecidos");
            System.out.println("Presiona 2 para ver el estado de respuestas");
            System.out.println("Presiona 3 para ver el numero de descargas por video");
            System.out.println("Presiona 4 salir");
            System.out.println("Ingresa tu opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {

                case 1: {
                    // LOGICA RECURSOS OFRECIDOS
                    break;
                }
                case 2: {
                    //LOGICA ESTADO DE RESPUESTAS
                    break;
                }
                case 3: {
                    //LOGICA NUMERO DE DESCARGAS POR VIDEO
                    break;
                }
                case 4: {
                    // LOGICA DE SALIR
                    continuar = false;
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
