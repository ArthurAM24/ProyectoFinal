package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {


    private EditText txtCel;
    private EditText txtPassword;

    private Button btnLogin;
    private Button btnRegistra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCel = (EditText) findViewById(R.id.txt_cel);
        txtPassword = (EditText) findViewById(R.id.txt_pass);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegistra = (Button) findViewById(R.id.btn_registrarse);


        //iniciar firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("usuarios");

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

                                    String usert = "false";

                                    if (user.getPassword().equals(txtPassword.getText().toString())) {
                                        Toast.makeText(MainActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();

                                        Intent homeIntent = new Intent(MainActivity.this, Inicio.class);
                                        Common.currentUser=user;
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
                        Toast.makeText(MainActivity.this, "Revise su conexion!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Ingrese datos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registra= new Intent(MainActivity.this,Registrar.class);
                startActivity(registra);
            }
        });


    }
}