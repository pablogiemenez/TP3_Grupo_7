package com.example.tp3_grupo_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);  // Variable local
        TextView registrate = findViewById(R.id.registerTextView);

        registrate.setOnClickListener(v -> {
            Intent i = new Intent(this, registro_activity.class);
            startActivity(i);
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
            } else {
                if (iniciarSesion(username, password)) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    SharedPreferences usuario = getSharedPreferences("usuarioEnSesion", Context.MODE_PRIVATE);
                    SharedPreferences.Editor obj_editor = usuario.edit();
                    obj_editor.putString("correo" ,getCorreoUsuario(username));
                    obj_editor.putString("nombre", username);
                    obj_editor.commit();

                    startActivity(intent);
                }
            }
        });
    }

    public Boolean iniciarSesion(String username, String password) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
        SQLiteDatabase basededatos = admin.getReadableDatabase();

        Cursor fila = basededatos.rawQuery("SELECT contrasenia FROM Usuarios WHERE nombre = ?", new String[]{username});


        if (fila.moveToFirst()) {
            String storedPassword = fila.getString(0);


            if (storedPassword.equals(password)) {
                fila.close();
                basededatos.close();
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }

        fila.close();
        basededatos.close();
        return false;
    }

    public String getCorreoUsuario (String username){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
        SQLiteDatabase basededatos = admin.getReadableDatabase();

        Cursor fila = basededatos.rawQuery("SELECT correo FROM Usuarios WHERE nombre = ?", new String[]{username});

        if(fila.moveToFirst()){
            String correoUsuario = fila.getString(0);
            return correoUsuario;
        }
        return null;
    }

}
