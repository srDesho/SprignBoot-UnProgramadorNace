package com.cristianml.service;

import com.cristianml.models.MakerModel;

import java.util.List;
import java.util.Optional;

// Estamos creando una estructura desacoplada, o sea la capa de persistencia sólo se encarga de interactuar con la BD
// y esta capa de Servicio se encarga de la lógica de negocio, ose la lógica de la aplicación.
public interface IMakerService {

    List<MakerModel> findAll();

    Optional<MakerModel> findById(Long id);

    void save(MakerModel maker);

    void deleteById(Long id);

}
