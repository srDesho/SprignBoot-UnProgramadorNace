package com.cristianml.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// ¿Qué es el stream?
// Es un flujo de datos, que va pasando una línea de ensamblaje como en una tubería y se va operando con
// ellos uno por uno.
public class StreamExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Cristian", "Pulpito", "Juan", "Mercedes", "Pepito", "Daniel", "Pedro");
        List<String> namesDuplicates = Arrays.asList("Cristian", "Juan", "Juan", "Pepito", "Pepito", "Pepito", "Pepito");
        // Con el método .stream() es como creamos nuestra línea de ensamblaje.
        // En los streams existen operadores finales y no finales
        // Los finales son los que siempre terminan el flujo.
        // Los no finales son los que no terminan su flujo y se puede seguir operando en ellos.

        // names.stream().forEach(System.out::println); // forEach es un operador final.

        // Operador filter(): Filtra los elementos que cumplen una condición.
        // filter() es un operador no final.
        /*names.stream()
                .filter(name -> name.length() > 6) // al ser no final siempre se debe terminar con un operador final
                .forEach(System.out::println);*/

        // Operador map(): Transforma los elementos aplicando una función.
        /*names.stream()
                .map(name -> name.toUpperCase()) // En este caso podemos resumir aún más:
                .map(String::toLowerCase) // Esto mucho más corto que la línea anterior y estamos poniendo tod0 en minúscula.
                // También podemos meter un filtro
                .filter(name -> name.startsWith("p"))
                .forEach(System.out::println);*/

        // Operador sorted(): Ordena los elementos desordenados del stream
       /* names.stream()
                .sorted()
                .forEach(System.out::println);*/

        // Operador forEach(): Aplica una acción a cada elemento.
        /*names.stream()
                .forEach(
                        (name) -> {
                            // Aquí podemos hacer varias operaciones:
                            // Consultas a la base de datos.
                            // Etc...
                        }
                );*/

        // Operador reduce(): Combina todos los elementos en un solo valor.
        /*String result = names.stream()
                // Debemos poner el primer elemento un identificador, pero podemos dejarlo vacío,
                .reduce( "", (a, b) -> a + " " + b);

        System.out.println(result);*/

        // Operador collect(): Recoge todos los elementos dentro de una colección.
        /*List<String> result = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList()); // Esta línea podemos reemplazarla directamente con toList() desde java 10
                //.toList();

        result.stream().forEach(System.out::println);*/

        // Operador distinct(): Elimina los elementos duplicados.
        /*namesDuplicates.stream()
                .distinct()
                .forEach(System.out::println);*/

        // Operador limit(): Liminita el número de elementos procesados.
        // Por ejemplo si tengo 7 elementos en mi tabla y sólo quiero trabajar con los primeros 4 pues hago lo siguiente.
        /*names.stream()
                .limit(4)
                .forEach(System.out::println);*/

        // Operador skip(): Omite un número específico de elementos.
        // Si queremos saltarnos los primeros 3 elementos:
        /*names.stream()
                .skip(3)
                .forEach(System.out::println);*/

        // Operador anyMatch(): Verifica si algún elemento cumple una condición.
        /*boolean result = names.stream()
                .anyMatch(name -> name.startsWith("D"));

        System.out.println(result);*/

        // Operador allMatch(): Verifica si todos los elementos cumplen una condición.
        /*boolean resutl = names.stream()
                .allMatch(name -> name.startsWith("P"));
        System.out.println(resutl);*/

        // Operador noneMatch(): Verifica si ningún elemento cumple una condición.
        boolean result = names.stream()
                .noneMatch(name -> name.length() == 10);
        System.out.println(result);
    }
}
