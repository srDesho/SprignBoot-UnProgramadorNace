package com.cristianml.persistence.impl;

import com.cristianml.models.MakerModel;
import com.cristianml.persistence.IMakerDAO;
import com.cristianml.repository.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component // Define como un bean para spring configure internamente.
public class MakerDAOImpl implements IMakerDAO {

    // Inyectamos nuestro repositorio
    @Autowired
    private MakerRepository makerRepository;

    @Override
    public List<MakerModel> findAll() {
        return (List<MakerModel>) makerRepository.findAll();
    }

    @Override
    public Optional<MakerModel> findById(Long id) {
        return this.makerRepository.findById(id);
    }

    @Override
    public void save(MakerModel maker) {
        this.makerRepository.save(maker);
    }

    @Override
    public void deleteById(Long id) {
        this.makerRepository.deleteById(id);
    }
}
