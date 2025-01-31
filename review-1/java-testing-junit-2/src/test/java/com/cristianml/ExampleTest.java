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

    // 4. Método que retorna un valor booleano si la lista contiene el elemento
    @Test
    public void testContieneElemento() {
        // Given
        List<String> herramientas = List.of(new String[]{"martillo", "alicate", "destornillador"});
        String elementoA = "pala";
        String elementoB = "martillo";

        // When
        boolean resultA = example.contieneElemento(herramientas, elementoA);
        boolean resultB = example.contieneElemento(herramientas, elementoB);

        // Then
        assertFalse(resultA);
        assertTrue(resultB);

    }

    // 5. Método para revertir una cadena
    @Test
    public void testRevertirCadena() {
        // Given
        String cadena = "hola";

        // When
        String result = example.revertirCadena(cadena);

        // Then
        assertEquals("aloh", result);
    }

    // 6. Método que calcula el factorial de un número
    @Test
    public void testFactorial() {
        // Given
        int nro = 4;
        int nroError = -10;

        // When
        long result = example.factorial(nro);

        // Then
        assertEquals(24, result);
        assertThrows(IllegalArgumentException.class, () -> {
            example.factorial(nroError);
        });
    }

    @Test
    public void testFactorialError() {
        // Given
        int nro = -50;

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            example.factorial(nro);
        });
    }

    // Given


    // When


    // Then


}
