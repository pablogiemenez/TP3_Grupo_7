package com.example.tp3_grupo_7;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_mi_cuenta extends AppCompatActivity {

    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);

        // Mostrar los datos del usuario en la vista
        SharedPreferences usuarioEnSesion = getSharedPreferences("usuarioEnSesion", Context.MODE_PRIVATE);
        TextView txtNombre = findViewById(R.id.txt_nombre);
        TextView txtMail = findViewById(R.id.txt_correo);

        String CorreoUsuarioSesion = usuarioEnSesion.getString("correo", "");
        String NombreUsuarioSesion = usuarioEnSesion.getString("nombre", "");

        if (!CorreoUsuarioSesion.isEmpty() && !NombreUsuarioSesion.isEmpty()) {
            txtNombre.setText(NombreUsuarioSesion);
            txtMail.setText(CorreoUsuarioSesion);
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

        // Configurar el botón de cancelar
        btnCancelar = findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Cierra la actividad
            }
        });
    }
}
