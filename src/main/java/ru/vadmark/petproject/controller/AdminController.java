package ru.vadmark.petproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminController {
    private final UserEntityRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        ArrayList<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/user/{id}")
    public String getUser(@PathVariable long id, Model model) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        List<UserEntity> users = new ArrayList<>();
        optionalUser.ifPresent(users::add);
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable long id, Model model) {
        log.info("Delete user: {}.", id);
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(userRepository::delete);
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }
}
