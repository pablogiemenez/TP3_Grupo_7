package com.example.tp3_grupo_7;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FloatingActionButton fabAddParqueo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}
