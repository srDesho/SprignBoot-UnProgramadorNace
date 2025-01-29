package com.cristianml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ExampleTest {

    // Creamos una variable global de nuestro objeto que tiene los métodos que estamos testeando.
   private Example example;

    // Hacemos la variable global se instancie para cada método con la anotación @BeforeEach
    @BeforeEach
    public void init() {
        this.example = new Example();
    }

    // 1. Método para sumar dos números
    @Test
    public void testSumar() {
        // Given
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
        int num = 2;
        
        // When
        boolean result = example.checkPositivo(num);

        // Then
        assertTrue(result);
    }

    @Test
    public void testCheckPositivoError() {
        // Given
        int num = -2;

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // Aquí dentro es donde ponemos nuestro código que valida que sea un error
            example.checkPositivo(num);
        });
    }

    // 3. Método para contar el número de letras 'a' en una cadena
    @Test
    public void testContarLetrasA() {
        // Given
        String cadena = "holaATodas";

        // When
        int result = example.contarLetrasA(cadena);

        // Then
        assertNotNull(result);
        assertEquals(2, result);
    }

   

    // Given


    // When


    // Then


}
