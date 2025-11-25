package com.paco.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    private Integer id;
    private Integer studentId;
    private Integer moduleId;
    private LocalDate date;
}
