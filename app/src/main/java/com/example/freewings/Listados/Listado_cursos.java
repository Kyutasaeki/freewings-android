package com.example.freewings.Listados;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.freewings.ABM.ABM_Cursos;
import com.example.freewings.BaseDatos.AdminSQLiteOpenHelper;
import com.example.freewings.Entidades.Curso;
import com.example.freewings.R;
import java.util.ArrayList;

public class Listado_cursos extends AppCompatActivity {

    protected AdminSQLiteOpenHelper admin;

    protected ListView listView;

    protected ArrayList<Curso> listaCursos; //Todos los cursos de la db;
    protected ArrayList<String> listaInformacion; //Lineas con informacion para el ListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_cursos);

        admin = new AdminSQLiteOpenHelper(this,"FreeWings",null,null,7);

        listView = findViewById(R.id.ls_cursos);

        crearListado(); //Se encarga de cargar el ListView con los cursos;
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        crearListado(); //Refresca la lista por si se modifico;
    }

    private void crearListado(){
        cargarListas(); //Se cargan las lista de cursos y de informacion;

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listView.setAdapter(adaptador);

        //Le seteo un listener, para que pueda EDITAR el curso, en ABM_Cursos, cuando lo selecciona;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent goToABMCursos = new Intent(Listado_cursos.this, ABM_Cursos.class);
                goToABMCursos.putExtra("curso", listaCursos.get(pos)); //Envio el curso entero, por eso implementa Serializable;
                startActivity(goToABMCursos);
            }
        });
    }

    private void cargarListas(){
        //Se abre la db en forma de lectura;
        SQLiteDatabase db = admin.getReadableDatabase();

        Curso curso;
        String linea;
        //Se inicializan las listas;
        listaCursos  = new ArrayList<Curso>();
        listaInformacion = new ArrayList<String>();

        //Se traen todos los datos de la db;
        Cursor c = db.rawQuery("SELECT * FROM cursos" ,null);

        while(c.moveToNext()){
            //Creo el curso con sus datos y lo inserto en la listaCursos;
            curso = new Curso();
            curso.setId(c.getInt(c.getColumnIndex("id")));
            curso.setNombre(c.getString(c.getColumnIndex("nombre")));
            curso.setPrecio(c.getInt(c.getColumnIndex("precio")));
            listaCursos.add(curso);

            //Creo la linea de informacion con Nombre y Precio del curso, y lo inserto en la listaInformacion;
            linea = curso.getNombre() + " $" + curso.getPrecio();
            listaInformacion.add(linea);
        }

        db.close();
    }

    //Metodo para el boton agregar;
    public void agregarCurso(View view){
        Intent goToADMCursos = new Intent(Listado_cursos.this, ABM_Cursos.class);
        startActivity(goToADMCursos);
    }


}

