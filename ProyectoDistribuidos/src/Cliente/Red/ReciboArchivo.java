/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import Dominio.Recurso;
import Dominio.Sistema;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
public class ReciboArchivo {

    DataOutputStream output;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    byte[] receivedData;
    int in;
    String file;

    public void descargarArchivo(String ip, int puerto, int nombre) {
        String[] d = null;
        Recurso re = new Recurso();
        DataInputStream dis = null;
        try {
            System.out.println("Iniciando proceso de descarga de archivo");
            // Se abre una conexion con Servidor Socket
            Socket cliente = new Socket(ip, puerto);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(cliente.getOutputStream());
            //Solicito el archivo:
            salidaObjeto.writeObject("4:" + nombre);
            bis = new BufferedInputStream(cliente.getInputStream());
            dis = new DataInputStream(cliente.getInputStream());
            //Recibimos el nombre del fichero
            file = dis.readUTF();
            d = file.split(":");
            d[0] = d[0].substring(d[0].indexOf('\\') + 1, d[0].length());
            re.setNombre(d[0]);
            re.setId(d[0].hashCode());
            re.setEstado("Descargando...");
            re.setTamanototal(Integer.parseInt(d[1]));
            Sistema.agregarRecibo(re);
            //La data recibida, vendran en paquetes de 1024 bytes. 
            receivedData = new byte[1024];
            //Para guardar fichero recibido
            File descargado = new File("Descargas\\" + d[0]);
            boolean recarga;
            if (descargado.exists() && descargado.length()< re.getTamanototal() && descargado.length()> re.getTamanototal()/2) {
                System.out.println("Su descarga habia sido interrumpida.. descargando nuevamente desde el 50% ");
                recarga=true;
                BufferedInputStream arreglar = new BufferedInputStream(new FileInputStream(descargado)); 
                byte [] arreglo = new byte[re.getTamanototal()/2]; 
                in=arreglar.read(arreglo); 
                recarga = false; 
                arreglar.close(); 
                bos = new BufferedOutputStream(new FileOutputStream("Descargas\\" + d[0])); 
                bos.write(arreglo, 0, in); 
                bos.close(); 
                salidaObjeto.writeObject(1);
                bos = new BufferedOutputStream(new FileOutputStream("Descargas\\" + d[0],true));
            }
            else{
                recarga=false;
                salidaObjeto.writeObject(0);
                bos = new BufferedOutputStream(new FileOutputStream("Descargas\\" + d[0]));
            }
                
            

            int l = 0;
            boolean partido = true;
            //Se manejan los datos acerca del libro recibido

            System.out.println("Tamano total: " + re.getTamanototal());
            while ((in = bis.read(receivedData)) != -1) {
                   // System.out.println("entro");
                    bos.write(receivedData, 0, in);
                    re.setTamano(re.getTamano() + in);
                if (partido && l > (re.getTamanototal() / 2)) {
                    partido=false;
                    System.out.println("El archivo ha alcanzado el 50%");
                    bos.close();
                    bos = new BufferedOutputStream(new FileOutputStream("Descargas\\" + d[0], true));
                }

                l += in;
            }
            //Se cierra la conexion con el servidor de descarga. 
            bos.close();
            dis.close();
            System.out.println("Finalizando proceso de descarga de archivo");
            aumentarReporte(nombre);
            Sistema.estadoRecibo(d[0].hashCode(), "Descarga Completa");
        } catch (Exception e) {
//            try {
//                System.out.println("La descarga del archivo " + d[0] + " ha fallado");
//                re.setEstado("Fallido");
//                System.out.println("Usted o el servidor ha perdido la conexion");
//                dis.close();
//                bos.close();
//                //System.err.println(e);
//            } catch (IOException ex) {
//                Logger.getLogger(ReciboArchivo.class.getName()).log(Level.SEVERE, null, ex);
//            }
            Logger.getLogger(ReciboArchivo.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void aumentarReporte(int id) {
        for (Recurso r : Sistema.recursos) {
            if (r.getId() == id) {
                r.setDescargas(r.getDescargas() + 1);
            }
        }
    }

    public boolean reanudarDescarga(int nombre) {
        for (Recurso r : Sistema.recibiendo) {
            if ((r.getId() == nombre) && (r.getEstado().equals("Fallido"))) {
                return true;
            }
        }
        return false;
    }
}
