package com.example.tp3_grupo_7;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class activity_mi_cuenta extends AppCompatActivity {
    private NavigationView nav;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences usuarioEnSesion = getSharedPreferences("usuarioEnSesion", Context.MODE_PRIVATE);
        // Configurar la Toolbar


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mi_cuenta);
        TextView txtNombre = findViewById(R.id.txt_nombre);
        TextView txtMail = findViewById(R.id.txt_correo);
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();
        String CorreoUsuarioSesion;
        CorreoUsuarioSesion = usuarioEnSesion.getString("correo","");
        String NombreUsuarioSesion= usuarioEnSesion.getString("nombre","");

        if(!CorreoUsuarioSesion.isEmpty()&&!NombreUsuarioSesion.isEmpty()){

            txtNombre.setText(NombreUsuarioSesion);
            txtMail.setText(CorreoUsuarioSesion);


        }else {
            Toast.makeText(this,"usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
        nav=findViewById(R.id.nv_mi_cuenta);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.nav_parqueos){
                    startActivity(new Intent(activity_mi_cuenta.this, HomeActivity.class));
                    return true;
                }
                if(id==R.id.nav_mi_cuenta){
                    startActivity(new Intent(activity_mi_cuenta.this,activity_mi_cuenta.class));
                    return true;
                }
                if(id==R.id.nav_cerrar_sesion){
                    startActivity(new Intent(activity_mi_cuenta.this, MainActivity.class));
                    return true;
                }
                return false;

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}