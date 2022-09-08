package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.example.proyectofinal.Modelo.MyResponse;
import com.example.proyectofinal.Modelo.Notification;
import com.example.proyectofinal.Modelo.Orden;
import com.example.proyectofinal.Modelo.Pedido;
import com.example.proyectofinal.Modelo.Sender;
import com.example.proyectofinal.Modelo.Token;
import com.example.proyectofinal.Remote.APIService;
import com.example.proyectofinal.View_Holder.CarritoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        //Iniciar service
        mService = Common.getFCMService();

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

    public void onStop() {
        super.onStop();
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

        alertDialog.show();

        btnAcepta.setOnClickListener(v -> {
            if (!edtMesa.getText().toString().isEmpty() && !nombreCliente.getText().toString().isEmpty()) {
                Orden request = new Orden(
                        Common.currentUser.getCelular(),
                        Common.currentUser.getNombres(),
                        nombreCliente.getText().toString(),
                        edtMesa.getText().toString(),
                        txtTotalPrecio.getText().toString(),
                        carrito

                );
                String orden_num = String.valueOf(System.currentTimeMillis());

                //agrega a firebase
                requests.child(orden_num)
                        .setValue(request);

                enviaNotificacionOrden(orden_num);
                //Eliminar Carrito
                new Database(getBaseContext()).limpíaCarrito(Common.currentUser.getCelular());
                alertDialog.dismiss();

            } else {
                Toast.makeText(Carrito.this, "Ingrese datos", Toast.LENGTH_SHORT).show();
            }
        });

        btnCance.setOnClickListener(v -> alertDialog.dismiss());

    }

    private void enviaNotificacionOrden(String orden_num) {

        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query data = tokens.orderByChild("servertoken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Token serverToken = postSnapshot.getValue(Token.class);

                    Notification notificacion = new Notification("PideAltoque", "Tienes una nueva Orden Pendiente: " + orden_num);
                    Sender content = new Sender(serverToken.getToken(), notificacion);
                    mService.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200) {
                                        if (response.body().success == 1) {
                                            Toast.makeText(Carrito.this, "Gracias, su orden ha sido enviada.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(Carrito.this, "Error!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // }


    }

    @SuppressLint("NotifyDataSetChanged")
    private void CargarListaComidas() {
        carrito = new Database(this).getCarrito(Common.currentUser.getCelular());
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
            EliminarCarrito(item.getOrder());
        return true;
    }

    private void EliminarCarrito(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titulo);
        builder.setMessage(R.string.cuerpo);
        builder.setPositiveButton("SI", (dialog, i) -> {

            carrito.remove(position);
            new Database(this).limpíaCarrito(Common.currentUser.getCelular());
            for (Pedido item : carrito) {
                new Database(this).agregarAlCarrito(item);
            }
            CargarListaComidas();
            Toast.makeText(this, "Carrito Eliminado!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("NO", (dialog, i) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();


    }

}