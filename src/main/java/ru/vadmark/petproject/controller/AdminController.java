package ru.vadmark.petproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@RestController
public class AdminController {
    @GetMapping("/admin")
    public String admin() {
        return "Hi.";
    }
}
