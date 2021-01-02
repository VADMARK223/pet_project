package ru.vadmark.petproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.vadmark.petproject.dao.UserRepository;
import ru.vadmark.petproject.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public String getUsers(Model model) {
        ArrayList<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable int id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        List<User> users = new ArrayList<>();
        optionalUser.ifPresent(users::add);
        model.addAttribute("users", users);
        return "users";
    }
}
