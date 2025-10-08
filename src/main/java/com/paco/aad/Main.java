package com.paco.aad;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
@Slf4j //acostumbrarme a añadir este @ y añadir el lombok para poder usar los log.ingo y no los System.out.println

public class Main {

    private static final String FILE_NAME = "alumnos.dat"; //nombre del fichero en binario

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            //rw es lectura y escirtura

            int opcion; //creamos el menú principal
            do {
                log.info("\n--- MENÚ ---");
                log.info("1. Insertar Alumno");
                log.info("2. Consultar alumno por posición");
                log.info("3. Modificar nota de un alumno");
                log.info("0. Salir.");
                log.info("------------");
                log.info("Escoja una opción: ");
                opcion = Integer.parseInt(scanner.nextLine()); //para leer por teclado la opción del usuario

                switch (opcion) {
                    case 1 -> insertarAlumno(raf,scanner);
                    case 2 -> consultarAlumno(raf,scanner);
                    case 3 -> modificarNota(raf,scanner);
                    case 0 -> {
                        log.info("Fin del programa.");
                        return;
                    }
                    default -> log.info("Opción no válida"); // cualquier cosa escrita por el usuario que no sea ninguna de las opciones, dará opción no válida
                }

            } while (opcion !=0); //el menú se ejecutará constántemente hasta que el usuario pulse 0

        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    //métodos del menú
    private static void insertarAlumno(RandomAccessFile raf, Scanner scanner) throws IOException {
        log.info("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();     // limpia el salto de línea

        log.info("Nombre: ");
        String nombre = scanner.nextLine();

        log.info("Nota: ");
        String notaPorTeclado = scanner.next().replace(',', '.');   // Acepta tanto coma como punto como separador decimal
        double nota = Double.parseDouble(notaPorTeclado);

        raf.seek(raf.length()); // Mover el puntero al final del archivo para escribir al final

        Alumno alumno = new Alumno(id, nombre, nota);   // Crear objeto alumno y escribirlo
        alumno.write(raf);

        System.out.println("Alumno insertado correctamente.");
    }

    private static void consultarAlumno(RandomAccessFile raf, Scanner scanner) throws IOException{
        log.info("Posición del alumno ( empezando en 0): ");
        int posicion = scanner.nextInt();  //introduciremos la posición mediante este scanner

        long bytePosicion = posicion * Alumno.RECORD_SIZE; //la posición del alumno estará definida por la posición dada multiplicada por la cantidad de bytes máximos, sinedo cada intervalo de 52 bytes, la inforomcaión de un alumno distinto

        if (bytePosicion >= raf.length()) {  // si la posición dada supera la cantidad de bytes que hay almacenados, significará que no hay ningún alumno en esa posición
            log.info("No existe alumno en esa posición");
            return;
        }

        raf.seek(bytePosicion);  //movemos el puntero al registro correspondiente

        Alumno alumno = Alumno.read(raf);  //lee el registro del alumno. EN BINARIO

        //una vez encontrado el alumno, enseñamos los datos
        log.info("---------------");
        log.info("ALUMNO ENCONTRADO");
        log.info("---------------");
        log.info("ID: " + alumno.getID());
        log.info("Nombre: " + alumno.getNombre());
        log.info("Nota: " + alumno.getNota());
    }
}
