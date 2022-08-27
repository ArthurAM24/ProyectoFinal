package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Database.Database;
import com.example.proyectofinal.Modelo.Comida;
import com.example.proyectofinal.Modelo.Pedido;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ComidaDetalle extends AppCompatActivity {

    TextView comida_nombre, comida_precio, comida_descripcion;
    ImageView comida_imagen;

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCarrito;
    TextView Cantidad;
    int contador = 1;

    String comidaID = "";

    FirebaseDatabase database;
    DatabaseReference comidas;

    Comida comidaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_detalle);

        //BUTTONS
        Cantidad = findViewById(R.id.textViewNumber);

        //FIREBASE
        database = FirebaseDatabase.getInstance();
        comidas = database.getReference("comidas");

        //INICIO VIEW

        btnCarrito = findViewById(R.id.btnCart);
        btnCarrito.setOnClickListener(v -> {

            new Database(getBaseContext()).agregarAlCarrito(new Pedido(
                    comidaID,
                    comidaActual.getNombre(),
                    Cantidad.getText().toString(),
                    comidaActual.getPrecio(),
                    comidaActual.getDescuento()

            ));
            Toast.makeText(ComidaDetalle.this, "Agregado Al carrito", Toast.LENGTH_SHORT).show();
        });


        comida_descripcion = findViewById(R.id.comida_descripcion);
        comida_nombre =  findViewById(R.id.comida_name);
        comida_precio =  findViewById(R.id.comida_precio);
        comida_imagen = findViewById(R.id.img_comida);


        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollaspedAppbar);

        //obtener comida
        if (getIntent() != null)
            comidaID = getIntent().getStringExtra("ComidasId");
        if (!comidaID.isEmpty()) {
            if (Common.isConnectedToInternet(getBaseContext()))
                getDetalleComida(comidaID);
            else {
                Toast.makeText(ComidaDetalle.this, "Porfavor revise su conexion!", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @SuppressLint("SetTextI18n")
    public void incrementar(View view) {
        contador++;
        Cantidad.setText("" + contador);
    }

    @SuppressLint("SetTextI18n")
    public void decrementar(View view) {
        if (contador <= 1) {
            contador = 1;
        } else {
            contador--;
            Cantidad.setText("" + contador);
        }

    }

    private void getDetalleComida(String comiId) {
        comidas.child(comiId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comidaActual = dataSnapshot.getValue(Comida.class);

                //set image
                Picasso.with(getBaseContext()).load(comidaActual.getImage())
                        .into(comida_imagen);

                collapsingToolbarLayout.setTitle(comidaActual.getNombre());
                comida_precio.setText(comidaActual.getPrecio());
                comida_nombre.setText(comidaActual.getNombre());
                comida_descripcion.setText(comidaActual.getDescripcion());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}