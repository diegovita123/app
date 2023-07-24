package com.idat.rentaflorv1.Actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.idat.rentaflorv1.Adapter.PaqueteAdapter;
import com.idat.rentaflorv1.Fragment.solicitudesFragment;
import com.idat.rentaflorv1.FragmentDetalle.SolicitudFinalFragment;
import com.idat.rentaflorv1.Modelo.Solicitudes;
import com.idat.rentaflorv1.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CotizacionDetalleActivity extends AppCompatActivity {
    TextView fechainicio, fechafin, c_costo, lugarinicio, lugarfin, motivo;
    TextView nombrecamioneta, tiposervicio, numerosolicitud, capacidad, colorcarro;
    ImageView imgcarro;
    Button btn_Aceptar;
    RecyclerView recyclerpaquete;
    RecyclerView recyclerCondAlquiler;
    PaqueteAdapter adapter;

    private int position;


    private Button btnprueva, btnagregarprueva;

    int idcotizacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizacion_detalle);

        nombrecamioneta = findViewById(R.id.nombre_camioneta_d_c);
        tiposervicio = findViewById(R.id.c_tipo_servico_solicitud);
        numerosolicitud = findViewById(R.id.numero_solicitud_c);
        imgcarro = findViewById(R.id.imagenToyota);
        motivo = findViewById(R.id.c_motivo_solicitudd);
        lugarinicio = findViewById(R.id.c_lugar_inicio);
        lugarfin = findViewById(R.id.c_lugar_destino);
        fechainicio = findViewById(R.id.c_fecha_inicio_solicitud);
        fechafin = findViewById(R.id.c_fecha_fin_solicitud);
        c_costo = findViewById(R.id.c_costo);
        capacidad = findViewById(R.id.c_numero_asientos);
        colorcarro = findViewById(R.id.c_color_camioneta);

        btn_Aceptar = findViewById(R.id.c_btn_aceptar);


        //--------------------------------------------------------------------
        // los recycler view
        recyclerpaquete = findViewById(R.id.c_recycler_paquete);
        recyclerCondAlquiler = findViewById(R.id.c_recycler_condicionesAlquiler);
        //recycler Condiciones de Alquiler
        recyclerCondAlquiler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCondAlquiler.setHasFixedSize(true);
        llenarRecyclerCondicionesAlquiler();
        // recycler paquete
        recyclerpaquete.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerpaquete.setHasFixedSize(true);
        llenarRecyclerPaquete();


        /////recuperar los datos
        if (getIntent().hasExtra("position")) {
            position = getIntent().getIntExtra("position", 0);
            idcotizacion = getIntent().getIntExtra("id", 0);
            Solicitudes solicitudes = SolicitudFinalFragment.listaSolicitud.get(position);
            nombrecamioneta.setText(solicitudes.getDescripcioncamioneta());
            tiposervicio.setText(solicitudes.getDescripciontiposervicio());
            numerosolicitud.setText(solicitudes.getNumsolicitud());
            lugarinicio.setText(solicitudes.getLigarinicio());
            lugarfin.setText(solicitudes.getLugarfin());
            motivo.setText(solicitudes.getMotivo());
            fechafin.setText(solicitudes.getFechafin());
            fechainicio.setText(solicitudes.getFechainicio());
            capacidad.setText(solicitudes.getCAPACIDAD());
            colorcarro.setText(solicitudes.getColor());
            c_costo.setText(formatoPrecio(solicitudes.getCosto()));
            int idcamioneta = solicitudes.getIdcamioneta();
            switch (idcamioneta) {
                case 2:
                    imgcarro.setImageResource(R.drawable.toyotahilux4x4);
                    break;
                case 3:
                    imgcarro.setImageResource(R.drawable.ford);
                    break;
                case 4:
                    imgcarro.setImageResource(R.drawable.fortune);
                    break;
                case 5:
                    imgcarro.setImageResource(R.drawable.solari);
                    break;
                case 6:
                    imgcarro.setImageResource(R.drawable.h1);
                    break;
                case 7:
                    imgcarro.setImageResource(R.drawable.mercedes);
                    break;
            }
        }

        btn_Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroServicioActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("id", idcotizacion);
                startActivity(intent);
            }
        });
    }

    public void llenarRecyclerPaquete() {
        ArrayList<String> nombre = new ArrayList<>();
        nombre.add("280 km libres por día/ 1.20 km adicional");
        nombre.add("Equipamiento de unidad bajo estándares mineros");
        nombre.add("Póliza de seguro contra todo riesgo de la unidad vehicular");
        nombre.add("Certificado de revisión técnica vehicular.");
        nombre.add("Equipo de ubicación GPS");
        nombre.add("Cobertura de asistencia mecánica a nivel nacional");
        nombre.add("Vehículo reten en caso de emergencia.");
        adapter = new PaqueteAdapter(getApplicationContext());
        adapter.AgregarLista(nombre);
        recyclerpaquete.setAdapter(adapter);
    }

    public void llenarRecyclerCondicionesAlquiler() {
        ArrayList<String> nombre = new ArrayList<>();
        nombre.add("Forma de pago: Crédito a 60 días");
        nombre.add("Penalidad: La cancelación del servicio se hará con 48 horas de anticipación de lo contario\n" +
                "se cobrará una penalidad del 50% de la tarifa por día");
        nombre.add("Los conductores tienen autorizadas máximo 12 horas de labor por día ");
        nombre.add("La unidad será entregada en perfectas condiciones de higiene y limpieza y deberá ser \n" +
                "retornada en las mismas condiciones, caso contrario en la valorización será considerado el \n" +
                "costo del lavado completo de la Unidad S/. 40.00.");
        adapter = new PaqueteAdapter(getApplicationContext());
        adapter.AgregarLista(nombre);
        recyclerCondAlquiler.setAdapter(adapter);
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