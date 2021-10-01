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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.freewings.BaseDatos.AdminSQLiteOpenHelper;
import com.example.freewings.Entidades.Alumno;
import com.example.freewings.Otros.Menu;
import com.example.freewings.R;
import java.util.ArrayList;

public class Alumnos_cursos extends AppCompatActivity {

    protected ListView listView;
    protected ArrayList<Alumno> listaAlumno; //Todos los alumnos de la db;
    protected ArrayList<String> listaInformacion; //Lineas con informacion para el ListView;

    protected ArrayList<Integer> listaID;
    protected ArrayList<Integer> listaAlumnos;
    protected int curso_id;
    protected int alumno_seleccionado;
    protected String accion;

    protected AdminSQLiteOpenHelper admin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alumnos_cursos);

        admin = new AdminSQLiteOpenHelper(this,"FreeWings",null,null,7);

        listView = findViewById(R.id.ls_alumnosSeleccion);

        Bundle b = getIntent().getExtras();

        listaID = (ArrayList<Integer>) b.get("listaID");
        curso_id = b.getInt("curso_id");
        accion = b.getString("accion");

        if(accion.equals("agregar")){
            cargarListaAgregar();
        }

        if(accion.equals("sacar")){
            cargarListaSacar();
        }
    }

    public void cargarListaAgregar(){
        //Abro la base de datos para escribir;
        SQLiteDatabase db = admin.getReadableDatabase();

        //Inicializo variabales;
        String linea;
        listaAlumnos = new ArrayList<Integer>();
        listaInformacion = new ArrayList<String>();

        //String hola = String.join(",", listaID); (,,,,,,) WHERE NO
        //Probar esto;

        //String lista = Arrays.toString(secuencia);
        //System.out.println(lista);

        String lista = "(";
        for(int i = 0; i < listaID.size(); i++){
            if(i == 0){
                lista = lista + listaID.get(i) + "";
            }
            else{
                lista = lista + ", " + listaID.get(i);
            }
        }
        lista = lista + ")";

        Cursor c = db.rawQuery("SELECT id, nombre, apellido FROM alumnos WHERE id NOT IN " + lista,null);

        while(c.moveToNext()){
            listaAlumnos.add(c.getInt(c.getColumnIndex("id")));

            linea = c.getString(c.getColumnIndex("nombre")) + " " + c.getString(c.getColumnIndex("apellido"));
            listaInformacion.add(linea);
        }

        db.close();

        //Se insertan en el ListView;
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listView.setAdapter(adaptador);

        //Le seteo un listener especifico para sacarlo;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                alumno_seleccionado = listaAlumnos.get(pos);
                confirmarAgregar();

            }
        });

    }

    public void cargarListaSacar(){
        //Abro la base de datos para escribir;
        SQLiteDatabase db = admin.getReadableDatabase();

        String linea;
        listaInformacion = new ArrayList<String>();
        listaAlumnos = new ArrayList<Integer>();

        String lista = "(";
        for(int i = 0; i < listaID.size(); i++){
            if(i == 0){
                lista = lista + listaID.get(i) + "";
            }
            else{
                lista = lista + ", " + listaID.get(i);
            }
        }
        lista = lista + ")";

        Cursor c = db.rawQuery("SELECT id, nombre, apellido FROM alumnos WHERE id IN " + lista,null);

        while(c.moveToNext()){
            listaAlumnos.add(c.getInt(c.getColumnIndex("id")));

            linea = c.getString(c.getColumnIndex("nombre")) + " " + c.getString(c.getColumnIndex("apellido"));
            listaInformacion.add(linea);
        }

        db.close();

        //Se insertan en el ListView;
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listView.setAdapter(adaptador);

        //Le seteo un listener especifico para sacarlo;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                alumno_seleccionado = listaAlumnos.get(pos);
                confirmarSacar();
            }
        });

    }

    private void confirmarAgregar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmación");
        dialogo.setMessage("Se agregara el alumno");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                agregar();
                goToMenu();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

    private void agregar(){
        //Abro la base de datos para escribir;
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("alumno_id", alumno_seleccionado);
        registro.put("curso_id",curso_id);


        System.out.println(alumno_seleccionado + " " + curso_id);

        //Guardo el resultado que me devuelve el insert de la db;
        long flag = db.insert("alumnos_cursos", null, registro);

        if(flag != -1){ //Inserto bien;
            Toast.makeText(this, "Se agrego correctamente", Toast.LENGTH_SHORT).show();
        }
        else{ //Hubo error;
            Toast.makeText(this, "Ocurrio un problema", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void confirmarSacar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmación");
        dialogo.setMessage("Se sacara el alumno del curso");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                sacar();
                goToMenu();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

    private void sacar(){
        SQLiteDatabase db = admin.getWritableDatabase();

        System.out.println(alumno_seleccionado + " " + curso_id);
        db.execSQL("DELETE FROM alumnos_cursos WHERE alumno_id=" + alumno_seleccionado + " AND curso_id=" + curso_id);
        db.close();

        Toast.makeText(Alumnos_cursos.this, "Removido/a del curso", Toast.LENGTH_SHORT).show();
    }

    private void goToMenu(){
        Intent goMenu = new Intent(Alumnos_cursos.this, Menu.class);
        startActivity(goMenu);
    }

}

