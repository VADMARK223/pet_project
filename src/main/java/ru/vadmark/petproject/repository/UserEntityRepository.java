package ru.vadmark.petproject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vadmark.petproject.entity.UserEntity;

import java.util.ArrayList;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
public interface UserEntityRepository extends CrudRepository<UserEntity, Integer> {
    @Override
    ArrayList<UserEntity> findAll();

    UserEntity findByUsername(String value);
}