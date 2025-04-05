package com.cristianml.lambdas.predicate;

import java.util.function.Predicate;

public class PredicateExample {
    public static void main(String[] args) {
        // PREDICATE
        // Recibe un valor y retorna un booleano.

        Predicate<String> predicate = (str) ->  str.length() > 5;

        // Para llamar a un predicate usamos el método .test
        boolean result = predicate.test("Hello World");
        System.out.println(result);
    }
}
