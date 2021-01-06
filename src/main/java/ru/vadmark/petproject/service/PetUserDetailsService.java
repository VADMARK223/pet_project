package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.config.jwt.JwtUtil;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PetUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        String token = jwtUtil.generateToken(user);
        log.info("Generate token: '{}'.", token);

        return user;
    }
}
