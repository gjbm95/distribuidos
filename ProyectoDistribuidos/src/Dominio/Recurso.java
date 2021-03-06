package Dominio;

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
public class Recurso {
    private int id; 
    private String nombre;
    private String ruta; 
    private String propietario;
    private int codigoprop;
    private int descargas=0;
    private String estado;
    private int tamano;
    private int tamanototal; 
    private int puerto;
    
    public Recurso(int id, String nombre, String ruta,String propietario,int puerto,int codigoprop) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.propietario = propietario;
        this.puerto = puerto; 
        this.codigoprop = codigoprop;
    }

    public Recurso(int id, int codigoprop) {
        this.id = id;
        this.codigoprop = codigoprop;
    }
    
    public Recurso(int id, int codigopropm,String ip,int puerto) {
        this.id = id;
        this.codigoprop = codigopropm;
        this.propietario = ip; 
        this.puerto = puerto; 
    }
    
    public Recurso(){
    
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public int getCodigoprop() {
        return codigoprop;
    }

    public void setCodigoprop(int codigoprop) {
        this.codigoprop = codigoprop;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public int getTamanototal() {
        return tamanototal;
    }

    public void setTamanototal(int tamanototal) {
        this.tamanototal = tamanototal;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    
    
    

}
