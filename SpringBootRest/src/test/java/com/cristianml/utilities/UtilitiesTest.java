package com.cristianml.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UtilitiesTest {

    @Test
    public void testUtilities() {
        Utilities utilities = new Utilities();
    }

    @Test
    public void testGenerateResponse_Success() {

        // Given
        HttpStatus status = HttpStatus.OK;
        String message = "Successfully.";

        // When
        ResponseEntity<Object> result = Utilities.generateResponse(status, message);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGenerateResponse_ErrorCase() {
        // Given
        HttpStatus status = null; // Esto causará NullPointerException
        String message = "test message";

        // When
        ResponseEntity<Object> result = Utilities.generateResponse(status, message);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

        // Verificar contenido del body en caso de error
        assertTrue(result.getBody() instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) result.getBody();

        assertNotNull(body.get("fecha"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertNotNull(body.get("mensaje")); // Puede ser el mensaje de error o "test message"
    }

    @Test
    public void testGenerateResponse_NullMessage() {
        ResponseEntity<Object> result = Utilities.generateResponse(HttpStatus.OK, null);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof Map);

        Map<?, ?> body = (Map<?, ?>) result.getBody();
        assertNull(body.get("mensaje")); // O el comportamiento que esperes
    }

    @Test
    public void testGenerateResponse_ExceptionCase() {
        ResponseEntity<Object> result = Utilities.generateResponse(null, "error");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) result.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertTrue(body.get("mensaje").toString().contains("Status cannot be null"));
    }
}
