package com.idat.rentaflorv1.Modelo;

import android.os.Parcelable;

public class LoginCliente {
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
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
