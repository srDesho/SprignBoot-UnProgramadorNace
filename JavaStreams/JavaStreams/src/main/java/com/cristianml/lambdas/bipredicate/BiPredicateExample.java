package com.cristianml.lambdas.bipredicate;

import java.util.function.BiPredicate;

public class BiPredicateExample {
    public static void main(String[] args) {
        // BIPREDICATE
        // Recibe dos valores y retorna un booleano

        BiPredicate<Integer, Integer> biPredicate = (a, b) -> a > b;

        boolean result = biPredicate.test(8, 2);
        System.out.println(result);
    }
}
