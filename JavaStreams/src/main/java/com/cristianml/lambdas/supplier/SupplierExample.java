package com.cristianml.lambdas.supplier;

import java.util.function.Supplier;

public class SupplierExample {
    public static void main(String[] args) {
        // SUPPLIER
        // No recibe ningún valor, pero retorna algo.
        Supplier<String> supplier = () -> "Hello, I'm a supplier.";

        // La forma para llamar un supplier es con .get()
        System.out.println(supplier.get());

    }

}
