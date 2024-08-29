package com.cristianml.rest.service.impl;

import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.persistence.impl.MakerDAOImpl;
import com.cristianml.rest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class MakerServiceImpl implements IMakerService {

    private final MakerDAOImpl makerDAO;

    @Autowired
    public MakerServiceImpl(MakerDAOImpl makerDAO) {
        this.makerDAO = makerDAO;
    }

    @Override
    public void save(Maker maker) {
        this.makerDAO.save(maker);
    }

    @Override
    public Optional<Maker> findById(Long id) {
        return this.makerDAO.findById(id);
    }

    @Override
    public List<Maker> findAll() {
        return this.makerDAO.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.makerDAO.deleteById(id);
    }
}
