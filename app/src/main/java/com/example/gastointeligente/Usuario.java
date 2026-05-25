package com.example.gastointeligente;

import com.orm.SugarRecord;

public class Usuario extends SugarRecord {

    private int cedula;
    private String nombre;
    private int telefono;

    // Sugar ORM requiere un constructor vacío
    public Usuario() {
    }

    public Usuario(int cedula, String nombre, int telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
