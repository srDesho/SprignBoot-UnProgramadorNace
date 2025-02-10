package org.mock.persistence.repository;

import org.mock.persistence.entity.PlayerEntity;

import java.util.List;

public interface IPlayerRepository {

    List<PlayerEntity> findAll();
    PlayerEntity findById(Long id);
    void save(PlayerEntity playerEntity);
    void deleteById(Long id);

}
