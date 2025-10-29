package com.paco.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data // lombok genera getters, setters, y toString automáticamente
@NoArgsConstructor // constructor vacío
@AllArgsConstructor // constructor con todos los parámetros
@ToString // para mostrar el objeto como texto
public class LogEvent {
    private LocalDateTime timestamp; //fecha y hora del evento
    private String message; //mensaje del evento

}
