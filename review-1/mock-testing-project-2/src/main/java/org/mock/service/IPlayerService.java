package org.mock.service;

import org.mock.persistence.entity.PlayerEntity;

import java.util.List;

public interface IPlayerService {

    List<PlayerEntity> findAll();
    PlayerEntity findById(Long id);
    void save(PlayerEntity playerEntity);
    void deleteById(Long id);


}
