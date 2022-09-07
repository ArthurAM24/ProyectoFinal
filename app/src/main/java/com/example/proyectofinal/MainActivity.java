package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    EditText txtCel;
    EditText txtPassword;
    CheckBox cboRecordarme;

    //PARA AUTENTIFICAR USUARIOS
    private FirebaseAuth mAuth;
    String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCel = findViewById(R.id.txt_cel);
        txtPassword = findViewById(R.id.txt_pass);
        Button btnConfirm = findViewById(R.id.btn_confirm);
        Button btnRegistra = findViewById(R.id.btn_registrarse);
        cboRecordarme = findViewById(R.id.cboRecordar);

        //inicia paper
        Paper.init(this);

        //iniciar autentificación
        mAuth = FirebaseAuth.getInstance();
        //iniciar firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("usuarios");


        btnConfirm.setOnClickListener(v -> {
            if (!txtCel.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
                if (Common.isConnectedToInternet(getBaseContext())) {


                    ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                    mDialog.setMessage("Porfavor espere...");
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
                                    //De boton recordarme
                                    if (cboRecordarme.isChecked()) {
                                        Paper.book().write(Common.USER_K, txtCel.getText().toString());
                                        Paper.book().write(Common.PASS_K, txtPassword.getText().toString());
                                    }

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
        //check if remembered

        String user = Paper.book().read(Common.USER_K);
        String pass = Paper.book().read(Common.PASS_K);

        if(user != null && pass != null)
        {
            if(!user.isEmpty() && !pass.isEmpty())
                loging(user,pass);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        txtCel.requestFocus();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            //
        } else {
            signInAnonymously();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    private void signInAnonymously() {

        mAuth.signInAnonymously().addOnSuccessListener(this, authResult -> {
                    //
                    Log.d(TAG, "signInWithCustomToken:success");
                })
                .addOnFailureListener(this, exception -> Log.e(TAG, "signInAnonymously:FAILURE", exception));
    }
    private void loging(final String phone, final String pwd) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("usuarios");

        if (Common.isConnectedToInternet(getBaseContext())) {

            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Porfavor espere...");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //Check si user existe en la BD
                    if (dataSnapshot.child(phone).exists()) {

                        //get Usuario
                        mDialog.dismiss();
                        Usuario user = dataSnapshot.child(phone).getValue(Usuario.class);

                        //set phone number
                        user.setCelular(phone);

                        if (user.getPassword().equals(pwd)) {
                            Toast.makeText(MainActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(MainActivity.this, Inicio.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Usuario no existe!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(MainActivity.this,"Porfavor revise su conexión a Internet!",Toast.LENGTH_SHORT).show();
            return;
        }
    }
}