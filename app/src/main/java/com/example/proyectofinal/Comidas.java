package com.example.proyectofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Modelo.Comida;
import com.example.proyectofinal.View_Holder.ComidaViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Interface.ItemClickListener;

public class Comidas extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference comidaList;
    String categoriaID="";
    FirebaseRecyclerAdapter<Comida,ComidaViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comidas);

        //FIREBASE
        database = FirebaseDatabase.getInstance();
        comidaList=database.getReference("Comidas");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //OBTIENE EL INTENT
        if (getIntent() !=null){
           categoriaID = getIntent().getStringExtra("CategoriaID");

        }if (!categoriaID.isEmpty() && categoriaID !=null){
            cargarListaComidas(categoriaID);
        }

    }

    private void cargarListaComidas(String categoriaID) {
        adapter= new FirebaseRecyclerAdapter<Comida, ComidaViewHolder>(Comida.class,R.layout.comidas_item,ComidaViewHolder.class,
                comidaList.orderByChild("MenuID").equalTo(categoriaID)) {
            @Override
            protected void populateViewHolder(ComidaViewHolder comidaViewHolder, Comida comida, int i) {
            comidaViewHolder.Comida_Nombre.setText(comida.getNombre());
                Picasso.with(getBaseContext()).load(comida.getImage()).into(comidaViewHolder.Comida_Imagen);

                final Comida local=comida;
                comidaViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(Comidas.this,""+local.getNombre(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}