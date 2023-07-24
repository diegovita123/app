package com.idat.rentaflorv1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idat.rentaflorv1.Modelo.Paquete;
import com.idat.rentaflorv1.R;

import java.util.ArrayList;
import java.util.List;

public class PaqueteAdapter extends RecyclerView.Adapter<PaqueteAdapter.PaqueteHolder> {

    List<String> lista;
    Context context;

    public PaqueteAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    public void AgregarLista(ArrayList<String> data) {
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PaqueteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paquete, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new PaqueteHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PaqueteHolder holder, int position) {
        holder.descripcion.setText(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PaqueteHolder extends RecyclerView.ViewHolder {
        TextView descripcion;

        public PaqueteHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.p_descripcion);
        }
    }
}
