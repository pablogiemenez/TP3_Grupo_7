package com.example.tp3_grupo_7;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FloatingActionButton fabAddParqueo;
    private TextView nombreUsuario;
    private TextView correoUsuario;
    private GridView GridParqueos;
    private MenuItem nav_parqueo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences usuarioEnSesion = getSharedPreferences("usuarioEnSesion", Context.MODE_PRIVATE);

        // Configurar la Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parking Control");
        getSupportActionBar().setSubtitle("Parqueos");

        // Configurar el DrawerLayout y el botón de hamburguesa
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configurar el NavigationView (menú lateral)
        NavigationView navigationView = findViewById(R.id.nav_view);

         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @SuppressLint("NonConstantResourceId")
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch(item.getItemId()){
                     case R.id.nav_parqueos:
                            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                         return true;

                     case R.id.nav_mi_cuenta:
                         startActivity(new Intent(HomeActivity.this, activity_mi_cuenta.class));
                         return true;

                     case R.id.nav_cerrar_sesion:
                         startActivity(new Intent(HomeActivity.this, MainActivity.class));
                         return true;

                     default:
                         return false;
                 }

             }
         });

         // Mostrar parqueos
        GridParqueos = findViewById(R.id.gridview_parqueos);
        if(usuarioEnSesion.getString("nombre","") != null){
            mostrarParqueos(usuarioEnSesion.getString("nombre",""));
        }

        // Configurar el usuarioEnSesion en el nav
        View headerView =  navigationView.getHeaderView(0);
        nombreUsuario = headerView.findViewById(R.id.nav_header_name);
        correoUsuario = headerView.findViewById(R.id.nav_header_email);
        nombreUsuario.setText(usuarioEnSesion.getString("nombre", ""));
        correoUsuario.setText(usuarioEnSesion.getString("correo",""));

        // Configurar el FloatingActionButton para agregar parqueo
        fabAddParqueo = findViewById(R.id.fab_add_parqueo);
        fabAddParqueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el Intent para abrir la actividad de registrar parqueo
                Intent intent = new Intent(HomeActivity.this, registro_parqueo_activity.class);
                startActivity(intent);
            }
        });
    }

    // Metodo para mostrar los parqueos del usuario
    private void mostrarParqueos(String username){
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
            SQLiteDatabase basededatos = admin.getReadableDatabase();

            Cursor fila = basededatos.rawQuery("SELECT matricula, tiempo FROM Parqueos WHERE usuarioParqueos = ?", new String[]{username});
            ArrayList<String> parqueosList = new ArrayList<>();
            if (fila.getCount() == 0) {
                Toast.makeText(this, "No se encontraron parqueos", Toast.LENGTH_SHORT).show();
            }

            if (fila.moveToFirst()) {
                do {
                    String matricula = fila.getString(0);
                    int tiempo = fila.getInt(1);

                    parqueosList.add(matricula + " " + tiempo);
                } while (fila.moveToNext());
            }
            fila.close();
            basededatos.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, parqueosList);
            GridParqueos.setAdapter(adapter);
        }
        catch (SQLiteException e) {
            Toast.makeText(this, "Error leyendo la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

}
