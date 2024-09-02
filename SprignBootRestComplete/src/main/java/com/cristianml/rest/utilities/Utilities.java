package com.cristianml.rest.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utilities {

    // Creamos nuestro método para formatear nuestro ResponseEntity, o sea para que esté personalizado
    // de tipo object para que pueda recibir cualquier tipo, le ponemos el nombre que querramos
    // le pasamos el parámetro HttpStatus para indicar el estado Http que va a retornar
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message) {
        // Creamos un map para indicar la estructura del jason
        Map<String, Object> map = new HashMap<String, Object>();

        // Envolvemos con un Try Catch
        try {
            map.put("fecha", new Date());
            map.put("status", status);
            map.put("mensaje", message);
            // Podemos agregar muchos más valores al map, pero los anteriores son los más necesarios.

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            // Si existe error hacemos lo siguiente
            map.clear(); // Limpiamos el map por si tiene registros anteriores que no nos sirven para la respuesta.
            map.put("fecha", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("mensaje", e.getMessage());

            return new ResponseEntity<Object>(map, status);
        }
    }
}
