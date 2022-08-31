package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText txtCel;
    private EditText txtPassword;

    //PARA AUTENTIFICAR USUARIOS
    private FirebaseAuth mAuth;
    String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCel = findViewById(R.id.txt_cel);
        txtPassword = findViewById(R.id.txt_pass);
        Button btnLogin = findViewById(R.id.btn_confirm);

        Button btnRegistra = findViewById(R.id.btn_registrarse);
        //iniciar autentificación
        mAuth = FirebaseAuth.getInstance();
        //iniciar firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("usuarios");



        btnLogin.setOnClickListener(v -> {
            if (!txtCel.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                    mDialog.setMessage("Porfavor espere");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //Si usuario no existe
                            if (snapshot.child(txtCel.getText().toString()).exists()) {

                                //Obtiene usuario
                                mDialog.dismiss();
                                Usuario user = snapshot.child(txtCel.getText().toString()).getValue(Usuario.class);
                                user.setCelular(txtCel.getText().toString()); //SET celular


                                if (user.getPassword().equals(txtPassword.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent = new Intent(MainActivity.this, Inicio.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Contraseña Incorrecta!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Usuario no existe!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Revise su conexion a Internet!", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(MainActivity.this, "Ingrese datos", Toast.LENGTH_SHORT).show();
            }
        });


        btnRegistra.setOnClickListener(v -> {
            Intent registra = new Intent(MainActivity.this, Registrar.class);
            startActivity(registra);
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        txtCel.requestFocus();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously();
        }
    }

    private void signInAnonymously() {

        mAuth.signInAnonymously().addOnSuccessListener(this, authResult -> {
                    // do your stuff
                    Log.d(TAG, "signInWithCustomToken:success");
                })
                .addOnFailureListener(this, exception -> Log.e(TAG, "signInAnonymously:FAILURE", exception));
    }
}