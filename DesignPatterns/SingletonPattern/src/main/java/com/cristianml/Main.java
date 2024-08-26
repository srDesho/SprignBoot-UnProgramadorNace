package com.cristianml;

// Patrón Singleton
// Es un patrón de diseño creacional que nos permite asegurarnos que una clase tenga una sola instancia, a la vez que
// proporciona un punto de acceso global a dicha instancia.

// En este proyecto vamos a hacer un ejemplo en el cual tendremos una clase(que será creada con patrón singleton) que
// se encargará de hacer conexión a una DB y no será necesario estar creando distintas clases que conecten a la DB
// desde otras clases.
public class Main {
    public static void main(String[] args) {

        // Hacemos ejemplo creando la instancia de la clase DatabaseConector
        DatabaseConector databaseConector = DatabaseConector.getInstance();
        System.out.println( "databaseConector" + databaseConector);
        
        // Creamos otro objeto con distinto nombre para ver que hace la misma referencia al objeto DatabaseConector
        // porque está definido con patrón singleton y este solo permite una sola instancia.
        DatabaseConector databaseConector2 = DatabaseConector.getInstance();
        System.out.println("databaseConector2 = " + databaseConector2);

    }
}