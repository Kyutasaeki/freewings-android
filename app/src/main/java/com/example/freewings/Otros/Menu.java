package com.example.freewings.Otros;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.freewings.Listados.Listado_alumnos;
import com.example.freewings.Listados.Listado_cobros;
import com.example.freewings.Listados.Listado_cursos;
import com.example.freewings.R;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    @Override
    public void onBackPressed() {
        //Implemento el metodo vacio asi no tiene acceso a volver para atras;
    }

    //Va hacia el listado de alumnos;
    public void goToAlumnos(View view){
        Intent siguiente = new Intent(this, Listado_alumnos.class);
        startActivity(siguiente);
    }

    //Va hacia el listado de cursos;
    public void goToCursos(View view){
        Intent siguiente = new Intent(this, Listado_cursos.class);
        startActivity(siguiente);
    }

    //Va hacia el panel administrar;
    public void goToAdministrar(View view){
        Intent siguiente = new Intent(this, Listado_cobros.class);
        startActivity(siguiente);
    }

    //Va hacia el login;
    public void logout(View view){
        Intent siguiente = new Intent(this, Login.class);
        startActivity(siguiente);
    }



}
