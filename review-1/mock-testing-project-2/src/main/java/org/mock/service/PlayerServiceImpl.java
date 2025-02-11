package org.mock.service;

import org.mock.persistence.entity.PlayerEntity;
import org.mock.persistence.repository.PlayerRepositoryImpl;

import java.util.List;

public class PlayerServiceImpl implements IPlayerService{

    private PlayerRepositoryImpl playerRepository;

    public PlayerServiceImpl (PlayerRepositoryImpl playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<PlayerEntity> findAll() {
        return this.playerRepository.findAll();
    }

    @Override
    public PlayerEntity findById(Long id) {
        return this.playerRepository.findById(id);
    }

    @Override
    public void save(PlayerEntity playerEntity) {
        this.playerRepository.save(playerEntity);
    }

    @Override
    public void deleteById(Long id) {
        this.playerRepository.deleteById(id);
    }
}
