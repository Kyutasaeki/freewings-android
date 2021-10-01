package com.example.freewings.ABM;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.freewings.BaseDatos.AdminSQLiteOpenHelper;
import com.example.freewings.Entidades.Curso;
import com.example.freewings.Otros.Menu;
import com.example.freewings.R;

import java.util.ArrayList;

public class ABM_Cursos extends AppCompatActivity {

    protected AdminSQLiteOpenHelper admin;

    //Elementos de la activity;
    protected TextView tTituloLista;
    protected EditText tNombreCurso, tValorCurso;
    protected Button btnAgregar, btnModificar, btnEliminar, btnAgregarAlumno, btnSacarAlumno;

    protected ListView tListaAlumnos;
    protected ArrayList<String> listaInformacion;

    protected ArrayList<Integer> listaID;
    private int curso_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abm_cursos);

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

    public void agregarCurso(View view){

        //Abro la base de datos para escribir;
        SQLiteDatabase db = admin.getWritableDatabase();

        //Obtengo los datos que cargo el usuario;
        String nombre = tNombreCurso.getText().toString();
        String precio = tValorCurso.getText().toString();

        //Compruebo los campos que son obligatorios;
        if (!nombre.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("nombre", nombre);
            registro.put("precio", precio);

            //Guardo el resultado que me devuelve el insert de la db;
            long flag = db.insert("cursos", null, registro);

            if(flag != -1){ //Inserto bien;
                Toast.makeText(this, "Se agrego correctamente", Toast.LENGTH_SHORT).show();
                db.close();
                goToMenu();
                limpiar();
            }
            else{ //Hubo error;
                Toast.makeText(this, "Ocurrio un problema", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Revise los datos rellenados nuevamente", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void modificarCurso(View view){

        //Abro la base de datos;
        SQLiteDatabase db = admin.getWritableDatabase();

        //Obtengo los datos nuevos que cargo;
        String nNombre = tNombreCurso.getText().toString();
        String nPrecio = tValorCurso.getText().toString();

        if (!nNombre.isEmpty() && !nPrecio.isEmpty()) {
            String instruccion = "UPDATE cursos SET nombre='" + nNombre + "', precio='" + nPrecio + "' WHERE id=" + curso_id;
            db.execSQL(instruccion);
            Toast.makeText(this, "Se modifico correctamente", Toast.LENGTH_SHORT).show();
            db.close();
            goToMenu();
        }
        else{
            Toast.makeText(this, "Revise los datos rellenados nuevamente", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void eliminar(){
        SQLiteDatabase db = admin.getWritableDatabase();

        //Eliminar en cascada;

        String instruccion = "DELETE From Cursos WHERE id=" + curso_id;
        db.execSQL(instruccion);
        db.close();

        Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();

        goToMenu();
    }

    public void eliminarCurso(View view){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmación");
        dialogo.setMessage("Se eliminara el curso");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                eliminar();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

    public void agregarAlumnoCurso(View view) {
        Intent goToSeleccion = new Intent(this, Alumnos_cursos.class);
        goToSeleccion.putExtra("listaID", listaID);
        goToSeleccion.putExtra("curso_id", curso_id);
        goToSeleccion.putExtra("accion","agregar");
        startActivity(goToSeleccion);
    }

    public void eliminarAlumnoCurso(View view){
        Intent goToSeleccion = new Intent(this, Alumnos_cursos.class);
        goToSeleccion.putExtra("listaID",listaID);
        goToSeleccion.putExtra("curso_id",curso_id);
        goToSeleccion.putExtra("accion","sacar");
        startActivity(goToSeleccion);
    }



    private void cargarDatos(Curso c){
        tNombreCurso.setText(c.getNombre());
        tValorCurso.setText(c.getPrecio() + "");
    }

    private void cargarLista(Curso curso){
        //Abro la base de datos para escribir;
        SQLiteDatabase db = admin.getReadableDatabase();

        //Se inicializa las variables a usar;
        String linea;
        String nombre;
        String apellido;
        int alumno_id;
        listaID = new ArrayList<Integer>();
        listaInformacion = new ArrayList<String>();

        //ver que no se cargan en la lista;

        Cursor c = db.rawQuery("SELECT alumnos_cursos.alumno_id, alumnos.nombre, alumnos.apellido FROM alumnos_cursos INNER JOIN alumnos ON alumnos_cursos.alumno_id = alumnos.id WHERE alumnos_cursos.curso_id =" + curso_id,null);
        while(c.moveToNext()){
            alumno_id = c.getInt(c.getColumnIndex("alumno_id"));
            listaID.add(alumno_id);

            nombre = c.getString(c.getColumnIndex("nombre"));
            apellido = c.getString(c.getColumnIndex("apellido"));
            linea = nombre + " " + apellido;
            listaInformacion.add(linea);
        }

        db.close();

        //Se insertan en el ListView;
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        tListaAlumnos.setAdapter(adaptador);
    }

    private void limpiar(){
        curso_id = 0;
        tNombreCurso.setText("");
        tValorCurso.setText("");
    }

    private void goToMenu(){
        Intent goMenu = new Intent(ABM_Cursos.this, Menu.class);
        startActivity(goMenu);
    }

}
