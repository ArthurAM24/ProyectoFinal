package com.example.proyectofinal;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Orden;
import com.example.proyectofinal.View_Holder.OrdenViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Interface.ItemClickListener;

public class EstadoDeOrden extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Orden, OrdenViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_de_orden);

        //Inicia el firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("pedidos");

        recyclerView = findViewById(R.id.ListarOrden);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent().getExtras() == null)
            loadOrders(Common.currentUser.getCelular());
        else
            loadOrders(getIntent().getStringExtra("celular"));
    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Orden, OrdenViewHolder>(
                Orden.class,
                R.layout.orden_layout,
                OrdenViewHolder.class,
                requests.orderByChild("celular")
                        .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrdenViewHolder ordenViewHolder, Orden orden, int i) {
                ordenViewHolder.txtOrdenID.setText(adapter.getRef(i).getKey());
                ordenViewHolder.txtOrdenEstado.setText(Common.convertCodeToStatus(orden.getEstado()));
                ordenViewHolder.txtNombre.setText(orden.getNombre());
                ordenViewHolder.txtMesa.setText("Número de Mesa: " + orden.getNroMesa());

                ordenViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }


        };

        recyclerView.setAdapter(adapter);
    }


}