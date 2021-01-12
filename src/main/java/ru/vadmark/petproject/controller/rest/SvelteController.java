package ru.vadmark.petproject.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

import java.util.List;
import java.util.Optional;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("${rest.svelte.base.path}")
public class SvelteController {
    private final UserEntityRepository userRepository;

    @GetMapping("/admin")
    public List<UserEntity> admin() {
        log.info("Get admin");
        return userRepository.findAll();
    }

    @DeleteMapping("/admin/user/{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("Delete user: {}.", id);
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(userRepository::delete);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
