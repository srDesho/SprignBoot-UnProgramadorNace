package com.cristianml.persistence;

import com.cristianml.models.MakerModel;
import com.cristianml.repository.MakerRepository;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

// Esta interfaz es para definir la funcionalidad que va a tener nuestra aplicación.

public interface IMakerDAO {

    List<MakerModel> findAll();

    Optional<MakerModel> findById(Long id);

    void save(MakerModel maker);

    void deleteById(Long id);

}


