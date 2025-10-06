package com.paco.aad;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Alumno {
    public static final int NOMBRE_LENGTH = 20;
    public static final int RECORD_SIZE = 4 + (2 * NOMBRE_LENGTH) + 8; // 52 bytes

    private int id;
    private String nombre;
    private double nota;

    public Alumno(int id, String nombre, double nota) {
        this.id = id;
        setNombre(nombre);
        this.nota = nota;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getNota() { return nota; }

    public void setNota(double nota) { this.nota = nota; }

    public void setNombre(String nombre) {
        // Rellenar o truncar el nombre a 20 caracteres
        if (nombre.length() > NOMBRE_LENGTH)
            this.nombre = nombre.substring(0, NOMBRE_LENGTH);
        else
            this.nombre = String.format("%-" + NOMBRE_LENGTH + "s", nombre);
    }

    // Escribir un alumno al archivo
    public void write(RandomAccessFile raf) throws IOException {
        raf.writeInt(id);
        raf.writeChars(nombre);
        raf.writeDouble(nota);
    }

    // Leer un alumno del archivo
    public static Alumno read(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();

        char[] nombreChars = new char[NOMBRE_LENGTH];
        for (int i = 0; i < NOMBRE_LENGTH; i++) {
            nombreChars[i] = raf.readChar();
        }
        String nombre = new String(nombreChars).trim();

        double nota = raf.readDouble();

        return new Alumno(id, nombre, nota);
    }
}
