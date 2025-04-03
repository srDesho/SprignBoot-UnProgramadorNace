package com.cristianml.lambdas.callable;

import java.util.concurrent.Callable;

// Callable y Runnable se utilizan para concurrencia, hilos y Promesas - Future
public class CallableExample {
    public static void main(String[] args) {
        // CALLABLE
        // No recibe valores, pero retorna un resultado y puede lanzar una excepción,
        Callable<String> callable = () -> "Task Result";

        try {
            String result = callable.call();
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
