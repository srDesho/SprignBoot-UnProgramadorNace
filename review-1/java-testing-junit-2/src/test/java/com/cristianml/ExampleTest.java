package com.cristianml;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    // 1. Método para sumar dos números
    @Test
    public void sumarTest() {
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


}
