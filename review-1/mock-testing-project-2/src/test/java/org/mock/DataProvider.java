package org.mock;

import org.mock.persistence.entity.PlayerEntity;

import java.util.List;

public class DataProvider {

    public static List<PlayerEntity> playerListMock() {

        System.out.println("--> Oteniendo listado player / Mock");

        return List.of(
                new PlayerEntity(1L, "Lionel Messi", "Inter Miami", "Delantero"),
                new PlayerEntity(2L, "Cristiano Ronaldo", "Al-Nassr", "Delantero"),
                new PlayerEntity(3L, "Kylian Mbappé", "Real Madrid", "Delantero"),
                new PlayerEntity(4L, "Erling Haaland", "Manchester City", "Delantero"),
                new PlayerEntity(5L, "Vinicius Jr.", "Real Madrid", "Delantero"),
                new PlayerEntity(6L, "Jude Bellingham", "Real Madrid", "Mediocampista"),
                new PlayerEntity(7L, "Kevin De Bruyne", "Manchester City", "Mediocampista"),
                new PlayerEntity(8L, "Rodri", "Manchester City", "Mediocampista"),
                new PlayerEntity(9L, "Bukayo Saka", "Arsenal", "Delantero"),
                new PlayerEntity(10L, "Phil Foden", "Manchester City", "Mediocampista")
                );
    }

    public static PlayerEntity playerMock() {
        return new PlayerEntity(1L, "Lionel Messi", "Inter Miami", "Delantero");
    }

    public static PlayerEntity newPlayerMock() {
        return new PlayerEntity(10L, "Iker Casillas", "España", "Arquero");
    }
}
