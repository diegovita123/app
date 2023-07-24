package com.idat.rentaflorv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.idat.rentaflorv1.Actividades.SolicitudActivity;
import com.idat.rentaflorv1.Modelo.Departamento;
import com.idat.rentaflorv1.Modelo.Provincia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class DepartamentoActivity2 extends AppCompatActivity {
    RequestQueue request;
    RequestQueue requestp;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<Departamento> listadep;
    ArrayList<Provincia> listaprovincia;
    Spinner spinner_departamento, spinner_provincia;

    private String iddepartamento, idprovincia,nombredepartamento,nombreprovincia;
    private Button btndepartamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamento2);


        spinner_departamento = findViewById(R.id.spinner_departamento);
        spinner_provincia = findViewById(R.id.spinner_provincia);
        btndepartamento=findViewById(R.id.btn_departamentoprovincia);

        request = Volley.newRequestQueue(getApplicationContext());
        requestp = Volley.newRequestQueue(getApplicationContext());
        listadep = new ArrayList<>();
        listaprovincia = new ArrayList<>();

        cargarWSdepartamento();

        btndepartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DepartamentoActivity2.this, "provincia :" + nombreprovincia, Toast.LENGTH_SHORT).show();
                Bundle parmetros = new Bundle();
                parmetros.putString("provincia", nombreprovincia);
                parmetros.putString("departamento", nombredepartamento);
                parmetros.putString("idprovincia", idprovincia);

                Intent in = new Intent(getApplicationContext(), SolicitudActivity.class);
                in.putExtras(parmetros);
                startActivity(in);
                finish();
            }
        });

        spinner_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idprovincia = String.valueOf(listaprovincia.get(position).getId());
                nombreprovincia=listaprovincia.get(position).getDescripcion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iddepartamento = String.valueOf(listadep.get(position).getId());
                nombredepartamento=listadep.get(position).getDescripcion();
                Toast.makeText(DepartamentoActivity2.this, "id :" + nombredepartamento, Toast.LENGTH_SHORT).show();
                cargarWSprovincia();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void cargarWSdepartamento() {
        String url = "http://192.168.100.10/rentaflor/listar_departamento.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Departamento departamentos = null;
                JSONArray json = response.optJSONArray("departamento");

                try {
                    for (int i = 0; i < json.length(); i++) {
                        departamentos = new Departamento();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        departamentos.setId(jsonObject.optInt("id"));
                        departamentos.setDescripcion(jsonObject.optString("descripcion"));
                        listadep.add(departamentos);
                    }

                    llenarSpinerDepartamento();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.sms_error_de_coneccion, Toast.LENGTH_LONG).show();

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
        request.add(jsonObjectRequest);

    }

    private void cargarWSprovincia() {
        String url = "http://192.168.100.10/rentaflor/listar_provincia.php?id=" + iddepartamento;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Provincia provincia = null;
                JSONArray json = response.optJSONArray("provincia");
                listaprovincia.clear();
                Log.e("jsonprovincia", json.toString());
                try {
                    for (int i = 0; i < json.length(); i++) {
                        provincia = new Provincia();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        provincia.setId(jsonObject.optInt("id"));
                        provincia.setDescripcion(jsonObject.optString("descripcion"));
                        listaprovincia.add(provincia);
                    }
                    llenarSpinerProvincia();

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
        requestp.add(jsonObjectRequest);

    }

    private void llenarSpinerDepartamento() {
        List<String> lablesT = new ArrayList<>();
        for (int i = 0; i < listadep.size(); i++) {
            lablesT.add(listadep.get(i).getDescripcion());
        }

        ArrayAdapter<String> spinnerAdapterT = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, lablesT);
        spinnerAdapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_departamento.setAdapter(spinnerAdapterT);
    }

    private void llenarSpinerProvincia() {
        List<String> lablesT = new ArrayList<>();
        for (int i = 0; i < listaprovincia.size(); i++) {
            lablesT.add(listaprovincia.get(i).getDescripcion());
        }

        ArrayAdapter<String> spinnerAdapterT = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, lablesT);
        spinnerAdapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_provincia.setAdapter(spinnerAdapterT);
    }
}