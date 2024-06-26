package org.mock;

import org.mock.persistence.entity.Player;
import org.mock.persistence.repository.PlayerRepositoryImpl;
import org.mock.service.PlayerServiceImpl;

public class Main {
    public static void main(String[] args) {

        // Creamos la instancia de nuestro servicio simulado con su dependencia, esto para hacer pruebas de que
        // están funcionando correctamente.

        PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

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
        
    }
}