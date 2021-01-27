package ru.vadmark.petproject.controller.rest;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;
import ru.vadmark.petproject.repository.model.RegistrationForm;
import ru.vadmark.petproject.service.UserService;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@Api(tags = "Svelte")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/svelte")
public class SvelteController {
    private final UserEntityRepository userRepository;
    private final UserService userService;

    @GetMapping("/auth")
    public ResponseEntity<Boolean> auth() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken && authentication.getPrincipal() instanceof UserEntity) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }

    @GetMapping("/admin")
    public List<UserEntity> admin() {
        log.info("Get admin");
        return userRepository.findAll();
    }

    @DeleteMapping("/admin/user/{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("Delete user: {}.", id);
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(userRepository::delete);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationForm registrationForm) throws
            MethodArgumentNotValidException, NoSuchMethodException {
        log.info("Registration form: {}.", registrationForm);

        String error = userService.registrationUser(registrationForm);
        if (error != null) {
            final MethodParameter methodParameter = new MethodParameter(RegistrationForm.class.getMethod("getConfirmPassword"), -1);
            final BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(registrationForm, "registrationForm");
            ObjectError objectError = new ObjectError("registrationForm", error);
            bindingResult.addError(objectError);
            throw new MethodArgumentNotValidException(methodParameter, bindingResult);
        }
        error = userService.registrationUser(registrationForm);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        if (e.getTarget() instanceof RegistrationForm) {
            BindingResult bindingResult = e.getBindingResult();
            if (!bindingResult.getAllErrors().isEmpty()) {
                return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Invalid Username or password.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public ResponseEntity<UserEntity> test(@RequestParam("id") String id) {
        log.info("id: {}.", id);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(999L);
        userEntity.setUsername("Vadim");
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/settings")
    public ResponseEntity<String> settings(Principal principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().body("Unauthorized user.");
        }

        String imgAsBase64 = userService.getAvatarAsBase64ByUsername(principal.getName());

        return ResponseEntity.ok(imgAsBase64);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(Principal principal, @RequestBody String imgAsBase64) {
        final String base64 = "base64,";
        int base64Index = imgAsBase64.indexOf(base64) + base64.length();
        String imgAsBase64Cat = imgAsBase64.substring(base64Index);
        if (principal == null) {
            return ResponseEntity.badRequest().body("Unauthorized user.");
        }
        UserEntity userEntity = userService.getUserByUsername(principal.getName());
        if (userEntity != null) {
            userEntity.setAvatar(Base64.getMimeDecoder().decode(imgAsBase64Cat));
            userService.saveUser(userEntity);
        }

        return ResponseEntity.ok().build();
    }
}
