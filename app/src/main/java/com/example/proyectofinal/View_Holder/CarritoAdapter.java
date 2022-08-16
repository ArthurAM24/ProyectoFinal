package com.example.proyectofinal.View_Holder;


import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Orden;
import com.example.proyectofinal.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Interface.ItemClickListener;

class  CarritoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        ,View.OnCreateContextMenuListener {

    public TextView txtCarrito_nomb, txtPrecio;
    public ImageView imageCarritoCount;

    private ItemClickListener itemClickListener;

    public void setTxtCarrito_nomb(TextView txtCarrito_nomb) {
        this.txtCarrito_nomb = txtCarrito_nomb;
    }

    public CarritoViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCarrito_nomb = (TextView) itemView.findViewById(R.id.cart_item_name);
        txtPrecio = (TextView) itemView.findViewById(R.id.cart_item_Price);
        imageCarritoCount = (ImageView) itemView.findViewById(R.id.cart_item_count);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Seleccione Acción");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}

public class CarritoAdapter extends RecyclerView.Adapter<CarritoViewHolder> {

    private List<Orden> ListDato = new ArrayList<>();
    private Context context;


    public CarritoAdapter(List<Orden> listDato, Context context) {
        ListDato = listDato;
        this.context = context;
    }

    @Override
    public CarritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.carrito_layout,parent,false);
        return new CarritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarritoViewHolder holder, int position) {

        TextDrawable drawable=TextDrawable.builder().buildRound(""+ ListDato.get(position).getCantidad(), Color.RED);
        holder.imageCarritoCount.setImageDrawable(drawable);

        Locale locale = new Locale("es","PE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int prec=(Integer.parseInt(ListDato.get(position).getPrecio()))*(Integer.parseInt(ListDato.get(position).getCantidad()));

        holder.txtPrecio.setText(fmt.format(prec));
        holder.txtCarrito_nomb.setText(ListDato.get(position).getProductoNomb());
    }

    @Override
    public int getItemCount() {
        return ListDato.size();
    }
}
