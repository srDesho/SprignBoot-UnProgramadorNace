package com.cristianml;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    // 1. Método para sumar dos números
    @Test
    public void testSumar() {
        // Given
        Example example = new Example();
        int numberA = 8;
        int numberB = 8;

        // When
        int result = example.sumar(numberA, numberB);

        // Then
        assertNotNull(result);
        assertEquals(16, result);
        assertInstanceOf(Integer.class, result);
    }

    // 2. Método que lanza una excepción si el argumento es negativo
    @Test
    public void testCheckPositivo() {
        // Given
        Example example = new Example();
        int num = 2;

        // When
        boolean result = example.checkPositivo(num);

        // Then
        assertTrue(result);
    }

    @Test
    public void testCheckPositivoError() {

        // Given
        Example example = new Example();
        int num = -2;

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // Aquí dentro es donde ponemos nuestro código que valida que sea un error
            example.checkPositivo(num);
        });
    }

    // Given


    // When


    // Then

}
