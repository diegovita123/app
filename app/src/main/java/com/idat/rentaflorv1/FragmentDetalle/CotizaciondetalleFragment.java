package com.idat.rentaflorv1.FragmentDetalle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.idat.rentaflorv1.Adapter.PaqueteAdapter;
import com.idat.rentaflorv1.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CotizaciondetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CotizaciondetalleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn_Aceptar;
    RecyclerView recyclerpaquete;
    RecyclerView recyclerCondAlquiler;
    PaqueteAdapter adapter;
    TextView c_costo;

    public CotizaciondetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CotizaciondetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CotizaciondetalleFragment newInstance(String param1, String param2) {
        CotizaciondetalleFragment fragment = new CotizaciondetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cotizaciondetalle, container, false);
        // parametros del xml
        c_costo= view.findViewById(R.id.c_costo);



// los recycler view
        recyclerpaquete = view.findViewById(R.id.c_recycler_paquete);
        recyclerCondAlquiler = view.findViewById(R.id.c_recycler_condicionesAlquiler);


        btn_Aceptar = view.findViewById(R.id.c_btn_aceptar);



        //recycler Condiciones de Alquiler
        recyclerCondAlquiler.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerCondAlquiler.setHasFixedSize(true);
        llenarRecyclerCondicionesAlquiler();
        // recycler paquete
        recyclerpaquete.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerpaquete.setHasFixedSize(true);
        llenarRecyclerPaquete();


        //prueva
        NumberFormat pen_promedio = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("S/. ");
        ((DecimalFormat) pen_promedio).setDecimalFormatSymbols(dfs);
        String promedio;
        promedio = pen_promedio.format(Double.parseDouble("25478.5"));
        c_costo.setText(promedio);
        return view;
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
        adapter = new PaqueteAdapter(getContext());
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
        adapter = new PaqueteAdapter(getContext());
        adapter.AgregarLista(nombre);
        recyclerCondAlquiler.setAdapter(adapter);
    }

}