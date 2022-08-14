package com.example.proyectofinal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Database.Database;
import com.example.proyectofinal.Modelo.Pedido;
import com.example.proyectofinal.View_Holder.CarritoAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Carrito extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotalPrecio;
    Button btnPlaceOrden;

    List<Pedido> carrito = new ArrayList<>();
    CarritoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

      /*  database = FirebaseDatabase.getInstance();
        request = database.getReference("Pedidos");
*/
        recyclerView = findViewById(R.id.listaCarrito);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrecio = findViewById(R.id.total);
        btnPlaceOrden = findViewById(R.id.btn_confirm);

        btnPlaceOrden.setOnClickListener(v -> {
            if(!carrito.isEmpty())
                MostgrarAlerta();
            else
                Toast.makeText(Carrito.this,"Carrito Vacio!",Toast.LENGTH_SHORT).show();
        });

       CargarListaComidas();
    }
    private void MostgrarAlerta() {

    }
    private void CargarListaComidas() {
        carrito = new Database(this).getCarrito();
        adapter = new CarritoAdapter(carrito, this);
        recyclerView.setAdapter(adapter);
        //CALCULA EL PRECIO

        int total = 0;
        for (Pedido pedido:carrito)
            total += (Integer.parseInt(pedido.getPrecio()))*(Integer.parseInt(pedido.getCantidad()));

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrecio.setText(fmt.format(total));
    }


}