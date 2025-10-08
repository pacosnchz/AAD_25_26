package com.paco.aad;


public class Alumno {
    //vamos a comenzar definiendo las constantes de tamaño (en bytes) de los alumnos

    public static final int NOMBRE_LENGTH = 20; //aquí establecemos la longitud de nombre de los alumnos, 20 caracteres
    public static final int RECORD_SIZE = 4 + (2 * NOMBRE_LENGTH ) + 8;     //el tamaño total de cada alumno será de 52 bytes. 4 bytes del ID, que es un int; 8 del double de la nota, y 40 bytes de los 20 caracteres de cada nombre (cada char son 2 bytes)

    //los atributos a definir serán id de alumno, nombre y nota
    private int id;
    private String nombre;
    private double nota;


    }
}
