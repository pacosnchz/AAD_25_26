package com.paco.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    private Integer id;          // id de la matrícula (PK)
    private Integer id_alumno;   // FK a alumno
    private Integer id_modulo;   // FK a modulo
    private LocalDate fecha;     // fecha de matrícula
}
