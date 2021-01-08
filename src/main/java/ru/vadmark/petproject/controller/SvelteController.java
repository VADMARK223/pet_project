package ru.vadmark.petproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author: Markitanov Vadim
 * Date: 08.01.2021
 */
@Controller
public class SvelteController {
    @GetMapping("/svelte/users")
    public String index(Model model) {
        model.addAttribute("temp", "123");
        return "svelte_users";
    }
}
