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

import com.example.freewings.ABM.ABM_Cobros;
import com.example.freewings.ABM.ABM_Cursos;
import com.example.freewings.BaseDatos.AdminSQLiteOpenHelper;
import com.example.freewings.Entidades.Cobro;
import com.example.freewings.Entidades.Curso;
import com.example.freewings.R;
import java.util.ArrayList;

public class Listado_cobros extends AppCompatActivity {

    protected AdminSQLiteOpenHelper admin;

    protected ListView listView;

    protected ArrayList<Cobro> listaCobros; //Todos los cobros de la db;
    protected ArrayList<String> listaInformacion; //Lineas con informacion para el ListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_cobros);

        admin = new AdminSQLiteOpenHelper(this,"FreeWings",null,null,7);

        listView = findViewById(R.id.ls_cobros);

        crearListado(); //Se encarga de cargar el ListView con los cobros;
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        crearListado(); //Refresca la lista por si se modifico;
    }

    private void crearListado(){
        cargarListas(); //Se cargan las lista de cobros y de informacion;

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listView.setAdapter(adaptador);

        //Le seteo un listener, para que pueda EDITAR el curso, en ABM_Cursos, cuando lo selecciona;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent goToABMCobro = new Intent(Listado_cobros.this, ABM_Cobros.class);
                goToABMCobro.putExtra("cobro", listaCobros.get(pos)); //Envio el curso entero, por eso implementa Serializable;
                startActivity(goToABMCobro);
            }
        });
    }

    private void cargarListas(){
        //Se abre la db en forma de lectura;
        SQLiteDatabase db = admin.getReadableDatabase();

        Cobro cobro;
        String linea;
        String nombreAlumno;
        String apellidoAlumno;
        String nombreCurso;
        //Se inicializan las listas;
        listaCobros  = new ArrayList<Cobro>();
        listaInformacion = new ArrayList<String>();

        //Se traen todos los datos de la db;
        Cursor c = db.rawQuery("SELECT alumnos_cursos.*, alumnos.nombre, alumnos.apellido, cursos.nombre, cursos.precio FROM alumnos_cobros" +
                " INNER JOIN alumnos ON alumnos.id = alumnos_cobros.alumno_id" +
                " INNER JOIN cursos ON cursos.id = alumnos_cobros.alumno_id" ,null);

        while(c.moveToNext()){
            //Creo el cobro con sus datos y lo inserto en la listaCobros;
            cobro = new Cobro();
            cobro.setId(c.getInt(c.getColumnIndex("id")));
            cobro.setAlumno_id(c.getInt(c.getColumnIndex("alumno_id")));
            cobro.setCurso_id(c.getInt(c.getColumnIndex("curso_id")));
            cobro.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
            cobro.setPrecio(c.getInt(c.getColumnIndex("precio")));
            cobro.setEstado(c.getString(c.getColumnIndex("estado")));
            cobro.setTipo(c.getString(c.getColumnIndex("tipo")));
            cobro.setVencimiento(c.getString(c.getColumnIndex("vencimiento")));
            listaCobros.add(cobro);

            nombreAlumno = c.getString(c.getColumnIndex("nombre"));
            apellidoAlumno = c.getString(c.getColumnIndex("nombre"));
            nombreCurso = c.getString(c.getColumnIndex("nombre")); //Ver para apuntar a curso;

            //Creo la linea de informacion y lo inserto en la listaInformacion;
            linea =  nombreAlumno + " " + apellidoAlumno + " - " + cobro.getVencimiento() + " " + " $" + cobro.getPrecio();
            listaInformacion.add(linea);
        }

        db.close();
    }

    //Metodo para el boton agregar;
    public void crearCobro(View view){
        Intent goToADMCobros = new Intent(Listado_cobros.this, ABM_Cobros.class);
        startActivity(goToADMCobros);
    }


}

