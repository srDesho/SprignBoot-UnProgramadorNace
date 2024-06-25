package com.cristianml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tipos y conceptos de los assertions.

// assertEquals: Evaluar un valor esperado con un valor actual.
// assertTrue: Valida que tengamos un resultado true.
// assertFalse: Valida que tengamos un resultado false.
// assertNotNull: Valida que el objeto respuesta no sea null.
// assertInstanceOf: Evaluamos el tipo de objeto.
// assertThrows: Sirve para validar excepciones.

// Hay que saber que siempre por defecto los tests deben pasar siempre aprueban.
// Los test nunca devuelven nada siempre son void sus métodos.

public class Example2Test {

    // Creamos una Variable global de nuestro objeto que tiene los métodos que estamos testeando
    private Example2 example;

    // Hacemos que la variable global se instancie para cada método con la anotación @BeforeEach
    @BeforeEach
    public void init() {
        this.example =  new Example2();
    }

    // Ejercicio 1
    @Test
    public void testSumar() {
        // Estructura que se suele utilizar bastante en test unitarios:
        // Given - Teniendo
        int numberA = 3;
        int numberB = 3;

        // When - Cuando
        int result = example.sumar(numberA, numberB);

        // Then - Entonces
        assertNotNull(result);
        assertEquals(6, result);
        assertInstanceOf(Integer.class, result);
    }




}