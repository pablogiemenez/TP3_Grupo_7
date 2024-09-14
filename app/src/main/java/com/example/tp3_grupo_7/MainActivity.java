package com.example.tp3_grupo_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Campos para los elementos de UI
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignar los elementos de la UI a variables
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);  // Variable local
        TextView registrate= findViewById(R.id.registerTextView);
        registrate.setOnClickListener(v->{
            Intent i=new Intent(this,registro_activity.class);
            startActivity(i);
        });
        // Configurar la acción del botón de inicio de sesión
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Validar que los campos no estén vacíos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí iría la lógica para validar el inicio de sesión con la base de datos
                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
