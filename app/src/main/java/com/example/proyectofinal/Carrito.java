package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Database.Database;
import com.example.proyectofinal.Modelo.Orden;
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
    DatabaseReference requests;

    TextView txtTotalPrecio;
    Button btnPlaceOrden;

    List<Orden> carrito = new ArrayList<>();
    CarritoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Pedidos");

        recyclerView = findViewById(R.id.listaCarrito);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrecio = findViewById(R.id.total);
        btnPlaceOrden = findViewById(R.id.btn_confirm);

        btnPlaceOrden.setOnClickListener(v -> {
            if (!carrito.isEmpty())

                MostrarAlerta();
            else
                Toast.makeText(Carrito.this, "Carrito Vacio!", Toast.LENGTH_SHORT).show();
        });

        CargarListaComidas();
    }

    //metodo Mostrar Alerta
    private void MostrarAlerta() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Carrito.this);
        alertDialog.setTitle("PideAltoq");
        alertDialog.setMessage("Ingrese Número de Mesa:");

        final EditText edtMesa = new EditText(Carrito.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtMesa.setLayoutParams(lp);
        alertDialog.setView(edtMesa);
        //alertDialog.setView(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("SI", (dialog, which) -> {
            Pedido request = new Pedido(
                    Common.currentUser.getCelular(),
                    Common.currentUser.getNombres(),
                    edtMesa.getText().toString(),
                    txtTotalPrecio.getText().toString(),
                    carrito
            );

            //Add to Firebase+
            requests.child(String.valueOf(System.currentTimeMillis()))
                    .setValue(request);

            //Eliminar Carrito
            new Database(getBaseContext()).limpíaCarrito();
            Toast.makeText(Carrito.this, "Gracias, su orden ha sido enviada.", Toast.LENGTH_SHORT).show();
            finish();
        });

        alertDialog.setNegativeButton("NO", (dialogInterface, which) -> dialogInterface.dismiss());
        alertDialog.show();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void CargarListaComidas() {
        carrito = new Database(this).getCarrito();
        adapter = new CarritoAdapter(carrito, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        //CALCULA EL PRECIO

        int total = 0;
        for (Orden pedido : carrito)
            total += (Integer.parseInt(pedido.getPrecio())) * (Integer.parseInt(pedido.getCantidad()));

        Locale locale = new Locale("es", "PE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrecio.setText(fmt.format(total));

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        //delete by position from List<Order>
        carrito.remove(position);
        new Database(this).limpíaCarrito();
        for (Orden item : carrito) {
            new Database(this).agregarAlCarrito(item);
        }
        CargarListaComidas();
    }

}