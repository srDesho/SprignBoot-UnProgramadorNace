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

    // Ejercicio 2
    // Aquí aprenderemos sobre la cobertura, o sea qué tanto vamos a cubrir el test de nuestro método.
    // Cuando tenemos condiciones(if) en nuestro método, quiere decir que trabaja con ramas(branch) es decir
    // que el código puede tomar diferentes caminos y entregar diferentes resultados.

    // Para medir la cobertura debemos agregar el plugin llamado jacoco en maven.
    // Para ver la cobertura de nuestro proyecto, una vez instalado jacoco, clic en maven de la parte derecha
    // en lifecycle damos doble clic en test y se cargará automáticamente, luego nos dirigimos a nuestra
    // carpeta target del proyecto, abrimos la carpeta site y ejecutamos el index en nuestro navegador.
    // En el navegador veremos la cobertura, buscamos la clase que queremos revisar y clic en el método.
    // Veremos partes del código con distintos colores.

    // VERDE: Lo que se vea en Verde quiere decir que está cubierto por test.
    // AMARILLO: Significa que está cubierto parcialmente, o sea no está cubierto del todo.
    // ROJO: Que no está siendo testeado.

    // También debemos manejar lo que es el umbral, en la parte donde agregamos el plugin en el pom, la parte de
    // COVEREDRATIO: aquí es donde definimos el umbral del proyecto, lo recomendado en la industria
    // es de 0.85 hasta 0.95 (85% hasta 95%) nunca se recomienda poner 1 porque sería el 100% y es complicado.
    // Para ver el porcentaje que llevamos de nuestro test, al inicio al abrir el index, tenemos la parte de la
    // tabla donde tiene como título "Cov" es aquí donde nos indica cuánto porcentaje vamos cubriendo en total.
    // Si no llegamos a cubrir la cantidad que definimos, pues no nos dejará compilar nuestra aplicación
    // ni mucho menos podrá ser desplegada de esa manera.
    @Test
    public void testCheckPositivo() {
        // Given - Teniendo
        int number = 4;

        // When - Cuando
        boolean result = example.checkPositivo(number);

        // Then - Entonces
        assertTrue(result);
        assertInstanceOf(Boolean.class, result); // Esto es redundante, pero veamos que sí se puede hacer.
    }

    // Hacemos otro test para el mismo ejercicio porque necesitamos cubrir el otro camino de nuestro método.
    // Agregamos error al final, porque eso es lo que estamos esperando en este camino.
    @Test
    public void testCheckPositivoError() {
        // Aquí en nuestra estructura no es necesario tener la parte del When porque es así cuando se trabaja con errores
        // Given - Teniendo
        int number = -2;

        // THEN
        // Usamos el método assertThrows y pasamos como parámetro nuestra exception que usamos en el método.
        assertThrows(IllegalArgumentException.class, () -> {
            example.checkPositivo(number);
        });

        // Para volver a revisarlo en el index.html, vamos en maven de la parte derecha, doble clic en clean,
        // luego doble clic en test y abrimos el index.html de la carpeta del directorio target.
        // Veremos que ta se marcó todo el método en verde, quiere decir que ta está cubierto del todo.

    }

    // Ejercicio 3
    @Test
    public void testContarLetrasA() {
        // Given
        String cadena = "unprogramadornace";

        // When
        int result = example.contarLetrasA(cadena);

        // Then
        assertNotNull(result);
        assertEquals(3, result);
    }

    // Ejercicio 4
    @Test
    public void testContieneElemento() {
        // Given
        List<String> countries = List.of("Bolivia", "España", "Colombia");
        String country = "Bolivia";

        // When
        boolean result = example.contieneElemento(countries, country);

        // Then
        assertTrue(result);
    }

    // Ejercicio 5
    @Test
    public void testRevertirCadena() {
        // Given
        String cadena = "perro";

        // When
        String result = this.example.revertirCadena(cadena);

        // Then
        assertEquals("orrep", result);
    }

    // Ejercicio 6
    @Test
    public void testFactorial() {
        // Given
        int number = 4;

        // When
        long result = this.example.factorial(number);

        // Then
        assertEquals(24, result);
    }

    // Camino del error, exception en el método
    @Test
    public void testFactorialError() {
        // Given
        int  number = -4;

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            example.factorial(number);
        });
    }

    // Ejercicio 7
    @Test
    public void testEsPrimo() {
        // Given
        int number = 3;

        // When
        boolean result = this.example.esPrimo(number);

        // Then
        assertTrue(result);
    }

    // Verificamos que tenga que ser mayor a 4 porque así está el método
    @Test
    public void testEsPrimoMayorACuatro() {
        // Given
        int number = 7;

        // When
        boolean result = this.example.esPrimo(number);

        // Then
        assertTrue(result);
    }

    // camino de cuando es menor o igual a 1
    @Test
    public void testEsPrimoMenorAUno() {
        // Given
        int number = 1;

        // When
        boolean result = this.example.esPrimo(number);

        // Then
        assertFalse(result);
    }

    // camino cuando es mayor que uno pero no es factorial
    @Test
    public void testNoEsPrimo() {

        // Given
        int number = 8;

        // When
        boolean result = this.example.esPrimo(number);


        // Then
        assertFalse(result);

    }

    // Ejercicio 8
    @Test
    // No hay problemas con agregar throws en nuestros tests
    public void testMensajeConRetraso() throws InterruptedException {
        // Given
        // En este caso no necesito escribir nada aquí porque mi método no recibe parámetros

        // When
        String result = this.example.mensajeConRetraso();

        // Then
        // En este ejercicio se está usando la clase Thread que es para pausar por segundos una ejecución.
        // Esto no se testea porque no afecta en nada, lo que sí debemos testear es el retorno, y este método nos
        // está retornando una cadena de texto.
        assertEquals("Listo después de retraso", result);
    }

    // Ejercicio 9
    @Test
    public void testConvertirAString() {
        // Given
        List<Integer> numberList = List.of(1, 2, 3, 4, 5);

        // When
        List<String> result = this.example.convertirAString(numberList);

        // Then
        assertEquals(List.of("1", "2", "3", "4", "5"), result);
    }

    // Ejercicio 10
    @Test
    public void testCalcularMedia() {
        // Given
        List<Integer> lista = List.of(3,3,3);

        // When
        double resutl = this.example.calcularMedia(lista);

        // Then
        assertEquals(3, resutl);
    }

    @Test
    public void testCalcularMediaNull() {
        // Given
        List<Integer> lista = null;

        // Ya sabemos que en casos de arrojar throws (errores) el When va junto con Then
        // When - Then
        assertThrows(IllegalArgumentException.class, () -> {
            this.example.calcularMedia(lista);
        });
    }

    @Test
    public void testCalcularMediaEmpty() {
        // Given
        List<Integer> lista = Collections.emptyList();

        // Ya sabemos que en casos de arrojar throws (errores) el When va junto con Then
        // When - Then
        assertThrows(IllegalArgumentException.class, () -> {
            this.example.calcularMedia(lista);
        });
    }

    // Ejercicio 11
    @Test
    public void testConcertirListaAString() {
        // Given
        List<String> listaNumeros = List.of("1", "2", "3");

        // When
        String lista = this.example.convertirListaAString(listaNumeros);

        // Then
        assertEquals("1,2,3", lista);

    }
}