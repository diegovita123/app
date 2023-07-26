package com.idat.rentaflorv1.Modelo;

public class Paquete {
    private int id;
    private String descripcion;

    public Paquete() {
    }
private int id_cliente;
    private String nombres;
    private String empresa;

    public LoginCliente(int id_cliente, String nombres, String empresa) {
        this.id_cliente = id_cliente;
        this.nombres = nombres;
        this.empresa = empresa;
    }

    public LoginCliente() {
    }

    public int getId_cliente() {
        return id_cliente;
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
