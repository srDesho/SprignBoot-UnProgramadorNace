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

    // 7. Método para verificar si un número es primo
    @Test
    public void testEsPrimo() {
        // Given
        int nro = 7;

        // When
        boolean result = example.esPrimo(nro);

        // Then
        assertTrue(result);
    }

    @Test
    public void testEsPrimoMenorOIgualQueUno() {
        // Given
        int nro = 1;

        // When
        boolean result = example.esPrimo(nro);

        // Then
        assertFalse(result);
    }

    @Test
    public void testEsPrimoMayorACuatro() {
        // Given
        int nro = 9;

        // When
        boolean result = example.esPrimo(nro);

        // Then
        assertFalse(result);
    }

    // 8. Método que simula un retraso y retorna un mensaje
    @Test
    public void testMensajeConRetraso() throws InterruptedException {
        // When
        String result = example.mensajeConRetraso();

        // Then
        assertEquals("Listo después de retraso", result);
    }

    // 9. Método para convertir una lista de enteros a lista de strings
    @Test
    public void testConvertirAString() {
        // Given
        List<Integer> nros = List.of(1, 2, 3, 4, 5, 6);

        // When
        List<String> resultList = example.convertirAString(nros);

        // Then
        assertEquals(List.of("1", "2", "3", "4", "5", "6"), resultList);

    }

    // 10. Método que calcula la media de una lista de enteros
    @Test
    public void testCalcularMedia() {
        // Given
        List<Integer> nros = List.of(9, 7, 5);

        // When
        double result = example.calcularMedia(nros);

        // Then
        assertEquals(7, result);
    }

    @Test
    public void testCalcularMediaNull() {
        // Given
        List<Integer> listaNula = null;

        // When Then
        assertThrows(IllegalArgumentException.class, () -> {
            double resultNulo = example.calcularMedia(listaNula);
        });

    }

    @Test
    public void testCalcularMediaEmpty() {
        // Given
        List<Integer> listaVacia = new ArrayList<>();

        // When Then
        assertThrows(IllegalArgumentException.class, () -> {

            double resultVacío = example.calcularMedia(listaVacia);
        });

    }

    // 11. Método para convertir una lista de enteros a lista de strings
    @Test
    public void testConvertirListaAString() {
        // Given
        List<String> nros = List.of("1", "2", "3", "4");

        // When
        String result = example.convertirListaAString(nros);

        // Then
        assertEquals("1,2,3,4", result);
    }

}
