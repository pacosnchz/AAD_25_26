package com.paco.aad;

import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repository.EnrollmentRepository;
import com.paco.aad.repository.ModuleRepository;
import com.paco.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {

    private final StudentRepository studentRepo;
    private final ModuleRepository moduleRepo;
    private final EnrollmentRepository enrollmentRepo;

    public AadApplication(
            StudentRepository studentRepo,
            ModuleRepository moduleRepo,
            EnrollmentRepository enrollmentRepo
    ) {
        this.studentRepo = studentRepo;
        this.moduleRepo = moduleRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }


    // a partir de aquí cada vez que quieras añadir un nuevo alumno deberás de poner un email y dni unico a cada Alumno, al igual que un id para los modulos distintos.

    @Override
    public void run(String... args) throws Exception {

        // Crear estudiante
        Student sofi = new Student(null, "49112936S", "Sofi", "sofia@gmail.com");
        sofi = studentRepo.create(sofi);
        log.info("Alumno creado: {}", sofi);

        // Crear módulo
        Module acceso_a_datos = new Module(null, "0003", "Acceso a Datos", 250);
        acceso_a_datos = moduleRepo.create(acceso_a_datos);
        log.info("Módulo creado: {}", acceso_a_datos);

        // Matricular
        enrollmentRepo.enroll(sofi.getId_alumno(), acceso_a_datos.getId_modulo());
        log.info("Alumno matriculado en el módulo");

        // Eliminar alumno

        //ESTO LO COMENTO PARA QUE NO BORRE EL ALUMNO CREADO EN LA BASE DE DATOS
//        studentRepo.delete(sofi.getId_alumno());
//        log.info("Alumno eliminado");

        log.info("Prueba ACT_2_1 completada correctamente");
    }
}
