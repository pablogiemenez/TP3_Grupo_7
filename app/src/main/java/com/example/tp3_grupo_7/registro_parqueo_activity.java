package com.example.tp3_grupo_7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registro_parqueo_activity extends AppCompatActivity {

    private EditText numeroMatriculaEditText;
    private EditText tiempoEditText;
    private TextView registrarTextView;
    private TextView cancelarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_parqueo);  // Enlaza con el layout activity_registro_parqueo.xml

        // Inicializamos los elementos de la interfaz
        numeroMatriculaEditText = findViewById(R.id.txt_NumeroMatricula);
        tiempoEditText = findViewById(R.id.txt_Tiempo);
        registrarTextView = findViewById(R.id.txt_Registrar);
        cancelarTextView = findViewById(R.id.txt_Cancelar);

        // Listener para el botón "Registrar"
        registrarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarParqueo();
            }
        });

        // Listener para el botón "Cancelar"
        cancelarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Cierra la actividad
            }
        });
    }

    // Método para registrar el parqueo
    private void registrarParqueo() {
        String numeroMatricula = numeroMatriculaEditText.getText().toString();
        String tiempoStr = tiempoEditText.getText().toString();
        SharedPreferences usuario = getSharedPreferences("usuarioEnSesion", Context.MODE_PRIVATE);

        if (numeroMatricula.isEmpty() || tiempoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            int tiempo = Integer.parseInt(tiempoStr);
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
            admin.insertarParqueo(numeroMatricula, tiempo, usuario.getString("nombre",""));

            Toast.makeText(this, "Parqueo registrado: " + numeroMatricula + " por " + tiempo + " horas", Toast.LENGTH_LONG).show();

            finish();  // Cierra la actividad después de registrar
        }
    }
}
