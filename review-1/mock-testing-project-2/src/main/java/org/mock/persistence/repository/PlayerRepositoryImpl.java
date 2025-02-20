package org.mock.persistence.repository;

import org.mock.persistence.entity.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepositoryImpl implements IPlayerRepository {

   /* List<PlayerEntity> playerDatabase = new ArrayList<>(List.of(
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
    ));*/

    private List<PlayerEntity> playerDatabase = new ArrayList<>();

    public void addPlayer(PlayerEntity player) {
        playerDatabase.add(player);
    }

    @Override
    public List<PlayerEntity> findAll() {
        System.out.println(" --> Método findAll real!!");
        return this.playerDatabase;
    }

    @Override
    public PlayerEntity findById(Long id) {
        System.out.println(" --> Método findById real!!");
        return this.playerDatabase.stream()
                .filter(player -> player.getId() == id).findFirst().orElseThrow();
    }

    @Override
    public void save(PlayerEntity playerEntity) {
        System.out.println(" --> Método save real!!");
        if (playerEntity != null) {
            this.playerDatabase.add(playerEntity);
        }
    }

    @Override
    public void deleteById(Long id) {
        System.out.println(" --> Método deleteById real!!");
        this.playerDatabase = this.playerDatabase.stream()
                .filter(player -> player.getId() != id)
                .toList();
    }

    public void clearDatabase() {
        playerDatabase.clear();
    }
}
