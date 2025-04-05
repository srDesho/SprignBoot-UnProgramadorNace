package com.cristianml.lambdas.biconsumer;

import java.util.function.BiConsumer;

public class BiConsumerExample {
    public static void main(String[] args) {
        // BICONSUMER
        // Recibe 2 valores y no retorna nada.

        BiConsumer<String, String> biConsumer = (a, b) -> System.out.println(a + " " +  b);
        biConsumer.accept("Hello", "Friend");
    }

}
