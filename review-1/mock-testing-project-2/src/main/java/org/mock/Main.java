package org.mock;

import org.mock.persistence.entity.PlayerEntity;
import org.mock.persistence.repository.PlayerRepositoryImpl;
import org.mock.service.PlayerServiceImpl;

public class Main {
    public static void main(String[] args) {
         PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
         PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

        // System.out.println(playerService.findAll());
        // System.out.println(playerService.findById(1L));

        // playerService.deleteById(2L);
        // System.out.println(playerService.findAll());

        // PlayerEntity player = new PlayerEntity(11L, "Luiz Diaz", "Delantero", "Liverpool");
        // playerService.save(player);
        // System.out.println(playerService.findAll());

        // Estas líneas anteriores sólo fueron para hacer testing de nuestra base de datos artificial y
        // aprender sobre la dependencia que tiene la clase PlayerServiceImpl, esa dependencia se trabaja con Mocks.

        // Nosotros debemos hacer test con Pruebas Unitarias y las dependencias las trabajamos con Mockito, para eso
        // debemos agregar las dependencias junit-jupiter-api y mockito-junit-jupiter

        // Para eso agregamos la dependencia de Mockito JUnit Jupiter a nuestro archivo pom.xml.
        // Creamos nuestra carpeta test en el directorio src y creamos el mismo paquete que tenemos en nuestro main.

        // Creamos la carpeta donde se encuentra la clase que vamos a testear, en este caso la carpeta service con
        // la clase PlayerServiceImpl porque esta es nuestra clase a testear, la clase la creamos con el mismo
        // nombre pero agregando la palabra Test al final, ejemplo:
        // NombreDeMiClaseTest
    }
}