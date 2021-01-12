package ru.vadmark.petproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@Api(tags = "Admin")
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminController {
    private final UserEntityRepository userRepository;

    @ApiOperation(notes = "Find all user in DB and return admin page.", value = "Get users list.")
    @GetMapping()
    public String admin(Model model) {
        ArrayList<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @ApiOperation("Get user by id.")
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable long id, Model model) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        List<UserEntity> users = new ArrayList<>();
        optionalUser.ifPresent(users::add);
        model.addAttribute("users", users);
        return "admin";
    }

    @ApiOperation("Delete user by id.")
    @PostMapping("/user/{id}")
    public String deleteUser(@PathVariable long id, Model model) {
        log.info("Delete user: {}.", id);
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(userRepository::delete);
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }
}
