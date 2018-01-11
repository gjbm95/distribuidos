package proyectodistribuidos;

import Central.DaoCentral;
import Cliente.ControladorC;
import Cliente.DaoFinger;
import Cliente.Red.Envio;
import Dominio.Sistema;
import java.util.ArrayList;
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
public class ProyectoDistribuidos {

    /**
     * Este es el programa principal donde inicia todo. Aqui el usuario selecciona
     * en que modalidad quiere ejecutar el programa. (Como fantasma o nodo del anillo). 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Sistema sistema = new Sistema();   
        int estado =1;
        while(estado!=0){ 
        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------------------------------------");
        System.out.println("Bienvendo al Proyecto de Distribuidos");
        System.out.println("----------------------------------------------------------");
        System.out.println("Realizado por:");
        System.out.println("- Carlos Valero");
        System.out.println("- Garry Bruno");
        System.out.println("----------------------------------------------------------");
        System.out.println("Con que rol deseas entrar?");
        System.out.println("Presiona 1 para entrar como Nodo");
        System.out.println("Presiona 2 para entrar como Servidor Fantasma(SOLO 1)");
        System.out.println("Presiona 3 para Salir");
        System.out.println("----------------------------------------------------------");
        System.out.println("Ingresa tu opcion: ");
        String i = sc.nextLine();
        switch(i){
            case "1":{
            // LOGICA CLIENTE

            int cantidad = (int)Envio.enviardato("2:","center");
            Sistema.miPuerto=9092+100*cantidad;
            Sistema.miPuertoArchivo=9092+100*cantidad+1;
                System.out.println("Puerto asignado: "+ Sistema.miPuerto);
                System.out.println("Puerto asignado: "+ Sistema.miPuertoArchivo);
            new Cliente.Red.Recepcion().start();
            new Cliente.Red.RecepcionArchivo().start();
            new DaoFinger().eliminarArchivo();
            new DaoFinger().crearXML();
            System.out.println("Indique su Hash de IP");
            i = sc.nextLine();
            Sistema.iphash = Integer.parseInt(i);
            Envio.enviardato("0:"+Sistema.ip+":"+Sistema.miPuerto+":"+Sistema.iphash,"center");
            //Sistema.anillo = (ArrayList<String>)Envio.enviardato("4:","center");
            new ControladorC();
            break;
            }
            case "2":{
            // LOGICA SERVIDOR FANTASMA
            new DaoCentral().crearXML();
            Sistema.miPuerto=9090;
            new Central.Red.Recepcion().start();
            System.out.println("Servidor Fantasma Iniciado...");
            System.out.println("Esperando peticiones...");
            break;
            }
            case "3": {
            // SALIR
              estado =0;
              System.exit(0);
            break;
            }
            default:
                System.out.println("Opcion erronea");
               
            }
        }
    }
    
}
