package com.paco.aad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AadApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		log.info("Introduce una  ruta:");


		Path ruta = Path.of(scanner.nextLine());

		if (Files.exists(ruta)) {
			log.info("Bienvenido a la ruta: " + ruta.toAbsolutePath());
		} else {
			log.info("La ruta no existe");
		}
	}
}
