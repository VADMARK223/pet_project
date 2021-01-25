package ru.vadmark.petproject.controller.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.vadmark.petproject.controller.LoginController;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;
import ru.vadmark.petproject.repository.model.RegistrationForm;
import ru.vadmark.petproject.repository.model.UserForm;
import ru.vadmark.petproject.service.UserDetailsServiceImpl;
import ru.vadmark.petproject.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final LoginController loginController;

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

    /*@PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody @Valid UserForm userForm) {
        log.info("data: {}.", userForm);
        //userDetailsService.authenticationUser(userForm.getUsername());
        Optional<UserEntity> userEntity = userRepository.findByUsername(userForm.getUsername());
        if (userEntity.isPresent() && bCryptPasswordEncoder.matches(userForm.getPassword(), userEntity.get().getPassword())) {
            log.info("GOOD.");
            //userDetailsService.authenticationUser(userForm.getUsername());
        } else {
            log.info("BAD.");
        }

        return ResponseEntity.ok().build();
    }*/

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationForm registrationForm) throws MethodArgumentNotValidException, NoSuchMethodException {
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
    public ResponseEntity<String> settings() {
        return ResponseEntity.ok("Settings");
    }
}
