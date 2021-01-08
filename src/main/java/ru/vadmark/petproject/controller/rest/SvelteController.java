package ru.vadmark.petproject.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
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
    public UserEntity test() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(99L);
        userEntity.setUsername("VADMARK");
        return userEntity;
    }
}
