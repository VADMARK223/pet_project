package ru.vadmark.petproject.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vadmark.petproject.model.User;

import java.util.ArrayList;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    @Override
    ArrayList<User> findAll();

    User findByUsername(String value);
}
