package com.cristianml.lambdas.bifunction;

import java.util.function.BiFunction;

public class BiFunctionExample {
    public static void main(String[] args) {
        // BIFUNCTION
        // Recibe 2 valores y retorna un resultado.

        BiFunction<Integer, Integer, Integer> biFunction = (a, b) -> a +b;

        Integer result = biFunction.apply(15, 15);
        System.out.println("result: ".concat(result.toString()));
    }
}
