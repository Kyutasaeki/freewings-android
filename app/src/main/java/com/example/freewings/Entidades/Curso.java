package com.example.freewings.Entidades;

import java.io.Serializable;

public class Curso implements Serializable {

    protected int id;
    protected String nombre;
    protected int precio;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }


}
