/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Central;
import Dominio.Recurso;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Junior
 */
public class DaoCentral {
    
    String filelocation = "fantasma.xml";
    Element root;
    
    /*
      Agregando elemento a archivo XML
    */
    public void agregarNodo(String direccion){
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
            root.addContent(nodo);
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
    
    public void eliminarNodo(String direccion){
       
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
                aux = obtenerIpNodo(nodos,direccion);
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
                    Element aux = new Element("recursos");
                    List nodos = root.getChildren("recursos"); 
                    ArrayList<String> resultados = new ArrayList<>(); 
                      Iterator i = nodos.iterator();
                         while (i.hasNext()) {
                              Element e = (Element) i.next();
                              resultados.add(e.getAttributeValue("id"));
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
    public Element obtenerIpNodo(List raiz,String direccion){
        
         Iterator i = raiz.iterator();
          while (i.hasNext()) {
            //System.out.println("i tiene algo");
            Element e = (Element) i.next();
            if (direccion.equals(e.getAttributeValue("ip"))) {
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
