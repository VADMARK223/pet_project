package ru.vadmark.petproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author: Markitanov Vadim
 * Date: 31.12.2020
 */
@Slf4j
@Controller
public class UploadController {
    @GetMapping("/upload")
    public ResponseEntity.BodyBuilder upload() {
        log.info("Upload image.");
        return ResponseEntity.ok();
    }
}
