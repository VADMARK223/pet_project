package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.entity.RoleEntity;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.model.UserForm;
import ru.vadmark.petproject.repository.RoleEntityRepository;
import ru.vadmark.petproject.repository.UserEntityRepository;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    public static final String ROLE_USER = "ROLE_USER";

    @Value("${registration.autologin: false}")
    private boolean autologin;

    private final UserEntityRepository userRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PetUserDetailsService petUserDetailsService;

    public boolean saveUser(UserForm userForm) {
        if (userRepository.findByUsername(userForm.getName()) != null) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(userForm.getName());

        RoleEntity roleEntity = roleEntityRepository.findByName(ROLE_USER);
        if (roleEntity == null) {
            log.error("Role '{}' not exists.", ROLE_USER);
            throw new RuntimeException("Role '" + ROLE_USER + "' not found.");
        }
        user.getRoles().add(roleEntity);
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userRepository.save(user);
        log.info("Save user.");

        if (autologin) {
            log.info("Autologin.");
            UserDetails userDetails = petUserDetailsService.loadUserByUsername(user.getUsername());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        return true;
    }

    public UserEntity getPrincipalUserEntity() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Principal: '{}'.", principal);
        if (principal instanceof UserDetails) {
            return (UserEntity) principal;
        }

        return null;
    }

    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
