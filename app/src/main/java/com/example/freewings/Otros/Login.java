package com.example.freewings.Otros;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freewings.R;

public class Login extends AppCompatActivity {

    private final String USUARIO = "";
    private final String PASSWORD = "";

    protected TextView user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user = (EditText) findViewById(R.id.txt_usuario);
        pass = (EditText) findViewById(R.id.txt_password);
    }

    @Override
    public void onBackPressed() {
        //Implemento el metodo vacio asi no tiene acceso a volver para atras;
    }

    //Pasa a la siguiente actividad si es correcto el usuario;
    public void login(View view){
        String usuario = user.getText().toString();
        String password = pass.getText().toString();

        if(password.equals(PASSWORD) && usuario.equals(USUARIO)){
            Intent siguiente = new Intent(this, Menu.class);
            startActivity(siguiente);
        }
        else{
            Toast.makeText(this, "Usuario/contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }

}
