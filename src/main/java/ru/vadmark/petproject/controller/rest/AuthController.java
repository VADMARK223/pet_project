package ru.vadmark.petproject.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Markitanov Vadim
 * @since 24.01.2021
 */
@Deprecated
@Slf4j
@RestController
public class AuthController {
    @PostMapping("/auth")
    public ResponseEntity<Boolean> auth(HttpServletRequest request, HttpServletResponse response,
                                        @RequestHeader(value = "test", required = false) String test,
                                        @RequestHeader(value = "Authorization", required = false) String authorization,
                                        @RequestHeader(value = "Content-Type", required = false) String contentType,
                                        @RequestBody(required = false) Object data) {
        log.info(">>>>>>>>>>> " + data);
        log.info("URI: " + request.getRequestURI());
        log.info("Tets header: {}.", test);
        log.info("Authorization header: {}.", authorization);
        log.info("ContentType header: {}.", contentType);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        response.addHeader(AUTHORIZATION, "vad");

        if (authentication instanceof AnonymousAuthenticationToken) {
            log.info("Is Anonymous.");
            return ResponseEntity.ok(false);
        } else {
            log.info("User authentication!");
            return ResponseEntity.ok(true);
        }
    }
}
