package com.example.proyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Categoria;
import com.example.proyectofinal.Modelo.Token;
import com.example.proyectofinal.Modelo.Usuario;
import com.example.proyectofinal.View_Holder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Categoria, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menú");
        setSupportActionBar(toolbar);

        //iniciar firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("categoria");

        Paper.init(this);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            Intent carritoInten = new Intent(Inicio.this, Carrito.class);
            startActivity(carritoInten);
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Nombre de usuario
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txt_Fullnombre);
        txtFullName.setText(Common.currentUser.getNombres());

        //Cargar menu
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        if (Common.isConnectedToInternet(this)) {
            loadMenu();
        } else {
            Toast.makeText(this, "Porfavor revise su conexión a Internet!", Toast.LENGTH_SHORT).show();
        }

        actualizaToken(FirebaseInstanceId.getInstance().getToken());
    }
    public void onStop() {
        super.onStop();
    }
    private void actualizaToken(String token) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token data = new Token(token, false);
        tokens.child(Common.currentUser.getCelular()).setValue(data);

    }

    private void loadMenu() {

        adapter =
                new FirebaseRecyclerAdapter<Categoria, MenuViewHolder>(Categoria.class, R.layout.menu_item, MenuViewHolder.class, category) {
                    @Override
                    protected void populateViewHolder(MenuViewHolder menuViewHolder, Categoria model, int i) {
                        menuViewHolder.Menu_Nombre.setText(model.getNombre());

                        Picasso.with(getBaseContext()).load(model.getImage())
                                .into(menuViewHolder.Menu_Imagen);

                        final Categoria clickItem = model;

                        menuViewHolder.setItemClickListener((view, position, isLongClick) -> {
                            Toast.makeText(Inicio.this, "" + clickItem.getNombre(), Toast.LENGTH_SHORT).show();
                            //Obtiene categoria y lo ennvia a la otra actividad

                            Intent comidaLista = new Intent(Inicio.this, Comidas.class);
                            comidaLista.putExtra("CategoriaID", adapter.getRef(position).getKey());
                            startActivity(comidaLista);
                        });
                    }
                };
        recycler_menu.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh)
            loadMenu();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_cart) {

            Intent cartIntent = new Intent(Inicio.this, Carrito.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_oders) {
            Intent OrdIntent = new Intent(Inicio.this, EstadoDeOrden.class);
            startActivity(OrdIntent);

        } else if (id == R.id.nav_log_out) {
            //Delete remember
            Paper.book().destroy();
            //Logout
            Intent signIn = new Intent(Inicio.this, MainActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
