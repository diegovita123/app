package com.idat.rentaflorv1.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.idat.rentaflorv1.Fragment.serviciosFragment;
import com.idat.rentaflorv1.Fragment.solicitudesFragment;
import com.idat.rentaflorv1.Modelo.Solicitudes;
import com.idat.rentaflorv1.R;

public class ServicioDetalleActivity extends AppCompatActivity {

    TextView tiposervicio, dia, lugarinicio, lugardestino, nombre, celular, cargo, referencia;
    Button btn_ok;

    int idcotizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_detalle);
        tiposervicio = findViewById(R.id.d_tiposervicio);
        dia = findViewById(R.id.d_dia);
        lugarinicio = findViewById(R.id.d_lugarinicio);
        lugardestino = findViewById(R.id.d_lugardestino);
        nombre = findViewById(R.id.d_nombre);
        celular = findViewById(R.id.d_celular);
        cargo = findViewById(R.id.d_cargo);
        referencia = findViewById(R.id.d_referencia);
        btn_ok = findViewById(R.id.d_btnagregar);


        if (getIntent().hasExtra("position")) {
            int position = getIntent().getIntExtra("position", 0);
            idcotizacion = getIntent().getIntExtra("id", 0);

            Solicitudes solicitudes = serviciosFragment.listaSolicitud.get(position);
            tiposervicio.setText(solicitudes.getDescripciontiposervicio());
            dia.setText(solicitudes.getFechainicio());
            lugardestino.setText(solicitudes.getLugarfin());
            lugarinicio.setText(solicitudes.getLigarinicio());
            nombre.setText(solicitudes.getClienteservicio());
            celular.setText(solicitudes.getNumero());
            cargo.setText(solicitudes.getCargoservicio());
            referencia.setText(solicitudes.getReferencia());
        }

    }
}