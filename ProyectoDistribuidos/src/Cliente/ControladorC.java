/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Cliente.Red.Envio;

/**
 *
 * @author Junior
 */
public class ControladorC {

    public ControladorC() {
         new VistaC().run();
    }
    
      
    public static void buscarRecurso(int valor){
      Envio.enviardato("3:"+valor,"server");
    }

}
