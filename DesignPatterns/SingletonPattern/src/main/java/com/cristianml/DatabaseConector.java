package com.cristianml;

// El patrón singleton tiene 3 reglas fundamentales para se implementado.

/**
 * Rules:
 * 1) Debemos tener un constructo privado / We need to have an empty constructor.
 * 2) Debemos tener un atributo privado estático / We need to have a private static attribute.
 * 3) Debemos tener un método estático que devuelva la instancia / We need to have a static method that returns the instance.
 */
public class DatabaseConector {

    // Creamos el atributo privado estático
    // Es común que otros programadores le llamen también "instance" a este atributo.
    private static DatabaseConector databaseConector;

    // Creamos nuestro constructo privado como lo dice la primera regla, esto para que sea restringido y no se pueda
    // instanciar.
    private DatabaseConector() {
        System.out.println("Simulando la creación del objeto");
    }

    // Creamos el método estático que devuelva la instancia.
    // Es muy común que este método se llame "getInstance".
    private static DatabaseConector getInstance() {
        // Debemos tener una condición para validar nuestra instancia.
        if (databaseConector == null) {
            databaseConector = new DatabaseConector(); // Podemos hacer uso del constructo porque es privado solo para la clase.
        }
        return databaseConector;
    }

}
