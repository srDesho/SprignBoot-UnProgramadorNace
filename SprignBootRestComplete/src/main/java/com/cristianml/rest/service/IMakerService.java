package com.cristianml.rest.service;

import com.cristianml.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerService {

    void save(Maker maker);
    Optional<Maker> findById(Long id);
    List<Maker> findAll();
    void deleteById(Long id);

}
