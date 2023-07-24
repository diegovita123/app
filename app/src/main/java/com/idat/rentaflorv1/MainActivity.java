package com.idat.rentaflorv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.idat.rentaflorv1.Fragment.ServicioContentFragment;
import com.idat.rentaflorv1.Fragment.ServicioFinalFragment;
import com.idat.rentaflorv1.Fragment.cotizacionesFragment;
import com.idat.rentaflorv1.Fragment.facturasFragment;
import com.idat.rentaflorv1.Fragment.homeFragment;
import com.idat.rentaflorv1.Fragment.informacionFragment;
import com.idat.rentaflorv1.Fragment.serviciosFragment;
import com.idat.rentaflorv1.Fragment.solicitudFragment;
import com.idat.rentaflorv1.Fragment.solicitudesFragment;
import com.idat.rentaflorv1.FragmentDetalle.CotizaciondetalleFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    private DrawerLayout mNavDrawer;
    TextView nombre, empresa;
    public static String idUsuario,user,nombreempresa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View heder = ((NavigationView) findViewById(R.id.navigation_view)).getHeaderView(0);
        mNavDrawer = findViewById(R.id.drawer_layout);

        nombre = heder.findViewById(R.id.nombre_user);
        empresa = heder.findViewById(R.id.empresa_user);

        SharedPreferences preferences = getSharedPreferences("preflogin", Context.MODE_PRIVATE);
        idUsuario = (preferences.getString("idusuario", "0"));
        user=(preferences.getString("nombreusuario", "Usuario"));
        nombreempresa=(preferences.getString("empresausuario", "Empresa"));
        empresa.setText(nombreempresa);
        nombre.setText(user);
        NavigationView navigationView = findViewById(R.id.navigation_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mNavDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

// fragment que va a mostrar en pantalla inicio
            navigationView.setCheckedItem(R.id.nav_home);
            cargarPantallaInicio();



    }

    @Override
    public void onBackPressed() {
        if (mNavDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavDrawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment miFragment = null;
        String titulo = null;
        boolean seleccionado = false;
        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                miFragment = new homeFragment();
                seleccionado = true;
                titulo = "Home";
                break;
            case R.id.nav_servicios:
                miFragment = new ServicioContentFragment();
                seleccionado = true;
                titulo = "Servicios";
                break;
            case R.id.nav_solicitudes:
                miFragment = new solicitudFragment();
                seleccionado = true;
                titulo = "Solicitudes";
                break;
            case R.id.nav_cotizaciones:
                miFragment = new CotizaciondetalleFragment();
                seleccionado = true;
                titulo = "Cotización";
                break;
            case R.id.nav_facturas:
                miFragment = new facturasFragment();
                seleccionado = true;
                titulo = "Facturas";
                break;
            case R.id.nav_mas:
                miFragment = new informacionFragment();
                seleccionado = true;
                titulo = "mas";
                break;
            case R.id.nav_sesion:
                SharedPreferences preferences = getSharedPreferences("preflogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                startActivity(new Intent(getApplicationContext(), login.class));

                break;

        }
        if (seleccionado == true) {
            getSupportActionBar().setTitle(titulo);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, miFragment).commit();
            mNavDrawer.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }

    public void cargarPantallaInicio() {
        Fragment miFragment = null;
        String titulo = null;

        if (getIntent().hasExtra("solicitudes")) {
            miFragment = new solicitudFragment();
            titulo = "Solicitudes";

        } else if (getIntent().hasExtra("cotizacion")) {
            miFragment = new serviciosFragment();
            titulo = "Servicios";
            }
        else{
            miFragment = new homeFragment();
            titulo = "Home";
        }


        getSupportActionBar().setTitle(titulo);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, miFragment).commit();
        mNavDrawer.closeDrawer(GravityCompat.START);


        }



    }

