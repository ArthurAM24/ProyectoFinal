package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button btnPlaceOrden, btnCance, btnAcepta;
    EditText edtMesa, nombreCliente;
    List<Pedido> carrito = new ArrayList<>();
    CarritoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("pedidos");

        recyclerView = findViewById(R.id.listaCarrito);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrecio = findViewById(R.id.total);
        btnPlaceOrden = findViewById(R.id.btn_confirm);

        btnPlaceOrden.setOnClickListener(v -> {
            if (!carrito.isEmpty()) {
                MostrarAlerta();
            } else
                Toast.makeText(Carrito.this, "Carrito Vacio!", Toast.LENGTH_SHORT).show();
        });

        CargarListaComidas();
    }

    //metodo Mostrar Alerta
    public void MostrarAlerta() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Carrito.this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_carrito, null);

        builder.setView(view);

        edtMesa = view.findViewById(R.id.dialog_mesa);
        nombreCliente = view.findViewById(R.id.dialog_cliente);

        final AlertDialog alertDialog = builder.create();


        btnAcepta = view.findViewById(R.id.btnAceptarD);
        btnCance = view.findViewById(R.id.btnCancelD);

        btnAcepta.setOnClickListener(v -> {

            Orden request = new Orden(
                    Common.currentUser.getCelular(),
                    Common.currentUser.getNombres(),
                    nombreCliente.getText().toString(),
                    edtMesa.getText().toString(),
                    txtTotalPrecio.getText().toString(),
                    carrito

            );

            //agrega a firebase
            requests.child(String.valueOf(System.currentTimeMillis()))
                    .setValue(request);

            //Eliminar Carrito

            new Database(getBaseContext()).limpíaCarrito();
            Toast.makeText(Carrito.this, "Gracias, su orden ha sido enviada.", Toast.LENGTH_SHORT).show();

        alertDialog.dismiss();
            finish();

        });

        btnCance.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

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
        for (Pedido pedido : carrito)
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

        carrito.remove(position);
        new Database(this).limpíaCarrito();
        for (Pedido item : carrito) {
            new Database(this).agregarAlCarrito(item);
        }
        CargarListaComidas();
    }

}