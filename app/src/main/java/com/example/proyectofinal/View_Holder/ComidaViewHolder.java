package com.example.proyectofinal.View_Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;

import Interface.ItemClickListener;

public class ComidaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Comida_Nombre;
    public ImageView Comida_Imagen;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ComidaViewHolder(@NonNull View itemView) {
        super(itemView);

        Comida_Nombre = (TextView) itemView.findViewById(R.id.comida_name);
        Comida_Imagen = (ImageView) itemView.findViewById(R.id.comida_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }
}
