package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.dao.UserRepository;
import ru.vadmark.petproject.model.Role;
import ru.vadmark.petproject.model.User;
import ru.vadmark.petproject.model.UserForm;

import java.util.Collections;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username: {}.", username);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean saveUser(UserForm userForm) {
        if (userRepository.findByUsername(userForm.getName()) != null) {
            return false;
        }

        User user = new User();
        user.setUsername(userForm.getName());

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userRepository.save(user);
        log.info("Save user.");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }
}
