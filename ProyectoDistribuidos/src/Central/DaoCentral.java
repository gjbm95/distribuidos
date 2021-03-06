package Central;
import Dominio.Sistema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DaoCentral {
    
    String filelocation = "fantasma.xml";
    Element root;
    
    /*
      Agregando elemento a archivo XML
    */
    public void agregarNodo(String direccion,String puerto,String hash){
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
            Element nodo = new Element("nodo");
            nodo.setAttribute(new Attribute("ip",direccion));
            nodo.setAttribute(new Attribute("id",hash));
            nodo.setAttribute(new Attribute("port",puerto));
            root.addContent(nodo);
            document.removeContent();
            document.addContent(root.detach());
            
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
    /**
     * Este metodo permite eliminar los datos de un nodo que se sale del anillo.
     * @param direccion 
     */
    public void eliminarNodo(String hash){
       
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
        
            Element aux = new Element("nodo");
            List nodos = root.getChildren("nodo");
            while (aux != null) {
                aux = obtenerIpNodo(nodos,hash);
                if (aux != null) {
                    nodos.remove(aux);
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
     Devuelve el string de la ip
    */
    public String obtenerIp(String direccion){
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
                boolean resultado = false;
                    Element aux = new Element("nodo");
                    List nodos = root.getChildren("nodo");
                    while (aux != null) {
                        aux = obtenerIpNodo(nodos,direccion);
                         return aux.getAttributeValue("ip");
                    }
                fis.close();
            } catch (JDOMException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
         } 

        return null; 
    }
    
    /**
     * Este metodo recoge los datos de los nodos registrados para luego difundirlo
     * @return 
     */
    public ArrayList<String> obtenerIps(){
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
                    Element aux = new Element("nodo");
                    List nodos = root.getChildren("nodo"); 
                    ArrayList<String> resultados = new ArrayList<>(); 
                      Iterator i = nodos.iterator();
                         while (i.hasNext()) {
                              Element e = (Element) i.next();
                              resultados.add(e.getAttributeValue("ip")+":"+e.getAttributeValue("id")
                                      +":"+e.getAttributeValue("port"));
                            
                          }    
                fis.close();
                return ordenarLista(resultados); 
            } catch (JDOMException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
         } 
        return null; 
    }
    /**
     * Este metodo se encarga de ordenar de menor a mayor por hash las IPs
     * @param lista
     * @return 
     */
    private ArrayList<String> ordenarLista(ArrayList<String> lista){
        ArrayList<Integer> numerico = new ArrayList<Integer>();
        ArrayList<String> resultado = new ArrayList<String>();
        
        for (String elemento : lista)
        {
           numerico.add(Integer.parseInt(elemento.split(":")[1]));
        }
        Collections.sort(numerico);
        for(Integer elemento : numerico){
          for(String organizando : lista){
            if (elemento == Integer.parseInt(organizando.split(":")[1])) 
              resultado.add(organizando);
          }
        } 
        return resultado;
    }
    
    public static String obtenerSucesor (String actual,ArrayList<String> anillo){
     int index =0;
     String sucesor="";
        for(String nodo : anillo){
         if(nodo.equals(actual))
          {
             if((index+1)<(anillo.size()-1))
               sucesor = anillo.get(index+1);
             else 
               sucesor = anillo.get(0);
          }
             index++;
        } 
        return sucesor;
    }
    
    public static ArrayList<String> construirTabla(String actual,ArrayList<String> anillo){
     ArrayList<String> respuesta = new ArrayList<String>();
     int hash = Integer.parseInt(actual.split(":")[1]);
     int potencia =0;
     boolean nada; 
        while(potencia<2){
            nada = true;
            int numero = (int) (hash + Math.pow(2,potencia));  
            int index =0; 
            for (String nodo : anillo)     
            {
               if((index-1)!=-1){
                   if ((numero>Integer.parseInt(anillo.get(index-1).split(":")[1]))&&(numero<=(Integer.parseInt(nodo.split(":")[1]))))
                   {    
                     respuesta.add(nodo);
                     nada = false;
                     break; 
                   }
               }
                   index++;
            }
            if (nada){
            respuesta.add(obtenerSucesor (actual,anillo));
            break;                                                                              
            }
            
             potencia++;
        }
        
        
        return respuesta;
  
    }
    
   /**
    * Este metodo cuenta la cantidad de nodos registrados con el fin de que se puedan 
    * calcular los puertos que son dinamicos para cada nodo y la formula (k+2^(n-1)). 
    * @return 
    */
    public int numeroNodos(){
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
                    Element aux = new Element("nodo");
                    List nodos = root.getChildren("nodo"); 
                    int resultados =0;  
                      Iterator i = nodos.iterator();
                         while (i.hasNext()) {
                              Element e = (Element) i.next();
                              resultados++;
                          }    
                fis.close();
                return resultados; 
            } catch (JDOMException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DaoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
         } 
        return 0; 
    }

    /*
     Retorna la ip del nodo en HASH almacenada
    */
    public Element obtenerIpNodo(List raiz,String hash){
        
         Iterator i = raiz.iterator();
          while (i.hasNext()) {
            //System.out.println("i tiene algo");
            Element e = (Element) i.next();
            if (hash.equals(e.getAttributeValue("id"))) {
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
    
    /*
      Metodo que saca el HASH a un estring determinado 
    */
    public int sacarHASH(String input){
      return input.hashCode();
    }
}
