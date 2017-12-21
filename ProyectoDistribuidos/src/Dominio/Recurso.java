/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

/**
 *
 * @author Junior
 */
public class Recurso {
    private int id; 
    private String nombre;
    private String ruta; 
    private String propietario;
    private int codigoprop;
    private int descargas=0;
    private String estado;
    
    public Recurso(int id, String nombre, String ruta,String propietario,int codigoprop) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.propietario = propietario; 
        this.codigoprop = codigoprop;
    }

    public Recurso(int id, int codigoprop) {
        this.id = id;
        this.codigoprop = codigoprop;
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

}
