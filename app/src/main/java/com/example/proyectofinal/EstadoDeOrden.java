package com.example.proyectofinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Orden;
import com.example.proyectofinal.View_Holder.OrdenViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                ordenViewHolder.txtOrdenID.setText(String.format("Orden ID: %s",adapter.getRef(i).getKey()));
                ordenViewHolder.txtOrdenEstado.setText(String.format("Estado: %s",Common.convertCodeToStatus(orden.getEstado())));
                ordenViewHolder.txtNombre.setText(String.format("Mesero: %s",orden.getNombre()));
                ordenViewHolder.txtMesa.setText(String.format("Número de Mesa: %s",orden.getNroMesa()));
                ordenViewHolder.txtCliente.setText(String.format("Cliente: %s",orden.getCliente()));

                ordenViewHolder.setItemClickListener((view, position, isLongClick) -> {

                });
            }

        };

        recyclerView.setAdapter(adapter);
    }
    public void onStop() {
        super.onStop();
    }

}