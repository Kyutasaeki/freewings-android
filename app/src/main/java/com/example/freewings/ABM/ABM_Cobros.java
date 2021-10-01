package com.example.freewings.ABM;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freewings.BaseDatos.AdminSQLiteOpenHelper;
import com.example.freewings.Entidades.Curso;
import com.example.freewings.R;

public class ABM_Cobros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abm_cobros);

        admin = new AdminSQLiteOpenHelper(this,"FreeWings",null,null,7);

        btnAgregar = findViewById(R.id.btn_agregar);
        btnModificar = findViewById(R.id.btn_modificar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        btnAgregarAlumno = findViewById(R.id.btn_agregarAlumno);
        btnSacarAlumno = findViewById(R.id.btn_sacarAlumno);

        tNombreCurso = findViewById(R.id.txt_nombreCurso);
        tValorCurso = findViewById(R.id.txt_valorCurso);
        tTituloLista = findViewById(R.id.txt_alumnos);
        tListaAlumnos = findViewById(R.id.ls_alumnosCurso);


        //Limpio all porque cargo de nuevo si es que viene para modificar/eliminar/agregar;
        limpiar();

        //Obtengo el curso pasado por parametro, y me sirve para chequear de que lado vino;
        Bundle b = getIntent().getExtras();

        if(b != null){ //Quiere modificar / Eliminar;

            //Oculto boton de agregar porque va a modificar;
            btnAgregar.setVisibility(View.INVISIBLE);
            btnModificar.setVisibility(View.VISIBLE);
            btnEliminar.setVisibility(View.VISIBLE);

            //Muestro la lista, y los botones para agregar/sacar alumnos;
            tTituloLista.setVisibility(View.VISIBLE);
            tListaAlumnos.setVisibility(View.VISIBLE);
            btnAgregarAlumno.setVisibility(View.VISIBLE);
            btnSacarAlumno.setVisibility(View.VISIBLE);

            //Obtengo del extra, que es el alumno;
            Curso curso = (Curso) b.getSerializable("curso");

            curso_id = curso.getId();

            //Cargo los datos;
            cargarDatos(curso);
            cargarLista(curso);
        }
        else{ //Quiere agregar;
            btnAgregar.setVisibility(View.VISIBLE);
            btnModificar.setVisibility(View.INVISIBLE);
            btnEliminar.setVisibility(View.INVISIBLE);

            //Oculto el agregar/sacar los alumnos;
            tTituloLista.setVisibility(View.INVISIBLE);
            tListaAlumnos.setVisibility(View.INVISIBLE);
            btnAgregarAlumno.setVisibility(View.INVISIBLE);
            btnSacarAlumno.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }
}
