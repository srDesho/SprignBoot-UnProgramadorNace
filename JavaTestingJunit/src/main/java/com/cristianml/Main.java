package com.cristianml;

public class Main {

    // 1. Para hacer pruebas Unitarias con jUnit, debemos agregar la dependencia de JUnit Jupiter Engine

    // En la carpeta "test" de nuestro proyecto es donde se almacena todo el testing, y es aquí donde se debe
    // tener las mismas carpetas que tiene nuestra carpeta "main" porque es un principio de las unit test.
    // Entonces debemos crear las mismas carpetas y paquetes manualmente, ejemplo las carpetas java y resources,
    // también creamos los paquetes de la carpeta java.com.cristianml.

    // 2. Cuál es el objetivo de hacer pruebas.
    // En los test nosotros vamos a trabajar con un "Valor esperado" y un "valor Actual"
    // - Valor esperado: Es lo que espero que devuelva mi método.
    // - Valor actual: Es lo que el método original devuelve.

    // Ejemplo con suma de 2 valores: 3, 3
    // Mi valor esperado será 6 porque eso es lo que creo y espero a que me devuelva el método.
    // Mi valor esperado será también 6 porque eso es lo que verdaderamente devuelve el método original.
    // En este caso como ambos coinciden entonces el test es aprobado y va a pasar.

    // Para hacer un test, debemos tener creada nuestras clases con sus respectivos métodos, las clases que
    // necesitemos que sean testeadas vamos a crearlas en la misma dirección pero en nuestra carpeta "test".
    // Creamos la clase con el nombre agregando la palabra test al final "NombreDeClaseTest"
    // Creamos el método que queremos testear con el mismo nombre del método, pero al comienzo escribimos test:

    // Hay que saber que siempre por defecto los tests deben pasar siempre aprueban.
    // Los test nunca devuelven nada siempre son void sus métodos.
    public static void main(String[] args) {


    }
}