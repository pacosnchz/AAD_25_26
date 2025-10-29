package com.paco.aad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("iniciando conversión de alumnos...");

        //Rutas de lso archivos
        String inputFile = "src/main/resources/alumnos.csv";
        String jsonOutput = "target/alumnos.json";
        String xmlOutput = "target/alumnos.xml";

        //primero leemos el csv
        List<Alumno> alumnos = leerCSV(inputFile);
        log.info("Leídos {} alumnos desde el CSV", alumnos.size());

        //ahora lo guardaremos en JSON y en XML
        //JSON
        guardarJSON(alumnos, jsonOutput);
        log.info("Fichero JSON creado correctamente: {}", jsonOutput);

        //XML
        guardarXML(alumnos, xmlOutput);
        log.info("Fichero XML creado correctamente: {}", xmlOutput);

        log.info("Conversión completada. Los archivos están en la carpeta /target");
    }

    //ahora creamos los métodos
    //primer mtodo para leer el csv y convertirlo en Lista de objetos

    private List<Alumno> leerCSV(String filePath) throws IOException {
        try (BufferedReader BR = Files.newBufferedReader(Paths.get(filePath))) {
            return BR.lines()           //sirve para hacer que todos los elementos del csv estén en una misma línea
                    .skip(1)        //saltamos la primera línea del csv, que es el encabezado
                    .map(line -> {      //comenzamos a separar los elementos de cada línea que están separados por als comas en los distintos atributos de cada alumno
                        String[] campos = line.split(",");
                        return new Alumno(
                                Integer.parseInt(campos[0]),
                                campos[1],
                                Double.parseDouble(campos[2])
                        );
                    })
                    .collect(Collectors.toList());      //juntamos todos los alumnos creados en una lista
        }
    }

    //Mtodo para generar el fichero JSON
    private void guardarJSON(List<Alumno> alumnos, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), alumnos);
    }

    //metodo para generar el fichero XMl
    private void guardarXML(List<Alumno> alumnos, String filePath) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), alumnos);
    }
}