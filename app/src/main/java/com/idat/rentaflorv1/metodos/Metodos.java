package com.idat.rentaflorv1.metodos;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;


public class Metodos {


    public Metodos() {
    }

    public String cadenaAmayuscula(String cadena) {
        String firstLtr = cadena.substring(0, 1);
        String restLtrs = cadena.substring(1, cadena.length());

        firstLtr = firstLtr.toUpperCase();
        cadena = firstLtr + restLtrs;
        return cadena;
    }

    public String formatoPrecio(String precio) {
        //prueva
        NumberFormat pen_promedio = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("S/. ");
        ((DecimalFormat) pen_promedio).setDecimalFormatSymbols(dfs);
        String promedio;
        promedio = pen_promedio.format(Double.parseDouble(precio));
        return promedio;
    }


}





