package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Registrar extends AppCompatActivity {

    private EditText txtNombre, txtCorreo, txtCelular, txtContra;
    private Button btnRegistra, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);


        txtNombre = findViewById(R.id.txt_nombre);
        txtCorreo = findViewById(R.id.txt_correo);
        txtCelular = findViewById(R.id.txt_celular);
        txtContra = findViewById(R.id.txt_contraseña);

        btnRegistra = findViewById(R.id.btn_registrarse);
        btnCancel = findViewById(R.id.btn_cancelar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("usuarios");

        btnRegistra.setOnClickListener(v -> {
            if (txtCelular.getText().toString().isEmpty()
                    || txtNombre.getText().toString().isEmpty()
                    || txtCorreo.getText().toString().isEmpty()
                    || txtContra.getText().toString().isEmpty()) {

                Toast.makeText(Registrar.this, "Ingrese datos", Toast.LENGTH_SHORT).show();
            } else if (txtCelular.getText().length() > 0 && txtCelular.getText().length() < 9) {
                Toast.makeText(Registrar.this, "Ingrese un Número de celular de 9 Dígitos", Toast.LENGTH_SHORT).show();
            } else if (Common.isConnectedToInternet(getBaseContext())) {

                final ProgressDialog mDialog = new ProgressDialog(Registrar.this);
                mDialog.setMessage("Porfavor espere...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mDialog.dismiss();
                        if (!txtCelular.getText().toString().isEmpty()
                                && !txtNombre.getText().toString().isEmpty()
                                && !txtCorreo.getText().toString().isEmpty()
                                && !txtContra.getText().toString().isEmpty()) {

                            if (snapshot.child(txtCelular.getText().toString()).exists()) {

                                mDialog.dismiss();
                                Toast.makeText(Registrar.this, "El Número de celular ya se encuentra registrado!", Toast.LENGTH_SHORT).show();

                            } else {

                                mDialog.dismiss();
                                Usuario user = new Usuario(txtCorreo.getText().toString(), txtNombre.getText().toString(), txtContra.getText().toString());
                                table_user.child(txtCelular.getText().toString()).setValue(user);
                                Toast.makeText(Registrar.this, "Usuario Registrado con éxito!", Toast.LENGTH_SHORT).show();
                                limpiartext();
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            } else {
                Toast.makeText(Registrar.this, "Porfavor revise su conexión a Internet!", Toast.LENGTH_SHORT).show();

            }


        });

        btnCancel.setOnClickListener(v -> {
            Intent cancl = new Intent(Registrar.this, MainActivity.class);
            startActivity(cancl);
        });

    }

    public void limpiartext() {

        //txt
        txtNombre.setText("");
        txtCorreo.setText("");
        txtCelular.setText("");
        txtContra.setText("");
    }
    public void onStop() {
        super.onStop();
    }
}
