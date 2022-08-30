package com.example.proyectofinal.View_Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;

import Interface.ItemClickListener;

public class OrdenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrdenID, txtOrdenEstado, txtNombre, txtMesa,txtCliente;
    private ItemClickListener itemClickListener;

    public OrdenViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMesa = itemView.findViewById(R.id.orden_mesa);
        txtNombre = itemView.findViewById(R.id.orden_nombre);
        txtOrdenEstado = itemView.findViewById(R.id.orden_estado);
        txtOrdenID = itemView.findViewById(R.id.orden_id);
        txtCliente= itemView.findViewById(R.id.orden_cliente);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
