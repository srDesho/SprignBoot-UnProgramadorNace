package com.cristianml.lambdas.runnable;

// Runnable y Callable se utilizan para concurrencia hilos y Promesas - Future.
public class RunnableExample {
    public static void main(String[] args) {
        // RUNNABLE
        // No recibe ningún valor y no retorna nada, solo ejecuta una tarea.
        Runnable runnable = () -> System.out.println("Executing task...");
        runnable.run();
    }
}
