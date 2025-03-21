package com.cristianml.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utilities {

    public Utilities(){};

    // Creamos nuestro método para formatear nuestro ResponseEntity, o sea para que esté personalizado
    // de tipo object para que pueda recibir cualquier tipo, le ponemos el nombre que querramos
    // le pasamos el parámetro HttpStatus para indicar el estado Http que va a retornar
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String mensaje) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (status == null) {
                throw new NullPointerException("Status cannot be null");
            }

            map.put("fecha", new Date());
            map.put("status", status.value()); // Usar .value() para consistencia
            map.put("mensaje", mensaje);
            return new ResponseEntity<>(map, status);

        } catch (Exception e) {
            map.clear();
            map.put("fecha", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("mensaje", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR); // ¡Corregir esto!
        }
    }
}