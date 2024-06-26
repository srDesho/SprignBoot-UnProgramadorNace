package org.mock.service;

import org.mock.persistence.entity.Player;
import org.mock.persistence.repository.PlayerRepositoryImpl;

import java.util.List;

public class PlayerServiceImpl implements IPlayerService{

    // Inyectamos nuestra persistencia porque al instanciar nuestra clase PlayerServiceImpl siempre va a depender de
    // este Objeto, a esto se le llama DEPENDENCIA,
    private final PlayerRepositoryImpl playerRepository;

    public PlayerServiceImpl(PlayerRepositoryImpl playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public List<Player> findAll() {
        return this.playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return this.playerRepository.findById(id);
    }

    @Override
    public void save(Player player) {
        this.playerRepository.save(player);
    }

    @Override
    public void deleteById(Long id) {
        this.playerRepository.deleteById(id);
    }
}
