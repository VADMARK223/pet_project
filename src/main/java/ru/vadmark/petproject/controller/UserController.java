package ru.vadmark.petproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vadmark.petproject.dao.UserRepository;
import ru.vadmark.petproject.model.User;

import java.util.ArrayList;

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
    public String users(Model model) {
        ArrayList<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}
