package com.cristianml.repository;

import com.cristianml.models.MakerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends CrudRepository<MakerModel, Long> {

}
