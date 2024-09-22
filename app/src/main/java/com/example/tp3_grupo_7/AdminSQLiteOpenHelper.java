package com.example.tp3_grupo_7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("CREATE TABLE Usuarios (" + "nombre TEXT, " + "correo TEXT UNIQUE, " + "contrasenia TEXT)");

        BaseDeDatos.execSQL("CREATE TABLE Parqueos (matricula TEXT, tiempo INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int i, int i1) {
    }

    public void insertarParqueo(String matricula, int tiempo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Parqueos (matricula, tiempo) VALUES ('" + matricula + "', " + tiempo + ")");
        db.close();
    }
}
