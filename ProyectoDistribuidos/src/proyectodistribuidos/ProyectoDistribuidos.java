/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodistribuidos;

import Central.Controlador;
import Central.DaoCentral;
import Cliente.ControladorC;
import Dominio.Sistema;
import Servidor.ControladorS;
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
            new Cliente.Red.Recepcion(sistema).start();
            new ControladorC();
            break;
            }
            case 2: {
                //LOGICA SERVIDOR
                new Servidor.Red.Recepcion(sistema).start();
                new ControladorS();
                break;
            }
            case 3:{
            // LOGICA SERVIDOR FANTASMA
            sistema.setMiPuerto(9090);
            new Central.Red.Recepcion(sistema).start();
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
