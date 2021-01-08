package ru.vadmark.petproject.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

import java.util.ArrayList;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${rest.svelte.base.path}")
public class SvelteController {
    private final UserEntityRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        ArrayList<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin_s";
    }


    @GetMapping("/test")
    public String test() {
        return "{\"data\":\"vadmark11\"}";
    }

    @GetMapping("/test1")
    public String test1() {
        return "{\"data\":\"vadmark111111\"}";
    }

    @GetMapping("/test3")
    public String test3() {
        return "{\"data\":\"vadmark3333\"}";
    }

    @GetMapping("/test4")
    public String test4() {
        return "{\"data\":\"vadmark444\"}";
    }
}
