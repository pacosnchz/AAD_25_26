package com.paco.aad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner { //estudiar ya que lo usaremos bastante

	public static void main(String[] args) {
		SpringApplication.run(AadApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in); //repasar

		log.info("Introduce una  ruta:"); //el log.info actúa como un print line, pero fuarda más información importante que será util más adelante
		Path ruta = Path.of(scanner.nextLine());

		if (Files.exists(ruta)) {
            log.info("Bienvenido a la ruta: {}", ruta.toAbsolutePath()); //despues de la coma (,) escribimos lo que queremos que se sume al texto por pantalla
		} else {
			log.info("La ruta no existe");
			return; //si la ruta no existe, el programa se para
		}

		//menú
		int opcion;
		do {log.info("\n--- MENÚ ---");
			log.info("1. Crear un nuevo fichero vacío");
			log.info("2. Mover un fichero a otra ubicación");
			log.info("3. Borrar un fichero existente");
			log.info("4. Mostrar contenido del directorio");
			log.info("0. Salir");
			log.info("Elige una opción:");

			opcion = Integer.parseInt(scanner.nextLine()); //esto no puede ir debaj0o de la declaración de 'int opcion' pq el prgorama pararía ahí

			switch (opcion) {
				case 1 -> {
					log.info("Introduce el nombre del fichero a crear:");
					String nombreFichero = scanner.nextLine();
					Path nuevoFichero = ruta.resolve(nombreFichero);
					try { 											//utilizamos try y catch para tener en cuenta que el usuario puede cometer errores
						Files.createFile(nuevoFichero);
						log.info("Fichero creado: {}", nuevoFichero.getFileName());
					} catch (FileAlreadyExistsException e) { 			// 'e' es el nombre de la variable que guarda el error
						log.error("El fichero ya existe.");
					}
				}
				case 2 -> {
					log.info("Introduce el nombre del fichero a mover:");
					String nombreFichero = scanner.nextLine(); //declaramos una cadena de carácteres para el nombre del fichero
					Path fichero = ruta.resolve(nombreFichero);

					if (!Files.exists(fichero)) { //si el fichero no existe, se parará aquí
						log.error("El fichero no existe.");
						break;
					}

					log.info("Introduce la ruta de destino:"); //aquínos pide la ruta de destino
					Path destino = Path.of(scanner.nextLine());

					try {
						Files.move(fichero, destino.resolve(fichero.getFileName()));  //movemos el archivo a la ruta con el nombre en la variable 'fichero'
						//en la línea de arriba, 'fichero' es la ruta completa del fichero que queremos mover y 'destino' la ruta a la que queremos ir. 'destino.resolve' es la junta combinada de la ruta de destino y el nombre del fichero conseguido mediante 'fichero.getFileName'
						log.info("Fichero movido correctamente.");
					} catch (IOException e) {
						log.error("Error al mover el fichero porque la ruta de destino no existe: {}", e.getMessage()); //este error salta si la ruta de destino está mal escrita o no existe
					}
				}
				case 3 -> {
					log.info("Introduce el nombre del fichero a borrar:");
					String nombreFichero = scanner.nextLine();
					Path fichero = ruta.resolve(nombreFichero);

					try {				// deleteIfExists devuelve true si el fichero se borra, false si no existe
						boolean borrado = Files.deleteIfExists(fichero);

						if (borrado) {
							log.info("Elemento borrado correctamente.");
						} else {
							log.warn("El fichero '{}' no existe en esta ruta.", nombreFichero); //si el fichero no existe o ya fue borrado, saltara este error
						}
					} catch (IOException e) {			// aparece si ocurre un error al intentar borrar (funcionaría con los permisos por ejemplo)
						log.error("Error al borrar el fichero: {}", e.getMessage());
					}
				}

				case 4 -> {
					log.info("\n--- Contenido de {} ---", ruta.toAbsolutePath()); // utilizamos '\n' para hacer un salto de línea y mostramos la ruta actual

					try (DirectoryStream<Path> stream = Files.newDirectoryStream(ruta)) {  // 'stream' se utiliza para recorrer toda la carpeta
						boolean vacio = true; 			// booleano para comprobar si la ruta está vacía o no, comienza estando vacía antes de analizar los elementos de la ruta

						for (Path p : stream) {
							vacio = false; 								// si entramos al bucle y hay al menos un elemnto, el booleano pasa a falso
							BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class); // obtenemos los atributos de cada archivo o carpeta

							if (attrs.isDirectory()) { 							// si es una carpeta, imprimimos 'DIR' y su nombre
								log.info("[DIR] {}", p.getFileName());
							} else { 								// si es un archivo, mostramos nombre, tamaño y fecha de última modificación
								log.info("[FILE] {} | {} bytes | Última modif: {}",
										p.getFileName(),
										attrs.size(),
										attrs.lastModifiedTime());
							}
						}

						if (vacio) { // si está vacío, el boolean será true
							log.warn("El directorio está vacío."); // avisamos de que no hay ningún elemento
						}

					} catch (IOException e) {
						// este error saltaría si tratamos de ver una carpeta en la que no tenemos permiso de lectura
						log.error("Error al listar el contenido: {}", e.getMessage());
					}
				}

				case 0 -> {   // si pulsamos 0, acabamos el programa
					log.info("Saliendo del explorador...");
					return;
				}
				default -> log.warn("Opción no válida."); // Advertencia cuando el usuario introduce un número que no sea del 0 al 4
			}

		} while (opcion != 0); //mientras que la opción sea distinta a 0, seguirá apareciando el menú
	}
}
