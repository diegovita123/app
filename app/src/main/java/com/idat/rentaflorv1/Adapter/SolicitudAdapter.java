package com.idat.rentaflorv1.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.idat.rentaflorv1.Actividades.ConfirmacionServicioActivity;
import com.idat.rentaflorv1.Actividades.CotizacionDetalleActivity;
import com.idat.rentaflorv1.Actividades.ServicioDetalleActivity;
import com.idat.rentaflorv1.Actividades.SolicitudActivity;
import com.idat.rentaflorv1.Modelo.Solicitudes;
import com.idat.rentaflorv1.R;
import com.idat.rentaflorv1.metodos.Metodos;

import java.util.ArrayList;
import java.util.List;

public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.SolicitudHolder> {
    Context context;
    List<Solicitudes> lista;
    ProgressDialog progreso;
    StringRequest stringRequest;
    RequestQueue request;
    Metodos metodos;

    public SolicitudAdapter(Context context) {
        this.context = context;
        this.lista = new ArrayList<>();
        metodos = new Metodos();
    }

    public void AgregarElementos(ArrayList<Solicitudes> dataPersona) {
        lista.clear();
        lista.addAll(dataPersona);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SolicitudHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitudes, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new SolicitudHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudHolder holder, final int position) {
        request = Volley.newRequestQueue(context);
        final Solicitudes solicitudes = lista.get(position);

        holder.numerosolicitud.setText(lista.get(position).getNumsolicitud());
        holder.motivosolicitud.setText(lista.get(position).getMotivo());
        holder.fechainicio.setText(lista.get(position).getFechafin());
        holder.fechafin.setText(lista.get(position).getFechainicio());
        holder.lugarinicio.setText(lista.get(position).getLigarinicio());
        holder.lugarfin.setText(lista.get(position).getLugarfin());
        if (lista.get(position).getEstado().equalsIgnoreCase("Reservado")) {
            holder.estado.setText(metodos.formatoPrecio(String.valueOf(lista.get(position).getCosto())));
        } else if (lista.get(position).getEstado().equalsIgnoreCase("Finalizada ")) {
            holder.estado.setText(metodos.formatoPrecio(String.valueOf(lista.get(position).getCosto())));
        } else {
            holder.estado.setText(lista.get(position).getEstado());
        }

        holder.itemSolicitud.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if (lista.get(position).getEstado().equalsIgnoreCase("En Proceso ")) {

                                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                            builder.setTitle("Accion");
                                                            builder.setMessage("¿Que decea hacer?");
                                                            builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent(context, SolicitudActivity.class);
                                                                    intent.putExtra("position", position);
                                                                    intent.putExtra("id", lista.get(position).getIdsolicitud());
                                                                    context.startActivity(intent);
                                                                }
                                                            });
                                                            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    canselarSolicitudWS(solicitudes);
                                                                    lista.remove(position);
                                                                    notifyDataSetChanged();
                                                                }
                                                            });
                                                            builder.setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            builder.show();
                                                        } else if (lista.get(position).getEstado().equalsIgnoreCase("Finalizada ")) {
                                                            Intent intent = new Intent(context, CotizacionDetalleActivity.class);
                                                            intent.putExtra("position", position);
                                                            intent.putExtra("id", lista.get(position).getIdcotizacion());
                                                            context.startActivity(intent);
                                                        } else if (lista.get(position).getEstado().equalsIgnoreCase("Reservado")) {

                                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                            builder.setTitle("Accion");
                                                            builder.setMessage("¿Que decea hacer?");
                                                            builder.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    finalizarServicioWS(solicitudes);
                                                                    lista.remove(position);
                                                                    notifyDataSetChanged();
                                                                }
                                                            });
                                                            builder.setNegativeButton("Ver", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent(context, ServicioDetalleActivity.class);
                                                                    intent.putExtra("position", position);
                                                                    intent.putExtra("id", lista.get(position).getIdcotizacion());
                                                                    context.startActivity(intent);
                                                                }
                                                            });
                                                            builder.setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            builder.show();


                                                        }else if (lista.get(position).getEstado().equalsIgnoreCase("Terminado")){
                                                            Intent intent = new Intent(context, ConfirmacionServicioActivity.class);
                                                            intent.putExtra("position", position);
                                                            context.startActivity(intent);
                                                        }
                                                    }
                                                }
        );


    }

    private void canselarSolicitudWS(Solicitudes solicitudes) {
        progreso = new ProgressDialog(context);
        progreso.setTitle("Cancelando Solicitud...");
        progreso.show();


        String url = "http://192.168.100.10/rentaflor/cancela_solisitud.php?id=" + solicitudes.getIdsolicitud();

        try {
            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equalsIgnoreCase("elimina")) {

                        Toast.makeText(context, R.string.Eliminar_sms_exito, Toast.LENGTH_SHORT).show();
                    } else {
                        if (response.trim().equalsIgnoreCase("noExiste")) {
                            Toast.makeText(context, R.string.Eliminar_sms_no_existe, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, R.string.Elimina_sms_Error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    progreso.hide();
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, R.string.sms_error_de_coneccion, Toast.LENGTH_SHORT).show();
                    Log.i("ERROR", "" + request);
                    progreso.hide();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.add(stringRequest);

    }

    private void finalizarServicioWS(Solicitudes solicitudes) {
        progreso = new ProgressDialog(context);
        progreso.setTitle("Cancelando Solicitud...");
        progreso.show();


        String url = "http://192.168.100.10/rentaflor/finaliza_servicio.php?id=" + solicitudes.getIdcotizacion();

        try {
            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equalsIgnoreCase("elimina")) {

                        Toast.makeText(context,"Termino su servicio con exito", Toast.LENGTH_SHORT).show();
                    } else {
                        if (response.trim().equalsIgnoreCase("noExiste")) {
                            Toast.makeText(context, R.string.Eliminar_sms_no_existe, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, R.string.Elimina_sms_Error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    progreso.hide();
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, R.string.sms_error_de_coneccion, Toast.LENGTH_SHORT).show();
                    Log.i("ERROR", "" + request);
                    progreso.hide();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.add(stringRequest);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class SolicitudHolder extends RecyclerView.ViewHolder {
        TextView estado, numerosolicitud, motivosolicitud, fechainicio, fechafin, lugarinicio, lugarfin;
        LinearLayout itemSolicitud;

        public SolicitudHolder(@NonNull View itemView) {
            super(itemView);

            estado = itemView.findViewById(R.id.estado_solicitud);
            numerosolicitud = itemView.findViewById(R.id.numero_solicitud);
            motivosolicitud = itemView.findViewById(R.id.motivo_solicitudd);
            fechainicio = itemView.findViewById(R.id.fecha_inicio_solicitud);
            fechafin = itemView.findViewById(R.id.fecha_fin_solicitud);
            lugarinicio = itemView.findViewById(R.id.lugar_inicio);
            lugarfin = itemView.findViewById(R.id.lugar_destino);
            itemSolicitud = itemView.findViewById(R.id.item_solicitud);


        }
    }
}
