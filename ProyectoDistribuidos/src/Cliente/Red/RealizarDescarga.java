/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Red;

import Dominio.Sistema;

    public class RealizarDescarga extends Thread
    {
        String ip;
        int Puerto;
        int nombre;
        String datoslibros; 
        String numeroS;
        int id; 

    public RealizarDescarga(int id,String ip, int Puerto, int nombre) {
        this.ip = ip;
        this.Puerto = Puerto;
        this.nombre = nombre;
        this.datoslibros = datoslibros;
        this.numeroS = numeroS;
        this.id = id;
    }
        

        public void run()
        { 
           new ReciboArchivo().descargarArchivo(ip,Puerto,nombre);
        }
    }
 

