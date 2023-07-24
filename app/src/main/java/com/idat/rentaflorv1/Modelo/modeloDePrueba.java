package com.idat.rentaflorv1.Modelo;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class modeloDePrueba {
    private int codigo;
    private String nombre;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public modeloDePrueba() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof modeloDePrueba)) return false;
        modeloDePrueba that = (modeloDePrueba) o;
        return getCodigo() == that.getCodigo() &&
                Objects.equals(getNombre(), that.getNombre());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getNombre());
    }
}
