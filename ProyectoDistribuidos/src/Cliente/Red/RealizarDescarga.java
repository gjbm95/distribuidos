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

    public RealizarDescarga(String ip, int Puerto, int nombre) {
        this.ip = ip;
        this.Puerto = Puerto;
        this.nombre = nombre;
    }
        

        public void run()
        { 
           new ReciboArchivo().descargarArchivo(ip,Puerto,nombre);
        }
}
 

