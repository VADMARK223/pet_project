package ru.vadmark.petproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.vadmark.petproject.entity.UserEntity;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
public interface UserEntityRepository extends CrudRepository<UserEntity, Integer> {
    @Override
    @NonNull
    ArrayList<UserEntity> findAll();

    Optional<UserEntity> findByUsername(String value);

    Optional<UserEntity> findById(Long id);
}