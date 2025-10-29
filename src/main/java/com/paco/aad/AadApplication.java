package com.paco.aad;

import com.paco.aad.application.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class AadApplication implements CommandLineRunner {

    private final LogService logService;

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean salir = false;

        while (!salir) {
            log.info("===== GESTOR DE LOGS =====");
            log.info("1. Añadir evento");
            log.info("2. Filtrar eventos por fecha (YYYY-MM-DD)");
            log.info("3. Ver / Cambiar codificación (actual: " + logService.getEncoding() + ")");
            log.info("4. Salir");
            log.info("Elige opción: ");

            String opcion = br.readLine();

            switch (opcion) {
                case "1" -> addEvent(br); //añade evento
                case "2" -> filterByDate(br); //busca evento por la fecha solo en ese formato
                case "3" -> changeEncoding(br); //ver o cambiar codificación
                case "4" -> {
                    log.info("Saliendo del programa...");
                    salir = true;
                }
                default -> log.info("Opción no válida.");
            }

            log.info("");
        }

        System.exit(0);
    }

    // metodos del menú


    private void addEvent(BufferedReader br) {          // Añade un evento al log
        try {
            log.info("Mensaje del evento: ");
            String msg = br.readLine();
            logService.addEvent(msg);
            log.info("Evento guardado correctamente.");
        } catch (Exception e) {
            log.error("Error al guardar el evento: {}", e.getMessage());
        }
    }

    private void filterByDate(BufferedReader br) {      // Filtra los eventos por fecha
        try {
            log.info("Introduce la fecha (YYYY-MM-DD): ");
            String fechaStr = br.readLine();
            List<String> eventos = logService.getEventsByDate(fechaStr);
            if (eventos.isEmpty()) {
                log.info("No hay eventos para {}", fechaStr);
            } else {
                log.info("Eventos del {}:", fechaStr);
                eventos.forEach(line -> log.info("{}", line));
            }
        } catch (Exception e) {
            log.error("Error al buscar eventos: {}", e.getMessage());
        }
    }

    private void changeEncoding(BufferedReader br) {        // Cambia la codificación de escritura del fichero
        try {
            log.info("Codificación actual: {}", logService.getEncoding());
            log.info("¿Quieres cambiarla? (UTF-8 / ISO-8859-1 / n para no)");
            String enc = br.readLine();
            if (!enc.equalsIgnoreCase("n")) {
                logService.setEncoding(enc);
                log.info("Codificación cambiada a {}", logService.getEncoding());
            }
        } catch (Exception e) {
            log.error("Codificación no válida: {}", e.getMessage());
        }
    }
}

