package com.cristianml.rest.service.impl;

import com.cristianml.rest.entities.Maker;
import com.cristianml.rest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class MakerServiceImpl implements IMakerService {

    private final IMakerService makerService;

    @Autowired
    public MakerServiceImpl(IMakerService makerService) {
        this.makerService = makerService;
    }

    @Override
    public void save(Maker maker) {
        this.makerService.save(maker);
    }

    @Override
    public Optional<Maker> findById(Long id) {
        return this.makerService.findById(id);
    }

    @Override
    public List<Maker> findAll() {
        return this.makerService.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.makerService.deleteById(id);
    }
}
