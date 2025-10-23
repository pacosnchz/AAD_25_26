package com.paco.aad;

public class Alumno {
    private int id;
    private String nombre;
    private double nota;

    //constructor vacío
    public Alumno() {
    }

    //constructor con parámetros
    public Alumno(int id, String nombre, double nota) {
        this.id = id;
        this.nombre = nombre;
        this.nota = nota;
    }

    //getters and setters
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    //opcional: para ver el objeto en consola de forma legible
    @Override
    public String toString() {
        return String.format("Alumno{id=%d, nombre='%s', nota=%.2f}", id, nombre, nota);
    }
}

