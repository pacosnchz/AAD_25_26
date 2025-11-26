package com.paco.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    private Integer studentId;   // id_alumno
    private Integer moduleId;    // id_modulo
    private LocalDate date;      // fecha
}
