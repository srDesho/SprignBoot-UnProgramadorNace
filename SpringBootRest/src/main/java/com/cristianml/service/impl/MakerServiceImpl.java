package com.cristianml.service.impl;

import com.cristianml.models.MakerModel;
import com.cristianml.persistence.IMakerDAO;
import com.cristianml.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MakerServiceImpl implements IMakerService {

    @Autowired
    private IMakerDAO makerDAO;

    @Override
    public List<MakerModel> findAll() {
        return this.makerDAO.findAll();
    }

    @Override
    public Optional<MakerModel> findById(Long id) {
        return this.makerDAO.findById(id);
    }

    @Override
    public void save(MakerModel maker) {
        this.makerDAO.save(maker);
    }

    @Override
    public void deleteById(Long id) {
        this.makerDAO.deleteById(id);
    }
}
