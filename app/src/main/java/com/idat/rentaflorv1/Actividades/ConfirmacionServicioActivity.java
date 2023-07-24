package com.idat.rentaflorv1.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.idat.rentaflorv1.Fragment.ServicioFinalFragment;
import com.idat.rentaflorv1.Fragment.facturasFragment;
import com.idat.rentaflorv1.Fragment.serviciosFragment;
import com.idat.rentaflorv1.Modelo.Solicitudes;
import com.idat.rentaflorv1.R;
import com.idat.rentaflorv1.metodos.Metodos;

public class ConfirmacionServicioActivity extends AppCompatActivity {

    TextView nombreempresa, ruc, solicitante, fecha, tiposervicio,
            lugarinicio, lugardestino, fechainicio, fechafin, km,
            conbudtible, peajes, viaticos, costo, total, igv;
    Button btn_ok;
    Metodos metodos;
    int idcotizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_servicio);
        nombreempresa = findViewById(R.id.s_nombreEmpresa);
        ruc = findViewById(R.id.s_ruc);
        solicitante = findViewById(R.id.s_solicitante);
        fecha = findViewById(R.id.s_fecha);
        tiposervicio = findViewById(R.id.s_tiposervicio);
        lugarinicio = findViewById(R.id.s_lugarinicio);
        lugardestino = findViewById(R.id.s_lugardestino);
        fechainicio = findViewById(R.id.s_fechainicio);
        fechafin = findViewById(R.id.s_fechafin);
        km = findViewById(R.id.s_km);
        conbudtible = findViewById(R.id.s_conbustible);
        peajes = findViewById(R.id.s_peaje);
        viaticos = findViewById(R.id.s_viaticos);
        costo = findViewById(R.id.s_costo);
        total = findViewById(R.id.s_total);
        igv = findViewById(R.id.s_igv);
        metodos = new Metodos();
        Solicitudes solicitudes;
        if (getIntent().hasExtra("position")) {
            int position = getIntent().getIntExtra("position", 0);
            if(getIntent().hasExtra("id")){
                idcotizacion = getIntent().getIntExtra("id", 0);
                solicitudes = serviciosFragment.listaSolicitud.get(position);
            }else{
                solicitudes = facturasFragment.listaSolicitud.get(position);
            }
            nombreempresa.setText(solicitudes.getEmpresanombre());
            ruc.setText(solicitudes.getRuc());
            solicitante.setText(solicitudes.getClientenombre());
            fecha.setText(solicitudes.getFecha());
            tiposervicio.setText(solicitudes.getDescripciontiposervicio());
            lugarinicio.setText(solicitudes.getLigarinicio());
            lugardestino.setText(solicitudes.getLugarfin());
            fechainicio.setText(solicitudes.getFechainicio());
            fechafin.setText(solicitudes.getFechafin());
            km.setText(metodos.formatoPrecio("0"));
            conbudtible.setText(metodos.formatoPrecio("0"));
            peajes.setText(metodos.formatoPrecio("0"));
            viaticos.setText(metodos.formatoPrecio("0"));
            Double costoo, igvv, totall;
            costoo = Double.parseDouble(solicitudes.getCosto());
            igvv = (costoo * 18) / 100;
            totall = costoo + igvv;
            costo.setText(metodos.formatoPrecio(String.valueOf(costoo)));
            total.setText(metodos.formatoPrecio(String.valueOf(totall)));
            igv.setText(metodos.formatoPrecio(String.valueOf(igvv)));
        }
    }
}