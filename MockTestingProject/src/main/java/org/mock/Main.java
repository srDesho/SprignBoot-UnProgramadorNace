package org.mock;

import org.mock.persistence.entity.Player;
import org.mock.persistence.repository.PlayerRepositoryImpl;
import org.mock.service.PlayerServiceImpl;

public class Main {
    public static void main(String[] args) {

        // Creamos la instancia de nuestro servicio simulado con su dependencia, esto para hacer pruebas de que
        // están funcionando correctamente.

        // PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
        // PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

         // System.out.println(playerService.findAll());
         // System.out.println(playerService.findById(1L));

         // playerService.deleteById(1L);
         // System.out.println(playerService.findAll());

         // Player player = new Player(7L, "Luiz Diaz", "Delantero", "Liverpool");
         // playerService.save(player);
         // System.out.println(playerService.findAll());

        // Todo esto que tenemos comentado fue una prueba que hicimos para saber si la dependencia es válida, también
        // para saber si nuestros métodos funcionan correctamente, pero es un test al vuelo, para esto es que
        // necesitamos trabajar con testing Mockito.

        // Para eso agregamos la dependencia de Mockito JUnit Jupiter a nuestro archivo pom.xml.
        // Creamos nuestra carpeta test en el directorio src y creamos el mismo paquete que tenemos en nuestro main.

        // Creamos la carpeta donde se encuentra la clase que vamos a testear, en este caso la carpeta service con
        // la clase PlayerServiceImpl porque esta es nuestra clase a testear, la clase la creamos con el mismo
        // nombre pero agregando la palabra Test al final, ejemplo:
        // NombreDeMiClaseTest
    }
}