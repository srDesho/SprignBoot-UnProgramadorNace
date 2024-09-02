package com.cristianml.rest.persistence.impl;

import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.persistence.IMakerDAO;
import com.cristianml.rest.repository.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MakerDAOImpl implements IMakerDAO {

    // Implementamos nuestro repositorio Maker
    private final MakerRepository makerRepository;

    @Autowired
    public MakerDAOImpl(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    @Override
    public void save(Maker maker) {
        this.makerRepository.save(maker);
    }

    @Override
    public Optional<Maker> findById(Long id) {
        return this.makerRepository.findById(id);
    }

    @Override
    public List<Maker> findAll() {
        return (List<Maker>) this.makerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {

    }
}
