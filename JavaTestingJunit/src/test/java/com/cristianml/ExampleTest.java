package com.cristianml;

// Agregamos el paquete de ass
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    // Creamos el método con el mismo nombre pero añadiendo la palabra test al comienzo.
    // Agregamos la anotación @Test

    // Hay que saber que siempre por defecto los tests deben pasar siempre aprueban.
    @Test
    public void testSumar() {
        Example example = new Example();
        int result = example.sumar(8, 8);
        // Hacemos la prueba con el Assertions
        // El asserEquals es para comparar el valor esperado con el valor actual.
        // Assertions.assertEquals(17, result); // Si son iguales pues pasa la prueba

        // Hacemos lo mismo pero sin agregar la clase Assertions, ya que la importamos arriba como static para
        // no estar repitiéndola a cada rato.
        assertEquals(16, result); // Si son iguales pues pasa la prueba
        assertTrue(16 == 16); // Más abajo se explica el concepto
        assertInstanceOf(Integer.class, result); // Más abajo se explica el concepto
    }

    // Tipos y conceptos de los assertions.

    // assertEquals: Evaluar un valor esperado con un valor actual.
    // assertTrue: Valida que tengamos un resultado true.
    // assertFalse: Valida que tengamos un resultado false.
    // assertNotNull: Valida que el objeto respuesta no sea null.
    // assertInstanceOf: Evaluamos el tipo de objeto.
    // assertThrows: Sirve para validar excepciones.









}
