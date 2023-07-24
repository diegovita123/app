package com.idat.rentaflorv1.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.idat.rentaflorv1.DepartamentoActivity2;
import com.idat.rentaflorv1.Fragment.solicitudesFragment;
import com.idat.rentaflorv1.MainActivity;
import com.idat.rentaflorv1.Modelo.Camioneta;
import com.idat.rentaflorv1.Modelo.Departamento;
import com.idat.rentaflorv1.Modelo.Distrito;
import com.idat.rentaflorv1.Modelo.Provincia;
import com.idat.rentaflorv1.Modelo.Solicitudes;
import com.idat.rentaflorv1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolicitudActivity extends AppCompatActivity {

    Spinner spinner_distrito, spinner_camioneta;
    Button btn_solisitar, btn_volver;
    ImageView btn_masdepartamento;
    TextInputEditText txtfechainicio, txtfechafin, descripcion, lugarinicio, lugarfin, motivo;

    //tetx aayuda de otros fragments
    String nombredepartamento, nombreprovincia, idprovincia;
    TextView tvinf_dep_dist;


    //parametros para consumir el servicio web
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestdistrito, requestcamioneta, requestsolicitud;
    StringRequest stringRequest;


    //variables necesarias
    ArrayList<Distrito> listadistrito;
    ArrayList<Camioneta> listacamioneta;

    //id de lOS spiner necesarios
    String idveiculo, iddistrito;
    //id campos a recuperar para enviar el ws solicitud
    String idusuario, idtiposervicio;

    //id solicitud para actualizar
    int idsolicitud=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);
        // todos los controladores de xml
        //botones
        btn_solisitar = findViewById(R.id.btn_agregar_s_servicio);
        btn_volver = findViewById(R.id.btn_volver_s_servicio);
        btn_masdepartamento = findViewById(R.id.btn_mas_distrito);
        //spineer
        spinner_camioneta = findViewById(R.id.spinner_camioneta);
        spinner_distrito = findViewById(R.id.spinner_distrito);
        //infofrmacion mostrada
        tvinf_dep_dist = findViewById(R.id.tvnombredepartamentoprovincia);

        //datos de xml
        txtfechainicio = findViewById(R.id.feha_inicio_solicitud);
        txtfechafin = findViewById(R.id.fecha_fin_solicitud);
        descripcion = findViewById(R.id.descripcion_solicitud);
        lugarinicio = findViewById(R.id.lugar_inicio_solicitud);
        lugarfin = findViewById(R.id.lugar_destino_solicitud);
        motivo = findViewById(R.id.motivo_solicitud);

        // recuperamos los parametros que recibimos de la actividad de departamento



        //inisializamos el request
        requestdistrito = Volley.newRequestQueue(getApplicationContext());
        requestcamioneta = Volley.newRequestQueue(getApplicationContext());
        requestsolicitud = Volley.newRequestQueue(getApplicationContext());

        //damos formato a los campos de texto fecha inicio y fecha fiin
        txtfechainicio.setText(new SimpleDateFormat("yyy-MM-dd").format(new Date()));
        txtfechafin.setText(new SimpleDateFormat("yyy-MM-dd").format(new Date()));

        //inicializamos las variables creadas
        listadistrito = new ArrayList<>();
        listacamioneta = new ArrayList<>();

        recuperarParametrosDepartamentoProvincia();

        //cargamos el spinner
        cargarWSdistrito();
        cargarWScamioneta();

        //// valido si me esta llegando para editar o actualizar los campos
        btn_solisitar.setTag(0);
        if (getIntent().hasExtra("position")) {
            int position = getIntent().getIntExtra("position", 0);
            idsolicitud=getIntent().getIntExtra("id",0);
            btn_solisitar.setTag(idsolicitud);
            btn_solisitar.setText("Actualizar");
            Solicitudes solicitudes = solicitudesFragment.listaSolicitud.get(position);
            txtfechainicio.setText(solicitudes.getFechainicio());
            txtfechafin.setText(solicitudes.getFechafin());
            descripcion.setText(solicitudes.getDescripcion());
            lugarinicio.setText(solicitudes.getLigarinicio());
            lugarfin.setText(solicitudes.getLugarfin());
            motivo.setText(solicitudes.getMotivo());

            for (int i = 0; i < listacamioneta.size(); i++) {
                if (listacamioneta.get(i).getId() == solicitudes.getIdcamioneta()) {
                    spinner_camioneta.setSelection(i);
                    break;
                }
            }

        }


        //recuperamos el id cliente de shartPreference
        SharedPreferences preferences = getSharedPreferences("preflogin", Context.MODE_PRIVATE);
        idusuario = (preferences.getString("idusuario", "1"));
        //Toast.makeText(this, "id usiario " + idusuario, Toast.LENGTH_SHORT).show();
        // recuperamos ir tipo servicio enviado de home

        idtiposervicio = getIntent().getStringExtra("idtiposervicio");

        Log.e("EROOR_Tipo", String.valueOf(idtiposervicio));

        // click de los botones
        btn_masdepartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DepartamentoActivity2.class));
            }
        });

        spinner_distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iddistrito = String.valueOf(listadistrito.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_camioneta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idveiculo = String.valueOf(listacamioneta.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_solisitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idsolicitud==0){
                    cargarWSagregarSolicitud();
                }else {
                    cargarWSeditaSolicitud();
                }

               
            }
        });
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volver al menu principal
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void cargarWSagregarSolicitud() {
        String url = "http://192.168.100.10/rentaflor/agregar_solisitud.php";


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // progreso.hide();

                if (response.trim().equalsIgnoreCase("registra")) {

                    Toast.makeText(getApplicationContext(), R.string.agercar_sms_exito, Toast.LENGTH_SHORT).show();
                    volverListaSolicitudes();
                } else {
                    //Toast.makeText(getApplicationContext(), R.string.actualizar_sms_error, Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ", "" + response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getApplicationContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                //  progreso.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<>();
                parametros.put("fechainicio", txtfechainicio.getText().toString());
                parametros.put("fechafin", txtfechafin.getText().toString());
                parametros.put("descripcion", descripcion.getText().toString());
                parametros.put("lugarinicio", lugarinicio.getText().toString());
                parametros.put("lugarfin", lugarfin.getText().toString());
                parametros.put("motivo", motivo.getText().toString());
                parametros.put("iddistrito", iddistrito);
                parametros.put("idcamioneta", idveiculo);
                parametros.put("idtiposervicio", "1");
                parametros.put("idcliente", idusuario);

                return parametros;
            }
        };
        requestsolicitud.add(stringRequest);
    }

    private void cargarWSeditaSolicitud() {
        String url = "http://192.168.100.10/rentaflor/editar_solisitud.php";


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // progreso.hide();

                if (response.trim().equalsIgnoreCase("registra")) {

                    Toast.makeText(getApplicationContext(), R.string.actializar_sms_exito, Toast.LENGTH_SHORT).show();
                    volverListaSolicitudes();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.actualizar_sms_error, Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ", "" + response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                //  progreso.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<>();
                parametros.put("fechainicio", txtfechainicio.getText().toString());
                parametros.put("fechafin", txtfechafin.getText().toString());
                parametros.put("descripcion", descripcion.getText().toString());
                parametros.put("lugarinicio", lugarinicio.getText().toString());
                parametros.put("lugarfin", lugarfin.getText().toString());
                parametros.put("motivo", motivo.getText().toString());
                parametros.put("iddistrito", iddistrito);
                parametros.put("idcamioneta", idveiculo);
                parametros.put("idsolicitud",String.valueOf(idsolicitud));

                return parametros;
            }
        };
        requestsolicitud.add(stringRequest);
    }


    private void cargarWScamioneta() {


        String url = "http://192.168.100.10/rentaflor/listar_camioneta.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Camioneta camioneta = null;
                JSONArray json = response.optJSONArray("camioneta");
                listacamioneta.clear();
                Log.e("jsonprovincia", json.toString());
                try {
                    for (int i = 0; i < json.length(); i++) {
                        camioneta = new Camioneta();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        camioneta.setId(jsonObject.optInt("id"));
                        camioneta.setDescripcion(jsonObject.optString("descripcion"));
                        listacamioneta.add(camioneta);
                    }
                    llenarSpinerCamioneta();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.sms_error_de_coneccion + e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("error", e.toString());

                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    Toast.makeText(getApplicationContext(), R.string.sms_error_de_coneccion + error.toString(), Toast.LENGTH_LONG).show();
                    Log.i("ERROR", error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        requestcamioneta.add(jsonObjectRequest);

    }

    public void cargarWSdistrito() {


        String url = "http://192.168.100.10/rentaflor/listar_distrito.php?id=" + idprovincia;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Distrito distrito = null;
                JSONArray json = response.optJSONArray("distrito");
                listadistrito.clear();
                Log.e("jsonprovincia", json.toString());
                try {
                    for (int i = 0; i < json.length(); i++) {
                        distrito = new Distrito();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        distrito.setId(jsonObject.optInt("id"));
                        distrito.setDescripcion(jsonObject.optString("descripcion"));
                        listadistrito.add(distrito);
                    }
                    llenarSpinerDistrito();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.sms_error_de_coneccion + e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("error", e.toString());

                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    Toast.makeText(getApplicationContext(), R.string.sms_error_de_coneccion + error.toString(), Toast.LENGTH_LONG).show();
                    Log.i("ERROR", error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        requestdistrito.add(jsonObjectRequest);

    }

    private void llenarSpinerDistrito() {
        List<String> lablesT = new ArrayList<>();
        for (int i = 0; i < listadistrito.size(); i++) {
            lablesT.add(listadistrito.get(i).getDescripcion());
        }

        ArrayAdapter<String> spinnerAdapterT = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, lablesT);
        spinnerAdapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_distrito.setAdapter(spinnerAdapterT);
    }

    private void llenarSpinerCamioneta() {
        List<String> lablesT = new ArrayList<>();
        for (int i = 0; i < listacamioneta.size(); i++) {
            lablesT.add(listacamioneta.get(i).getDescripcion());
        }

        ArrayAdapter<String> spinnerAdapterT = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, lablesT);
        spinnerAdapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_camioneta.setAdapter(spinnerAdapterT);
    }


    public void recuperarParametrosDepartamentoProvincia() {
        Bundle parametros = this.getIntent().getExtras();
        try {
            if (parametros == null) {
                nombredepartamento = "LIMA";
                nombreprovincia = "LIMA";
                idprovincia = "127";
                tvinf_dep_dist.setText(" " + nombredepartamento + "..->.." + nombreprovincia);
            } else {
                nombredepartamento = parametros.getString("departamento", "LIMA");
                nombreprovincia = parametros.getString("provincia", "LIMA");
                idprovincia = parametros.getString("idprovincia", "127");
                tvinf_dep_dist.setText(" " + nombredepartamento + "..->.." + nombreprovincia);

            }
        } catch (Exception e) {
            e.fillInStackTrace();
            Log.e("ERROR", e.toString());
        }


    }

    public void volverListaSolicitudes() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("solicitudes", 1);
        startActivity(intent);
        finish();
    }



}
