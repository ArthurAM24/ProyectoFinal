package com.example.proyectofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Comida;
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
    TextView valor;
    int contador = 0;

    String comidaID = "";
    FirebaseDatabase database;
    DatabaseReference comidas;

    Comida comidaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_detalle);

        //BUTTONS

        valor = (TextView) findViewById(R.id.textViewNumber);

        //FIREBASE
        database = FirebaseDatabase.getInstance();
        comidas = database.getReference("Comidas");

        //INICIO VIEW

        btnCarrito = (FloatingActionButton) findViewById(R.id.btnCart);
        comida_descripcion = (TextView) findViewById(R.id.comida_descripcion);
        comida_nombre = (TextView) findViewById(R.id.comida_name);
        comida_precio = (TextView) findViewById(R.id.comida_precio);
        comida_imagen = (ImageView) findViewById(R.id.img_comida);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollaspedAppbar);

        //get comida
        if (getIntent() != null)
            comidaID = getIntent().getStringExtra("ComidasId");
        if (!comidaID.isEmpty()) {
            if (Common.isConnectedToInternet(getBaseContext()))
                getDetalleComida(comidaID);
            else {
                Toast.makeText(ComidaDetalle.this, "Porfavor revise su conexion!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    public void incrementar(View view) {
        contador++;
        valor.setText(""+ contador);

    }

    public void decrementar(View view) {
        if (contador <= 0) {
            contador = 0;
        } else {
            contador--;
            valor.setText("" + contador);
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