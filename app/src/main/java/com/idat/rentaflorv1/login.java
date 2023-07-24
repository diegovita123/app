package com.idat.rentaflorv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.idat.rentaflorv1.Modelo.LoginCliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class login extends AppCompatActivity {
    private EditText etusuario, etpassword;
    private Button btningresar;
    private final static String canal_id = "NOTIFICACION";
    private final static int notif_id = 0;
    private PendingIntent pendingIntent;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    LoginCliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etusuario = (EditText) findViewById(R.id.etusuario);
        etpassword = (EditText) findViewById(R.id.etpassword);
        btningresar = findViewById(R.id.btningresar);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        cliente=new LoginCliente();


        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (etusuario.getText().toString().trim().equals("") || etpassword.getText().toString().trim().equals("")) {
                    Toast.makeText(login.this, "Ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    ValidarUsuario();
                }

            }


        });
    }

    public void ValidarUsuario() {


        String url = "http://192.168.100.10/rentaflor/validar_usuario.php?usuario=" + etusuario.getText() + "&password=" + etpassword.getText();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("usuario");
                Log.e("usuario", response.toString());
                JSONObject jsonObject = null;

                try {
                    jsonObject = json.getJSONObject(0);
                    cliente.setId_cliente(jsonObject.optInt("ID_CLIENTE"));
                    cliente.setNombres(jsonObject.optString("nombres"));
                    cliente.setEmpresa(jsonObject.optString("empresa"));

                    if (cliente.getId_cliente() == 0) {
                        Toast.makeText(login.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(login.this, "Biembenido: " + cliente.getNombres(), Toast.LENGTH_SHORT).show();
                        guardarPreferenica();
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(in);
                        finish();

                    }


                } catch (JSONException e) {
                    e.fillInStackTrace();
                    Toast.makeText(getApplicationContext(), "Error de coneccion " + e.toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Toast.makeText(getApplicationContext(), R.string.sms_error_de_coneccion + error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("ERROR", error.toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void crearNotif() {
        NotificationCompat.Builder nt = new NotificationCompat.Builder(getApplicationContext(), canal_id);
        nt.setSmallIcon(R.drawable.ic_car);
        nt.setContentTitle("RentaFlor");
        nt.setContentText("Hola " + etusuario.getText() + " Bienvenido!");
        nt.setColor(Color.BLUE);
        nt.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        nt.setLights(Color.BLACK, 1000, 1000);
        nt.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        nt.setDefaults(Notification.DEFAULT_SOUND);
        nt.setContentIntent(pendingIntent);
        NotificationManagerCompat nmc = NotificationManagerCompat.from(getApplicationContext());
        nmc.notify(notif_id, nt.build());
    }

    public void crearNotificacionOreo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence nombre = "Notificacion";
            NotificationChannel nc = new NotificationChannel(canal_id, nombre, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.createNotificationChannel(nc);
        }
    }

    public void setPendingIntent() {
        Intent i = new Intent(this, MainActivity.class);
        TaskStackBuilder tsb = TaskStackBuilder.create(this);
        tsb.addParentStack(MainActivity.class);
        tsb.addNextIntent(i);
        pendingIntent = tsb.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void guardarPreferenica() {
        SharedPreferences sharedPreferences = getSharedPreferences("preflogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idusuario",String.valueOf(cliente.getId_cliente()));
        editor.putString("nombreusuario",cliente.getNombres());
        editor.putString("empresausuario",cliente.getEmpresa());
        editor.putBoolean("sesion",true);
        editor.commit();



    }

}