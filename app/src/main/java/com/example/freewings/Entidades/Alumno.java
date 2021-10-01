package com.example.freewings.Entidades;

import java.io.Serializable;

//Modela el comportamiento de un alumno, e implementa Serializable para poder pasarse como parametro entre activities;
public class Alumno implements Serializable {

    protected int id;
    protected int dni;
    protected String nombre;
    protected String apellido;
    protected String nacimiento;
    protected String telefono;
    protected String direccion;
    protected String extra;
    protected String nombrePadre;
    protected String telefonoPadre;


    public Alumno(int dni, String nombre, String apellido, String nacimiento, String telefono, String direccion, String extra, String nombrePadre, String telefonoPadre) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacimiento = nacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.extra = extra;
        this.nombrePadre = nombrePadre;
        this.telefonoPadre = telefonoPadre;
    }

    public Alumno() {
    }


    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) { this.nacimiento = nacimiento; }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getTelefonoPadre() {
        return telefonoPadre;
    }

    public void setTelefonoPadre(String telefonoPadre) {
        this.telefonoPadre = telefonoPadre;
    }

}