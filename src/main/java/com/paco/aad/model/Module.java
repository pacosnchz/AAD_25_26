package com.paco.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {

    private Integer id_modulo;
    private String codigo;
    private String nombre;
    private Integer horas;
}
