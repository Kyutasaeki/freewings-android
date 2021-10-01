package com.example.freewings.ABM;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freewings.BaseDatos.AdminSQLiteOpenHelper;
import com.example.freewings.Entidades.Alumno;
import com.example.freewings.Otros.Menu;
import com.example.freewings.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ABM_Alumnos extends AppCompatActivity {

    //Elementos de la activity;
    protected EditText tDni, tNombre, tApellido, tNacimiento, tTelefono, tDireccion, tExtra, tNombrePadre, tTelefonoPadre;
    protected Button btnModificar, btnAgregar,btnEliminar;

    //Para la selecionar la fecha;
    private String formatoUsuario = "dd-MM-yyyy";
    private Calendar calendario = Calendar.getInstance();

    protected AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ABM_Alumnos);

        admin = new AdminSQLiteOpenHelper(this,"FreeWings",null,null,7);

        btnEliminar = findViewById(R.id.btn_eliminar);
        btnModificar = findViewById(R.id.btn_modificar);
        btnAgregar = findViewById(R.id.btn_agregar);

        tDni = findViewById(R.id.txt_dni);
        tNombre = findViewById(R.id.txt_nombreCurso);
        tApellido = findViewById(R.id.txt_apellido);
        tTelefono = findViewById(R.id.txt_telefono);
        tDireccion = findViewById(R.id.txt_direccion);
        tExtra = findViewById(R.id.txt_extra);
        tNombrePadre = findViewById(R.id.txt_nombrePadre);
        tTelefonoPadre = findViewById(R.id.txt_telefonoPadre);
        tNacimiento = findViewById(R.id.txt_nacimiento);


        //Limpio all porque cargo de nuevo si es que viene para modificar/eliminar/agregar;
        limpiar();

        //Obtengo el alumno pasado por parametro, y me sirve para chequear de que lado vino;
        Bundle b = getIntent().getExtras();

        if(b != null){ //Quiere modificar / Eliminar;

            //Oculto boton de agregar porque va a modificar;
            btnAgregar.setVisibility(View.INVISIBLE);
            btnModificar.setVisibility(View.VISIBLE);
            btnEliminar.setVisibility(View.VISIBLE);

            //Para que no me lo editen;
            tDni.setEnabled(false);

            //Obtengo del extra, que es el alumno;
            Alumno alumno = (Alumno) b.getSerializable("alumno");

            //Cargo los datos;
            cargarDatos(alumno);
        }
        else{ //Quiere agregar;
            btnAgregar.setVisibility(View.VISIBLE);
            btnModificar.setVisibility(View.INVISIBLE);
            btnEliminar.setVisibility(View.INVISIBLE);
            tDni.setEnabled(true);
        }
    }

    //Agrega un nuevo alumno a la base de datos;
    public void agregarAlumno(View view){

        //Abro la base de datos para escribir;
        SQLiteDatabase db = admin.getWritableDatabase();

        //Obtengo los datos que cargo el usuario;
        String dni = tDni.getText().toString();
        String nombre = tNombre.getText().toString();
        String apellido = tApellido.getText().toString();
        String nacimiento = tNacimiento.getText().toString();
        String telefono = tTelefono.getText().toString();
        String direccion = tDireccion.getText().toString();
        String extra = tExtra.getText().toString();
        String nombrePadre = tNombrePadre.getText().toString();
        String telefonoPadre = tTelefonoPadre.getText().toString();

        //Compruebo los campos que son obligatorios;
        if (!nombre.isEmpty() && !apellido.isEmpty() && !dni.isEmpty() && (dni.length() >= 7)) {
            ContentValues registro = new ContentValues();

            registro.put("dni", dni);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("nacimiento", nacimiento); //Se inserta con el formato del usuario;
            registro.put("telefono", telefono);
            registro.put("direccion", direccion);
            registro.put("extra", extra);
            registro.put("nombrePadre", nombrePadre);
            registro.put("telefonoPadre", telefonoPadre);

            //Guardo el resultado que me devuelve el insert de la db;
            long flag = db.insert("alumnos", null, registro);

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

    //Se modifica el alumno a traves de los parametros;
    public void modificarAlumno(View view){

        //Abro la base de datos;
        SQLiteDatabase db = admin.getWritableDatabase();

        //Obtengo los datos nuevos que cargo;
        String nNombre = tNombre.getText().toString();
        String nApellido = tApellido.getText().toString();
        String nNacimiento = tNacimiento.getText().toString();
        String nTelefono = tTelefono.getText().toString();
        String nDireccion = tDireccion.getText().toString();
        String nExtra = tExtra.getText().toString();
        String nNombrePadre = tNombrePadre.getText().toString();
        String nTelefonoPadre = tTelefonoPadre.getText().toString();

        //No chequeo DNI porque esta Deshabilitado para la edicion;
        if (!nNombre.isEmpty() && !nApellido.isEmpty()) {
            String instruccion = "UPDATE alumnos SET nombre='" + nNombre + "', apellido='" + nApellido + "', telefono='" + nTelefono + "', direccion='" + nDireccion + "', extra='" + nExtra + "', nombrePadre='" + nNombrePadre + "', telefonoPadre='" + nTelefonoPadre + "', nacimiento='" + nNacimiento + "' WHERE dni=" + Integer.parseInt(tDni.getText().toString());
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

    //Se elimina el alumno por dni, seleccionado en el listado
    private void eliminar(){
        SQLiteDatabase db = admin.getWritableDatabase();

        String instruccion = "DELETE From alumnos WHERE dni=" + tDni.getText().toString();
        db.execSQL(instruccion);
        db.close();

        Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();

        goToMenu();
    }

    //Cargo los datos en los textView, sacando los datos del alumno;
    private void cargarDatos(Alumno a){
        tDni.setText(a.getDni() + "");
        tNombre.setText(a.getNombre());
        tApellido.setText(a.getApellido());
        tNacimiento.setText(a.getNacimiento());
        tTelefono.setText(a.getTelefono());
        tDireccion.setText(a.getDireccion());
        tExtra.setText(a.getExtra());
        tNombrePadre.setText(a.getNombrePadre());
        tTelefonoPadre.setText(a.getTelefonoPadre());
    }

    //Limpia todos los campos;
    private void limpiar(){
        tDni.setText("");
        tNombre.setText("");
        tApellido.setText("");
        tTelefonoPadre.setText("");
        tTelefono.setText("");
        tNombrePadre.setText("");
        tNacimiento.setText("");
        tDireccion.setText("");
        tExtra.setText("");

        btnAgregar.setVisibility(View.VISIBLE);
        btnModificar.setVisibility(View.INVISIBLE);
        btnEliminar.setVisibility(View.INVISIBLE);
    }

    //El intent para ir al menu;
    private void goToMenu(){
        Intent goMenu = new Intent(ABM_Alumnos.this, Menu.class);
        startActivity(goMenu);
    }

    public void eliminarAlumno(View view){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmación");
        dialogo.setMessage("Se eliminara el alumno/a");
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

    //Metodo para el mostrar el DatePicker que tiene el OnClick del textView fecha nacimiento;
    public void actualizarFecha(View view){
        new DatePickerDialog(ABM_Alumnos.this, date,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    //Carga la fecha en el TextView con el formato para el usuario dd-MM-yyyy;
    private void mostrarFecha(){
        SimpleDateFormat sdf = new SimpleDateFormat(formatoUsuario, Locale.getDefault());
        tNacimiento.setText(sdf.format(calendario.getTime()));
    }

    //Listener del calendario;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, month);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mostrarFecha();
        }
    };






}
