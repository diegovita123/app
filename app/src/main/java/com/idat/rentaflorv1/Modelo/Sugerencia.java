package com.idat.rentaflorv1.Modelo;

public class Paquete {
    private int id;
    private String descripcion;

    public Paquete() {
    }

    public Paquete(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
