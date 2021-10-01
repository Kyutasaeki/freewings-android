package com.example.freewings.Entidades;

import java.io.Serializable;

public class Cobro implements Serializable {

    protected int id;
    protected int alumno_id;
    protected int curso_id;
    protected String descripcion;
    protected int precio;
    protected String estado;
    protected String tipo;
    protected String vencimiento;

    public Cobro(int id, int alumno_id, int curso_id, String descripcion, int precio, String estado, String tipo, String vencimiento) {
        this.id = id;
        this.alumno_id = alumno_id;
        this.curso_id = curso_id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.tipo = tipo;
        this.vencimiento = vencimiento;
    }

    public Cobro(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlumno_id() {
        return alumno_id;
    }

    public void setAlumno_id(int alumno_id) {
        this.alumno_id = alumno_id;
    }

    public int getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(int curso_id) {
        this.curso_id = curso_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

}
