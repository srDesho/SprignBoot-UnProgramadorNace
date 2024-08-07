package com.cristianml.DAOvsDTO.percistence.dao.implementation;

import com.cristianml.DAOvsDTO.percistence.UserEntity;
import com.cristianml.DAOvsDTO.percistence.dao.interfaces.IUserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Vamos a hacer nuestra persistencia con DAO manualmente, ya que no vamos a utilizar la interfaz de JpaRepository

@Repository // Con esta anotación indicamos a spring que esta clase trabajará como repositorio.
public class UserDAOImpl implements IUserDAO {

    // Cargamos el administrador de persistencia de Jpa
    @PersistenceContext
    private EntityManager em;

    @Override
    // Transactional es para que indiquemos a jpa que trabaje con la transacción.
    @Transactional(readOnly = true) // readOnly indica que este método será solo de lectura.
    public List<UserEntity> findAll() {
        // Hacemos una consulta que retorna todos los registros de UserEntity
        return this.em.createQuery("SELECT u FROM UserEntity u").getResultList();
    }

    @Override
    @Transactional(readOnly = true) // readOnly indica que este método será solo de lectura.
    public Optional<UserEntity> findById(Long id) {
        // Con esta línea retornamos al usuario encontrado
        return Optional.ofNullable(this.em.find(UserEntity.class, id));
    }

    @Override
    @Transactional // Este no será solo de lectura porque este sí hace operación en la base de datos.
    public void saveUser(UserEntity userEntity) {
        this.em.persist(userEntity);
        this.em.flush(); // Con .flush nos aseguramos que todo se sincronice con la base de datos correctamente.
    }

    @Override
    @Transactional // Este no será solo de lectura porque este sí hace operación en la base de datos.
    public void updateUser(UserEntity userEntity) {
        this.em.merge(userEntity);
    }

    @Override
    @Transactional // Este no será solo de lectura porque este sí hace operación en la base de datos.
    public void deleteUser(UserEntity userEntity) {
        this.em.remove(userEntity);
    }
}
