package com.example.tp3_grupo_7;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registro_activity extends AppCompatActivity {
    private EditText et_nombre, et_correo_electronico, et_contrasenia, et_contrasenia2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);


        et_nombre = findViewById(R.id.txt_nombre);
        et_correo_electronico = findViewById(R.id.txt_correo_electronico);
        et_contrasenia = findViewById(R.id.txt_contrasenia);
        et_contrasenia2 = findViewById(R.id.txt_repetir_contrasenia);
        Button btnRegistro = findViewById(R.id.btn_register);


        btnRegistro.setOnClickListener(v -> {
            String nombre = et_nombre.getText().toString();
            String mail = et_correo_electronico.getText().toString();
            String contrasenia = et_contrasenia.getText().toString();
            String contrasenia2 = et_contrasenia2.getText().toString();

            if (!nombre.isEmpty() && !mail.isEmpty() && !contrasenia.isEmpty() && !contrasenia2.isEmpty()) {
                if (contrasenia2.equals(contrasenia)) {
                    if (!correoYaRegistrado(mail) && !nombreYaRegistrado(nombre)) {
                        agregarUsuario(nombre, mail, contrasenia);
                        Toast.makeText(this, "Usuario agregado", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    } else {
                        if (correoYaRegistrado(mail)) {
                            Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "El nombre de usuario ya está registrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void agregarUsuario(String nombre, String correo, String contrasenia) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("nombre", nombre);
        registro.put("correo", correo);
        registro.put("contrasenia", contrasenia);

        BaseDeDatos.insert("Usuarios", null, registro);
        BaseDeDatos.close();
        limpiarControlesDeTexto();
    }

    private void limpiarControlesDeTexto() {
        et_nombre.setText("");
        et_correo_electronico.setText("");
        et_contrasenia.setText("");
        et_contrasenia2.setText("");
    }

    private boolean correoYaRegistrado(String correo) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("SELECT correo FROM Usuarios WHERE correo = ?", new String[]{correo});

        boolean existe = fila.moveToFirst();

        fila.close();
        BaseDeDatos.close();

        return existe;
    }

    private boolean nombreYaRegistrado(String nombre) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ParkingControl", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("SELECT nombre FROM Usuarios WHERE nombre = ?", new String[]{nombre});

        boolean existe = fila.moveToFirst();

        fila.close();
        BaseDeDatos.close();

        return existe;
    }
}