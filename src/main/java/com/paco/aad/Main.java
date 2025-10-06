package com.paco.aad;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "alumnos.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {

            int opcion;
            do {
                System.out.println("\n===== GESTIÓN DE ALUMNOS =====");
                System.out.println("1. Insertar alumno");
                System.out.println("2. Consultar alumno por posición");
                System.out.println("3. Modificar nota de un alumno");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1 -> insertarAlumno(raf, sc);
                    case 2 -> consultarAlumno(raf, sc);
                    case 3 -> modificarNota(raf, sc);
                }

            } while (opcion != 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void insertarAlumno(RandomAccessFile raf, Scanner sc) throws IOException {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nota: ");
        double nota = sc.nextDouble();

        // Mover el puntero al final del archivo
        raf.seek(raf.length());
        Alumno alumno = new Alumno(id, nombre, nota);
        alumno.write(raf);

        System.out.println("Alumno insertado correctamente.");
    }

    private static void consultarAlumno(RandomAccessFile raf, Scanner sc) throws IOException {
        System.out.print("Posición del alumno (empezando en 0): ");
        int pos = sc.nextInt();

        long posicionByte = pos * Alumno.RECORD_SIZE;
        if (posicionByte >= raf.length()) {
            System.out.println("No existe alumno en esa posición.");
            return;
        }

        raf.seek(posicionByte);
        Alumno alumno = Alumno.read(raf);

        System.out.println("ID: " + alumno.getId());
        System.out.println("Nombre: " + alumno.getNombre());
        System.out.println("Nota: " + alumno.getNota());
    }

    private static void modificarNota(RandomAccessFile raf, Scanner sc) throws IOException {
        System.out.print("Posición del alumno: ");
        int pos = sc.nextInt();

        long posicionByte = pos * Alumno.RECORD_SIZE;
        if (posicionByte >= raf.length()) {
            System.out.println("No existe alumno en esa posición.");
            return;
        }

        raf.seek(posicionByte);
        Alumno alumno = Alumno.read(raf);

        System.out.println("Alumno encontrado: " + alumno.getNombre() + " (Nota actual: " + alumno.getNota() + ")");
        System.out.print("Nueva nota: ");
        double nuevaNota = sc.nextDouble();

        alumno.setNota(nuevaNota);

        // Volver al inicio del registro para sobrescribir solo esa parte
        raf.seek(posicionByte);
        alumno.write(raf);

        System.out.println("Nota actualizada correctamente.");
    }
}
