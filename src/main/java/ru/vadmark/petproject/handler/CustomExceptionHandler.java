package ru.vadmark.petproject.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Markitanov Vadim
 * @since 21.01.2021
 */
@Deprecated
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<String> handler(final UsernameNotFoundException exception) {
        log.info("exception: {}.", exception.getMessage());
        return ResponseEntity.ok("AAAAAAA");
    }
}
