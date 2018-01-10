package Cliente.Red;
import Cliente.ControladorC;
import Cliente.DaoC;
import static Cliente.DaoC.buscarArchivo;
import Dominio.Recurso;
import Dominio.Sistema;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
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
public class EnvioArchivo extends Thread {
    
    DataInputStream input;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    ServerSocket server; 
    Socket connection;
    int in;
    byte[] byteArray;
     byte[] mitad;
    //Fichero a transferir
    String solicitud= null;
    String ip; 
    String libro;
    
    public EnvioArchivo() 
    {
    
    
    }
    public EnvioArchivo(ServerSocket server, Socket connection)
    {
        this.connection = connection;
        this.server = server;
    }
    
    /**
     * Este hilo se encarga del envio de archivos al nodo que lo solicite
     */
    public void run (){
                String [] dt=null;
                Recurso re=null;
               try{
                     int id=0;
                     ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
                     solicitud = (String)ois.readObject();
                     dt = solicitud.split(":");
                      System.out.println("Iniciando proceso de envio del archivo: "+ buscarArchivo(Integer.parseInt(dt[1])));
                     File localFile = new File("canciones"+Sistema.miPuerto+"/"+buscarArchivo(Integer.parseInt(dt[1])));
                     //System.out.println("Recibido es: " + solicitud);
                     //System.out.println("El archivo es: " + buscarArchivo(Integer.parseInt(dt[1])));
                     re = new Recurso();
                     re.setNombre(buscarArchivo(Integer.parseInt(dt[1])));
                     re.setId(ControladorC.sacarHash(re.getNombre()));
                     re.setEstado("Enviando...");
                     Sistema.agregarEnvio(re);
                     bis = new BufferedInputStream(new FileInputStream(localFile));
                     bos = new BufferedOutputStream(connection.getOutputStream());       
                     //Enviamos el nombre del fichero
                     DataOutputStream dos=new DataOutputStream(connection.getOutputStream());
                     dos.writeUTF(localFile.getName()+":"+Integer.toString((int)localFile.length()));
                        int tamano = (int)localFile.length();
                     if((int)ois.readObject()==0){
                     //Enviamos el fichero
                     re.setTamanototal(tamano);
                    byteArray = new byte[(int)localFile.length()];
                     //Mando:
                     int k=0;
                      while ((in = bis.read(byteArray)) != -1){        
                      bos.write(byteArray,0,in); 
                      k+=in; 
                     }
                     }
                     else{
                     re.setTamanototal(tamano);
                     byteArray = new byte[tamano/2];
                     bis.skip(new Long(tamano/2));
                     while ((in = bis.read(byteArray,0,byteArray.length)) != -1){        
                      bos.write(byteArray,0,in); 
                        }
                     }
                      // Se cierra la conexion
                      bis.close();
                      bos.close();
                       System.out.println("Envio de Archivo finalizado!");
                       Sistema.estadoEnvio(re.getId(),"Envio Completo");
                    }catch ( Exception e ) {
//                            try {
//                                System.out.println("Error de Envio del archivo "+dt[1]+ ". Usted o el cliente ha perdido la conexion");
//                                re.setEstado("Fallido");
//                                bis.close();
//                               } catch (IOException ex) {
//                                Logger.getLogger(EnvioArchivo.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                    Logger.getLogger(EnvioArchivo.class.getName()).log(Level.SEVERE, null, e);
                    }
    }
        
    /**
     * Este nodo se encarga de reanudar la descarga en caso de ser necesario. 
     * @param nombre
     * @return 
     */
    public boolean reanudarDescarga(int nombre){
       for (Recurso r : Sistema.recibiendo )
       {
          if ((r.getId()==nombre)&&(r.getEstado().equals("Fallido")))
          {
            return true;
          }
       }
      return false; 
    } 
    
}
