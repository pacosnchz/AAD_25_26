package com.paco.aad;


import lombok.extern.slf4j.Slf4j;
import java.io.RandomAccessFile;
import java.util.Scanner;
@Slf4j //acostumbrarme a añadir este @ y añadir el lombok para poder usar los log

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


                }
            }
        }
    }
}
