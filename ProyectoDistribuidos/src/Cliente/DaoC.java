package Cliente;

import Central.DaoCentral;
import static Cliente.ControladorC.seleccionarNodo;
import Cliente.Red.Envio;
import Dominio.Recurso;
import Dominio.Sistema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
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
public class DaoC {
    String filelocation = "cliente"+Sistema.miPuerto+".xml";
    Element root;
    
    /**
     * Agregando elemento a archivo XML
    **/
    public void agregarRecurso(Recurso archivo){
        File xmlFile = new File(filelocation);
        Document document = null;
        
        if(xmlFile.exists()) {
            try {
                // try to load document from xml file if it exist
                // create a file input stream
                FileInputStream fis = new FileInputStream(xmlFile);
                // create a sax builder to parse the document
                SAXBuilder sb = new SAXBuilder();
                // parse the xml content provided by the file input stream and create a Document object
                document = sb.build(fis);
                // get the root element of the document
                root = document.getRootElement();
                fis.close();
            } catch (JDOMException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
         } 
            Element recurso = new Element("recurso");
            recurso.setAttribute("id", Integer.toString(archivo.getId()));
            recurso.setAttribute("nombre",archivo.getNombre());
            recurso.setAttribute("hash",archivo.getNombre());
            recurso.setAttribute("ruta",archivo.getRuta());
            recurso.setAttribute("ip",archivo.getPropietario());
            recurso.setAttribute("puerto",Integer.toString(archivo.getPuerto()));
            recurso.setAttribute("iphash",Integer.toString(archivo.getCodigoprop()));
            root.addContent(recurso);
            document.removeContent();
            document.addContent(root);
            
                try {
                    FileWriter writer = new FileWriter(xmlFile);
                    XMLOutputter outputter = new XMLOutputter();
                    outputter.setFormat(Format.getPrettyFormat());
                    outputter.output(document, writer);
                    //outputter.output(document, System.out);
                    writer.close(); // close writer
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
    /*
      Elimina un recurso del cliente 
    */
    public void eliminarRecurso(int id){
       
        File xmlFile = new File(filelocation);
        Document document = null;
        
        if(xmlFile.exists()) {
            try {
                // try to load document from xml file if it exist
                // create a file input stream
                FileInputStream fis = new FileInputStream(xmlFile);
                // create a sax builder to parse the document
                SAXBuilder sb = new SAXBuilder();
                // parse the xml content provided by the file input stream and create a Document object
                document = sb.build(fis);
                // get the root element of the document
                root = document.getRootElement();
                fis.close();
            } catch (JDOMException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
         } 
        
            Element aux = new Element("recurso");
            List recursos = root.getChildren("recurso");
            while (aux != null) {
                aux = obtenerIdRecurso(recursos,id);
                if (aux != null) {
                    recursos.remove(aux);
                    updateDocument();   
                }
            }
            document.removeContent();
            document.addContent(root);
            
                try {
                    FileWriter writer = new FileWriter(xmlFile);
                    XMLOutputter outputter = new XMLOutputter();
                    outputter.setFormat(Format.getPrettyFormat());
                    outputter.output(document, writer);
                    //outputter.output(document, System.out);
                    writer.close(); // close writer
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
    
    /*
     Devuelve un recurso del cliente
    */
    public Recurso obtenerRecurso(int id){
       File xmlFile = new File(filelocation);
        Document document = null;
        if(xmlFile.exists()) {
            try {
                // try to load document from xml file if it exist
                // create a file input stream
                FileInputStream fis = new FileInputStream(xmlFile);
                // create a sax builder to parse the document
                SAXBuilder sb = new SAXBuilder();
                // parse the xml content provided by the file input stream and create a Document object
                document = sb.build(fis);
                // get the root element of the document
                root = document.getRootElement();
                Recurso resultado = null;
                    Element aux = new Element("recurso");
                    List nodos = root.getChildren("recurso");
                    aux = obtenerIdRecurso(nodos,id);
                    if(aux != null) {
                        resultado =  new Recurso(Integer.parseInt(aux.getAttributeValue("id"))
                                 ,aux.getAttributeValue("nombre"),aux.getAttributeValue("ruta")
                                 ,aux.getAttributeValue("ip"),
                                 Integer.parseInt(aux.getAttributeValue("puerto")),
                                 Integer.parseInt(aux.getAttributeValue("iphash")));
                    }
                fis.close();
                return resultado; 
            } catch (JDOMException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
         } 

        return null; 
    }
   /**
    * Permite localizar un archivo por su nombre en hash
    * @param nombre
    * @return 
    */ 
   public static String buscarArchivo(int nombre){
      File f = new File("canciones"+Sistema.miPuerto);
      File[] ficheros = f.listFiles();
      for (int x=0;x<ficheros.length;x++){
          if(ControladorC.sacarHash(ficheros[x].getName().substring(0, ficheros[x].getName().lastIndexOf(".")))==nombre){
           return ficheros[x].getName();
        }
      }
       return null; 
    } 
    
    
    /*
     Devuelve un recurso del cliente
    */
    public ArrayList<Recurso> obtenerRecursos(){
       File xmlFile = new File(filelocation);
        Document document = null;
        if(xmlFile.exists()) {
            try {
                // try to load document from xml file if it exist
                // create a file input stream
                FileInputStream fis = new FileInputStream(xmlFile);
                // create a sax builder to parse the document
                SAXBuilder sb = new SAXBuilder();
                // parse the xml content provided by the file input stream and create a Document object
                document = sb.build(fis);
                // get the root element of the document
                root = document.getRootElement();
                    Element aux = new Element("recurso");
                    List nodos = root.getChildren("recurso"); 
                    ArrayList<Recurso> resultados = new ArrayList<Recurso>(); 
                      Iterator i = nodos.iterator();
                         while (i.hasNext()) {
                              Element e = (Element) i.next();
                              resultados.add(new Recurso(Integer.parseInt(e.getAttributeValue("id"))
                                 ,e.getAttributeValue("nombre"),e.getAttributeValue("ruta")
                                 ,e.getAttributeValue("ip"),
                                 Integer.parseInt(e.getAttributeValue("puerto")),
                                 Integer.parseInt(e.getAttributeValue("iphash"))));
                          }    
                fis.close();
                return resultados; 
            } catch (JDOMException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
         } 
        return null; 
    }
    
    
    /*
     Retorna la ip del nodo en HASH almacenada
    */
    public Element obtenerIdRecurso(List raiz,int id){
        
         Iterator i = raiz.iterator();
          while (i.hasNext()) {
            //System.out.println("i tiene algo");
            Element e = (Element) i.next();
            if (id==Integer.parseInt(e.getAttributeValue("id"))) {
                return e;
            }
        }
      return null; 
    }
    
    /*
     Creando archivo XML 
    */
    public void crearXML(){
         try {
		Element anillo = new Element("anillo");
		Document doc = new Document(anillo);
		doc.setRootElement(anillo);
		// new XMLOutputter().output(doc, System.out);
		XMLOutputter xmlOutput = new XMLOutputter();
		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter(filelocation));
	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  }
    
    }
    
    /*
      Funcion que actualiza el documento XML 
    */
    private boolean updateDocument() {
        try {
            XMLOutputter out = new XMLOutputter(org.jdom.output.Format.getPrettyFormat());
            FileOutputStream file = new FileOutputStream(filelocation);
            out.output(root, file);
            file.flush();
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }
    /**
     * Elimina el archivo XML del disco
     */
    public void eliminarArchivo(){
      File fichero = new File(filelocation);
      fichero.delete();
    }
    
}