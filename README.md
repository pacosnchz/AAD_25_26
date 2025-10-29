# AAD_25_26-Acceso-a-datos

# Gestor de Logs (AAD)

Aplicación de consola en Java (Spring Boot) para gestionar un fichero de logs `app.log`.

## Funcionalidades

1. Añadir un evento al log
    - Guarda fecha y hora en formato `YYYY-MM-DD HH:mm:ss`
    - Ejemplo de línea:  
      `[2025-09-13 18:45:00] Usuario Ana inició sesión`

2. Filtrar eventos por fecha
    - Introduces `YYYY-MM-DD` y te muestra todas las entradas de ese día.

3. Configurar la codificación
    - Por defecto: UTF-8
    - Se puede cambiar a ISO-8859-1 para futuras escrituras.

## Requisitos

- Java 17+
- Maven
- IntelliJ
- Dependencias:
    - Spring Boot
    - Lombok

## Ejecución

Ejecutar la clase `AadApplication`.  
El programa mostrará un menú interactivo en consola.
