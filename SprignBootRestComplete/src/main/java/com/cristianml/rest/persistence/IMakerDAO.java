package com.cristianml.rest.persistence;

import com.cristianml.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerDAO {

    void save(Maker maker);
    Optional<Maker> findById(Long id);
    List<Maker> findAll();
    void deleteById(Long id);
}
