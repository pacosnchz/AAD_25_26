package com.paco.aad.application;

import com.paco.aad.model.LogEvent;
import com.paco.aad.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void addEvent(String message) throws IOException {       // Lógica para crear un LogEvent y añadirlo usando logRepository

        LogEvent event = new LogEvent(LocalDateTime.now(), message);
        logRepository.appendEvent(event);
    }

    public List<String> getEventsByDate(String dateStr) throws IOException {            // Lógica para convertir dateStr a LocalDate y obtener eventos usando logRepository

        LocalDate date = LocalDate.parse(dateStr);
        return logRepository.findByDate(date);
    }

    public String getEncoding() {                   // Lógica para obtener la codificación actual desde logRepository

        return logRepository.getCurrentEncoding();
    }

    public void setEncoding(String encodingName) {          // Lógica para cambiar la codificación en logRepository

        logRepository.changeEncoding(encodingName);
    }
}
