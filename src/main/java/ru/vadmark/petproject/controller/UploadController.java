package ru.vadmark.petproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

import java.util.Base64;


/**
 * Author: Markitanov Vadim
 * Date: 31.12.2020
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class UploadController {

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // 1 MB 1*1024*1024
        commonsMultipartResolver.setMaxUploadSize(Long.MAX_VALUE);
        return commonsMultipartResolver;
    }


    private final UserEntityRepository userEntityRepository;

    @GetMapping("/upload")
    public String upload(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Principal: {}.", principal);
        byte[] rawAvatar = null;

        if (principal instanceof UserDetails) {
            UserEntity userEntity = userEntityRepository.findByUsername(((UserDetails) principal).getUsername());
            rawAvatar = userEntity.getAvatar();
        }

        String imgAsBase64 = rawAvatar == null ? "" : new String(Base64.getEncoder().encode(rawAvatar));
        model.addAttribute("imgAsBase64", "data:image/jpeg;base64," + imgAsBase64);

        return "upload";
    }

    @PostMapping("/upload")
    public String postUpload(@RequestParam("imageToUpload") MultipartFile multipartFile, Model model) {
        log.info("multipartFile: {}.", multipartFile);
        model.addAttribute("imgAsBase64", "asdas");
        return "upload";
    }
}
