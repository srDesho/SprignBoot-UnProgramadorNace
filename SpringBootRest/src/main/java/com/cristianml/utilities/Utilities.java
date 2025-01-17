package com.cristianml.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utilities {

    // Creamos nuestro método para formatear nuestro ResponseEntity, o sea para que esté personalizado
    // de tipo object para que pueda recibir cualquier tipo, le ponemos el nombre que querramos
    // le pasamos el parámetro HttpStatus para indicar el estado Http que va a retornar
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String mensaje) {
        // Creamos un Map para indicar la estructura del json
        Map<String, Object> map = new HashMap<String, Object>();

        // Envolvemos con un try catch
        try {

            map.put("fecha", new Date());
            map.put("status", status);
            map.put("mensaje", mensaje);
            // Podemos agregar muchos mas valores que necesitemos al map.

            return new ResponseEntity<Object>(map, status);

        } catch (Exception e) {
            // Si hay error pues hacemos lo siquiente
            map.clear(); // Limpiamos el map, por si se cargó algo antes del error
            map.put("fecha", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("mensaje", e.getMessage());
            // Podemos agregar muchos mas valores que necesitemos al map.

            return new ResponseEntity<Object>(map, status);
        }
    }

}