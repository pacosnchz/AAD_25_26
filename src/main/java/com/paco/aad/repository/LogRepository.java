package com.paco.aad.repository;

import com.paco.aad.model.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogRepository {

    // Ruta del fichero log
    private final Path logPath = Path.of("app.log");
    // Formato de salida: [YYYY-MM-DD HH:mm:ss] mensaje
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // Codificación actual (por defecto UTF-8)
    private Charset currentCharset = Charset.forName("UTF-8");

    //Añade un evento al fichero

    public void appendEvent(LogEvent event) throws IOException {
        // Creamos el fichero si no existe
        if (!Files.exists(logPath)) {
            Files.createFile(logPath);
        }

        // Construir la línea
        String line = String.format(
                "[%s] %s",
                event.getTimestamp().format(formatter),
                event.getMessage()
        );

        // Escribir usando la codificación actual
        try (Writer w = Files.newBufferedWriter(logPath, currentCharset, java.nio.file.StandardOpenOption.APPEND)) {
            try (BufferedWriter bw = new BufferedWriter(w)) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    //Devuelve todos los eventos de una fecha concreta (YYYY-MM-DD)

    public List<String> findByDate(LocalDate date) throws IOException {
        List<String> result = new ArrayList<>();

        if (!Files.exists(logPath)) {
            return result; // si no hay fichero todavía, lista vacía
        }

        // ejemplo línea: [2025-09-13 18:45:00] Usuario Ana inició sesión
        // vamos a comprobar solo la parte de fecha "2025-09-13"
        String datePrefix = "[" + date.toString(); // ej: "[2025-09-13"

        List<String> allLines = Files.readAllLines(logPath, currentCharset);
        for (String line : allLines) {
            if (line.startsWith(datePrefix)) {
                result.add(line);
            }
        }

        return result;
    }

    //Cambia la codificación del fichero para próximas escrituras. Valores esperados: "UTF-8" o "ISO-8859-1"

    public void changeEncoding(String encodingName) {
        this.currentCharset = Charset.forName(encodingName);
    }

    //Devuelve la codificación actual
    public String getCurrentEncoding() {
        return currentCharset.displayName();
    }
}
