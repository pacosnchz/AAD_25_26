package com.paco.aad;


public class Alumno {
    //vamos a comenzar definiendo las constantes de tamaño (en bytes) de los alumnos

    public static final int NOMBRE_LENGTH = 20; //aquí establecemos la longitud de nombre de los alumnos, 20 caracteres
    public static final int RECORD_SIZE = 4 + (2 * NOMBRE_LENGTH ) + 8;     //el tamaño total de cada alumno será de 52 bytes. 4 bytes del ID, que es un int; 8 del double de la nota, y 40 bytes de los 20 caracteres de cada nombre (cada char son 2 bytes)

    //los atributos a definir serán id de alumno, nombre y nota
    private int id;
    private String nombre;
    private double nota;

    //constructor
    public Alumno (int od, String nombre, double nota){ //este es el constructor de Alumno
        this.id = id;
        setNombre(nombre); //definido más abajo, para ajustar el tamaño del nombre
        this.nota = nota;
    }

    //getters and setters
    public int getID() { return id; }
    public String getNombre() { return nombre; }
    public double getNota() { return nota; }

    public void setNota(double nota) {this.nota = nota; }//para ajustar el tamaño del nombre a 20; si se pasa, se recorta a 20, y si no llega, se rellena hasta 20

    public void setNombre(String nombre) {
        if (nombre.length() > NOMBRE_LENGTH)
            this.nombre = nombre.substring(0, NOMBRE_LENGTH);
        else
            this.nombre = String.format("%-" + NOMBRE_LENGTH + "s", nombre),
    }
}
