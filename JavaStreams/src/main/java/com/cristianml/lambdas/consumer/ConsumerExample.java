package com.cristianml.lambdas.consumer;

// Las funciones LAMBDAS son funciones anónimas que no tienen un nombre definido como las funciones normales,
// permiten realizar tareas que son bien cortas como por ejemplo de una línea. Su sintaxis es trabajando con flecha

import java.util.function.Consumer;

public class ConsumerExample {
    public static void main(String[] args) {

        // CONSUMER
        // Recibe un valor y no retorna nada.
        // Consumer<String> consumer = (param) -> System.out.println(param);

        // Podemos resumir el System.out.println(param), siempre y cuando el parámetro que se recibe es el mismo
        // que se usa dentro del método.
        Consumer<String> consumer = System.out::println;

        consumer.accept("Hello Elliot.");
    }
}