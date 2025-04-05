package com.cristianml.lambdas.function;

import java.util.function.Function;

public class FunctionExample {

    public static void main(String[] args) {
        // FUNCTION
        // Recibe un valor y retorna un resultado.

        // En los argumentos dentro del operador diamante PRIMER va el tipo de valor que va a reciber,
        // SEGUNDO va el tipo de valor que se va a retornar.
        Function<Integer, String> function = (num) ->  "El valor es " + num;

        // Para llamar a la función lo hacemos con apply()
        String result = function.apply(50);
        System.out.println(result);
    }

}
