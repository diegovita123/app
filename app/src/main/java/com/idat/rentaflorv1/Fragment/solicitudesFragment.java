package com.idat.rentaflorv1.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idat.rentaflorv1.Actividades.SolicitudActivity;
import com.idat.rentaflorv1.Adapter.SolicitudAdapter;
import com.idat.rentaflorv1.MainActivity;
import com.idat.rentaflorv1.Modelo.Solicitudes;
import com.idat.rentaflorv1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link solicitudesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class solicitudesFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //-------------------------------------------------------------------------------

    public static ArrayList<Solicitudes> listaSolicitud;
    ProgressDialog progress;

    //para poder consumir los servicios web
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest = null;

    //los componentes de mi xml
    RecyclerView recyclersolicitud;
    FloatingActionButton fla_agregaSolicitud;


    public solicitudesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment solicitudesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static solicitudesFragment newInstance(String param1, String param2) {
        solicitudesFragment fragment = new solicitudesFragment();
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
        View view = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        //recepciono los componentes de mi xml
        recyclersolicitud = view.findViewById(R.id.recyclersolicitudes);
        fla_agregaSolicitud = view.findViewById(R.id.fab_solicitudes);
        //inicializo mis variables creadas
        listaSolicitud = new ArrayList<>();
        //inicializo variable para consumir web servico con Volley
        request = Volley.newRequestQueue(getContext());
        //formato del recyclerview
        recyclersolicitud.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclersolicitud.setHasFixedSize(true);

        //llamo a mi procedimineto
        cargarWSlistaSolicitudes();

        // click de floatinf
        fla_agregaSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), SolicitudActivity.class);
                in.putExtra("idtiposervicio", "1");
                startActivity(in);
            }
        });

        return view;

    }

    private void cargarWSlistaSolicitudes() {

        progress = new ProgressDialog(getContext());
        progress.setMessage("Cargando ...");
        progress.show();
        String id = MainActivity.idUsuario;
        String url = "http://192.168.100.10/rentaflor/listar_solicitud.php?estado=1&&id="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progress.hide();
        System.out.println();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progress.hide();
        Solicitudes solicitudes = null;
        JSONArray jsonArray = response.optJSONArray("solicitud");

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                solicitudes = new Solicitudes();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                solicitudes.setIdsolicitud(jsonObject.optInt("ID_SOLICITUD"));
                solicitudes.setNumsolicitud(jsonObject.optString("NUMERO_SOLICITUD"));
                solicitudes.setFechainicio(jsonObject.optString("FECHA_INICIO"));
                solicitudes.setFechafin(jsonObject.optString("FECHA_FIN"));
                solicitudes.setLigarinicio(jsonObject.optString("LUGAR_INICIO"));
                solicitudes.setLugarfin(jsonObject.optString("LUGAR_DESTINO"));
                solicitudes.setDescripcion(jsonObject.optString("DESCRIPCION"));
                solicitudes.setMotivo(jsonObject.optString("MOTIVO"));
                solicitudes.setEstado(jsonObject.optString("ESTADO"));
                solicitudes.setIdtiposervicio(jsonObject.optInt("ID_T_SERVICIO"));
                solicitudes.setDescripcioncamioneta(jsonObject.optString("DESCRIPCION_T_SERVICIO"));
                solicitudes.setIdcamioneta(jsonObject.optInt("ID_CAMIONETA"));
                solicitudes.setDescripcioncamioneta(jsonObject.optString("DESCRIPCION_CAMIONETA"));

                listaSolicitud.add(solicitudes);

            }

            SolicitudAdapter solicitudAdapter = new SolicitudAdapter(getContext());
            solicitudAdapter.AgregarElementos(listaSolicitud);
            recyclersolicitud.setAdapter(solicitudAdapter);


        } catch (Exception e) {
            e.fillInStackTrace();
           // Toast.makeText(getContext(), R.string.sms_error_de_coneccion, Toast.LENGTH_SHORT).show();
            progress.hide();
        }

    }
}