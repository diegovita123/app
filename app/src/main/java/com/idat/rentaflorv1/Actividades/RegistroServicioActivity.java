package com.idat.rentaflorv1.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.idat.rentaflorv1.MainActivity;
import com.idat.rentaflorv1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistroServicioActivity extends AppCompatActivity {

    private TextInputEditText nombre, apellido, celular;
    AutoCompleteTextView cargo, referencia;
    private Button btnagrega;

    private int position, idcotizacion;
    private String idcliente;

    RequestQueue request;
    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_servicio);

        nombre = findViewById(R.id.r_nombre);
        apellido = findViewById(R.id.r_apellido);
        celular = findViewById(R.id.r_celular);
        cargo = findViewById(R.id.r_cargo);
        referencia = findViewById(R.id.r_referencia);
        btnagrega = findViewById(R.id.r_agregar);

        idcliente = MainActivity.idUsuario;

        request = Volley.newRequestQueue(getApplicationContext());

        //llenamos los autocompletes
        llenaViewCargo();
        llenaViewreferencia();
        //recupero los datos enviados de la otra actividad
        if (getIntent().hasExtra("position")) {
            position = getIntent().getIntExtra("position", 0);
            idcotizacion = getIntent().getIntExtra("id", 0);
        } else {
            Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }

        btnagrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().trim().isEmpty() &&
                        apellido.getText().toString().trim().isEmpty() &&
                        celular.getText().toString().trim().isEmpty() &&
                        cargo.getText().toString().trim().isEmpty() &&
                        referencia.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegistroServicioActivity.this, "Campos Obligatorios", Toast.LENGTH_SHORT).show();

                }else {
                    cargarWSagregarServicio();

                }

            }
        });
    }

    private void cargarWSagregarServicio() {
        String url = "http://192.168.100.10/rentaflor/agregar_servicio.php";


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // progreso.hide();

                if (response.trim().equalsIgnoreCase("registra")) {

                    Toast.makeText(getApplicationContext(), R.string.agercar_sms_exito, Toast.LENGTH_SHORT).show();
                    enviarPantallaExito();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.actualizar_sms_error, Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ", "" + response);
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                //  progreso.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<>();
                parametros.put("idcliente", idcliente);
                parametros.put("idcotizacion", String.valueOf(idcotizacion));
                parametros.put("nombre", cadenaAmayuscula(nombre.getText().toString()));
                parametros.put("apellido", cadenaAmayuscula(apellido.getText().toString()));
                parametros.put("celular", celular.getText().toString());
                parametros.put("cargo", cadenaAmayuscula(cargo.getText().toString()));
                parametros.put("referencia", cadenaAmayuscula(referencia.getText().toString()));
                return parametros;
            }
        };
        request.add(stringRequest);
    }

    private String cadenaAmayuscula(String cadena) {
        String firstLtr = cadena.substring(0, 1);
        String restLtrs = cadena.substring(1, cadena.length());

        firstLtr = firstLtr.toUpperCase();
        cadena = firstLtr + restLtrs;
        return cadena;
    }

    private void enviarPantallaExito() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("cotizacion", 1);
        startActivity(intent);
        finish();
    }
    private void llenaViewCargo() {
        String[] cadena = new String[]{
                "DIRECTOR EJECUTIVO - CEO",
                "DIRECTOR DE OPERACIONES - COO",
                "DIRECTOR COMERCIAL - CSO",
                "DIRECTOR DE MARKETING - CMO",
                "DIRECTOR DE RECURSOS HUMANOS - CHRO",
                "DIRECTOR FINANCIERO - CFO",
                "SUPERVISOR",
                "TECNICO",
                "OPERARIO",
                "INGENIERO",
                "PERSONA NATURAL"
        };
        ArrayAdapter<String> lista = new ArrayAdapter<>(getApplicationContext(), R.layout.menu_items, cadena);
        cargo.setAdapter(lista);
    }

    private void llenaViewreferencia() {
        String[] cadena = new String[]{
                "Av. Antonio Miro Quesada Nro. 425",
                "Juan de Aliaga, Oficinas 1307-1313",
                "Av. Alfredo Mendiola Nro. 3805",
                "Mall del sur",
                "Plaza Norte",
                "Mega Plaza",
                "Megacentro Lurin"

        };
        ArrayAdapter<String> lista = new ArrayAdapter<>(getApplicationContext(), R.layout.menu_items, cadena);
        referencia.setAdapter(lista);
    }

}