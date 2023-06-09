package com.example.proyectofinal.View_Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;

import Interface.ItemClickListener;


public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Menu_Nombre;
    public ImageView Menu_Imagen;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        Menu_Nombre = itemView.findViewById(R.id.menu_name);
        Menu_Imagen =  itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(),false);
    }
}
