package com.example.freewings.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    private final String ALUMNOS = "CREATE TABLE IF NOT EXISTS alumnos(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "dni INTEGER, " +
            "nombre TEXT, " +
            "apellido TEXT," +
            "nacimiento DATETIME," +
            "telefono TEXT," +
            "direccion TEXT," +
            "extra TEXT," +
            "nombrePadre TEXT," +
            "telefonoPadre TEXT)";

    private final String ALUMNOS_CURSOS = "CREATE TABLE IF NOT EXISTS alumnos_cursos(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "alumno_id INTEGER," +
            "curso_id INTEGER)";

    private final String CURSOS = "CREATE TABLE IF NOT EXISTS cursos(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "precio INTEGER)";

    private final String ALUMNOS_COBROS = "CREATE TABLE IF NOT EXISTS alumnos_cobros(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "alumno_id INTEGER" +
            "descripcion TEXT" +
            "precio INTEGER" +
            "estado TEXT" +
            "tipo TEXT" +
            "vencimiento DATETIME" +
            "curso_id INTEGER)";

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, Object o, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALUMNOS);
        db.execSQL(ALUMNOS_CURSOS);
        db.execSQL(CURSOS);
        db.execSQL(ALUMNOS_COBROS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alumnos");
        db.execSQL("DROP TABLE IF EXISTS alumnos_cursos");
        db.execSQL("DROP TABLE IF EXISTS cursos");
        db.execSQL("DROP TABLE IF EXISTS alumnos_cobros");

        db.execSQL(ALUMNOS);
        db.execSQL(ALUMNOS_CURSOS);
        db.execSQL(CURSOS);
        db.execSQL(ALUMNOS_COBROS);
    }

}
