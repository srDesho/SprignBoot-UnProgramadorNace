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
    }
}