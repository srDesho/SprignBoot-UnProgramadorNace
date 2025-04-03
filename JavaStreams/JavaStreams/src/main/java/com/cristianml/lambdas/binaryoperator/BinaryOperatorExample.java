package com.cristianml.lambdas.binaryoperator;

import java.util.function.BinaryOperator;

public class BinaryOperatorExample {
    public static void main(String[] args) {
        // BINARY OPERATOR
        // Recibe dos valores del mismo tipo y devuelve un valor del mismo tipo.
        BinaryOperator<Integer> binaryOperator = (a, b) -> a + b;

        System.out.println(binaryOperator.apply(10, 15));
    }
}
