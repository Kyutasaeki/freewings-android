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
import com.example.freewings.ABM.ABM_Alumnos;
import com.example.freewings.BaseDatos.AdminSQLiteOpenHelper;
import com.example.freewings.Entidades.Alumno;
import com.example.freewings.R;
import java.util.ArrayList;

public class Listado_alumnos extends AppCompatActivity {

    protected ListView listView;
    protected AdminSQLiteOpenHelper admin;

    //Para el ordenamiento de distintas formas;
    private String ordenar;
    private final String NOMBRE = "UPPER(nombre)"; //Upper para que compare todo en mayuscula;
    private final String APELLIDO = "UPPER(apellido)";
    private final String NACIMIENTO = "(substr(nacimiento, 7, 4) || '-' || substr(nacimiento, 4, 2) || '-' || substr(nacimiento, 1, 2)) DESC";

    protected ArrayList<Alumno> listaAlumno; //Todos los alumnos de la db;
    protected ArrayList<String> listaInformacion; //Lineas con informacion para el ListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_alumnos);

        admin = new AdminSQLiteOpenHelper(this,"FreeWings",null,null,7);

        listView = findViewById(R.id.ls_alumnos);

        ordenar = APELLIDO;  // Por defecto el orden es por apellido;
        crearListado(); //Se encarga de cargar el ListView con los alumnos;
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        crearListado(); //Refresca la lista por si se modifico;
    }

    private void crearListado(){
        cargarListas(); //Se cargan las lista de alumnos y de informacion;

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listView.setAdapter(adaptador);

        //Le seteo un listener, para que pueda EDITAR al alumno, en administracion, cuando lo selecciona;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent goToAdministrar = new Intent(Listado_alumnos.this, ABM_Alumnos.class);
                goToAdministrar.putExtra("alumno", listaAlumno.get(pos)); //Envio el alumno entero, por eso implementa Serializable;
                startActivity(goToAdministrar);
            }
        });
    }

    private void cargarListas(){
        //Se abre la db en forma de lectura;
        SQLiteDatabase db = admin.getReadableDatabase();

        Alumno alumno;
        String linea;
        //Se inicializan las listas;
        listaAlumno  = new ArrayList<Alumno>();
        listaInformacion = new ArrayList<String>();

        //Se traen todos los datos de la db;
        Cursor c = db.rawQuery("SELECT * FROM alumnos ORDER BY " + ordenar ,null);

        while(c.moveToNext()){
            //Creo el alumno con sus datos y lo inserto en la listaAlumnos;
            alumno = new Alumno();
            alumno.setDni(c.getInt(c.getColumnIndex("dni")));
            alumno.setNombre(c.getString(c.getColumnIndex("nombre")));
            alumno.setApellido(c.getString(c.getColumnIndex("apellido")));
            alumno.setNacimiento(c.getString(c.getColumnIndex("nacimiento")));
            alumno.setDireccion(c.getString(c.getColumnIndex("direccion")));
            alumno.setTelefono(c.getString(c.getColumnIndex("telefono")));
            alumno.setExtra(c.getString(c.getColumnIndex("extra")));
            alumno.setNombrePadre(c.getString(c.getColumnIndex("nombrePadre")));
            alumno.setTelefonoPadre(c.getString(c.getColumnIndex("telefonoPadre")));
            listaAlumno.add(alumno);

            //Creo la linea de informacion con Nombre y Apellido, y lo inserto en la listaInformacion;
            linea = alumno.getNombre() + " " + alumno.getApellido();
            listaInformacion.add(linea);
        }

        db.close();
    }

    public void agregarAlumno(View view){
        Intent goToABMAlumnos = new Intent(Listado_alumnos.this, ABM_Alumnos.class);
        startActivity(goToABMAlumnos);
    }

    /* Metodos para los botones, para ordenar de distintas maneras; */

    public void ordenarApellido(View view){
        ordenar = APELLIDO;
        crearListado();
    }

    public void ordenarNombre(View view){
        ordenar = NOMBRE;
        crearListado();
    }

    public void ordenarEdad(View view){
        //Como se inserto en el formato del usuario (dd-MM-yyyy), lo acomodo en la instruccion para que se ordene bien;
        ordenar  = NACIMIENTO;
        crearListado();
    }

}

