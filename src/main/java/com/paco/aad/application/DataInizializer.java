package com.paco.aad.application;

import com.paco.aad.model.Module;
import com.paco.aad.model.Profile;
import com.paco.aad.model.Student;
import com.paco.aad.service.StudentManagementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInizializer implements CommandLineRunner {

    private final StudentManagementService managementService;

    @Override
    @Transactional
    public void run(String... args) {

        // =========================
        // PROFILES
        // =========================
        Profile profile1 = new Profile();
        profile1.setAddress("Rafael Alberti 74");
        profile1.setPhone("661362140");

        Profile profile2 = new Profile();
        profile2.setAddress("Rafael Alberti 56");
        profile2.setPhone("646321320");

        // =========================
        // STUDENTS
        // =========================
        Student paco = new Student();
        paco.setNif("11111111A");
        paco.setName("Paco");
        paco.setEmail("paco@email.com");
        paco.setCourse("DAM");
        paco.setProfile(profile1);

        Student sofia = new Student();
        sofia.setNif("22222222B");
        sofia.setName("Sofia");
        sofia.setEmail("sofia@email.com");
        sofia.setCourse("DAM");
        sofia.setProfile(profile2);

        paco = managementService.createStudent(paco);
        sofia = managementService.createStudent(sofia);

        // =========================
        // MODULES
        // =========================
        Module accesoDatos = new Module();
        accesoDatos.setCode("AD");
        accesoDatos.setName("Acceso a Datos");
        accesoDatos.setHours(180);

        Module programacion = new Module();
        programacion.setCode("PRO");
        programacion.setName("Programación");
        programacion.setHours(256);

        accesoDatos = managementService.createModule(accesoDatos);
        programacion = managementService.createModule(programacion);

        // =========================
        // ENROLLMENTS
        // =========================
        managementService.enrollStudent(paco.getId(), accesoDatos.getId());
        managementService.enrollStudent(paco.getId(), programacion.getId());
        managementService.enrollStudent(sofia.getId(), programacion.getId());

        // =========================
        // COUNT ENROLLMENTS
        // =========================
        int total = managementService.countEnrollments(paco.getId());
        System.out.println("Matrículas de Paco: " + total);

        // =========================
        // TRANSACTION TEST (ROLLBACK)
        // =========================
        /*
        Student error = new Student();
        error.setNif("ERROR");
        error.setName("Rollback");
        error.setEmail("error@email.com");
        error.setCourse("DAM");

        managementService.createStudent(error);
        throw new RuntimeException("Forzando rollback");
        */
    }
}
