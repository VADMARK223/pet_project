package ru.vadmark.petproject.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.handler.AuthFailureHandler;
import ru.vadmark.petproject.repository.model.UserForm;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static ru.vadmark.petproject.config.jwt.JWTUtil.BEARER_;

/**
 * @author Markitanov Vadim
 * @since 20.01.2021
 */
@Slf4j
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserForm userForm = null;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

        super.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/settings"));
        super.setAuthenticationFailureHandler(new AuthFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("request.getHeader(HttpHeaders.CONTENT_TYPE): {}.", request.getHeader(HttpHeaders.CONTENT_TYPE));
        if (MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader(HttpHeaders.CONTENT_TYPE))) {
            try {
                userForm = new ObjectMapper().readValue(request.getInputStream(), UserForm.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserEntity userEntity = (UserEntity) authResult.getPrincipal();
        log.info("Successful authentication for user #{} {}.", userEntity.getId(), userEntity.getUsername());

        String token = JWTUtil.createToken(userEntity);
        log.info("Token: " + token);

        response.addHeader(AUTHORIZATION, BEARER_ + token);
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        if (userForm != null && MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return userForm.getUsername();
        }
        return super.obtainUsername(request);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        if (userForm != null && MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return userForm.getPassword();
        }
        return super.obtainPassword(request);
    }
}
