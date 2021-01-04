package ru.vadmark.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadmark.petproject.entity.RoleEntity;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
