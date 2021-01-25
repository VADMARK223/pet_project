package ru.vadmark.petproject.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Markitanov Vadim
 * @since 21.01.2021
 */
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("Authentication failure: " + exception.getLocalizedMessage());
        final String message = "Invalid username or password.";
        super.setDefaultFailureUrl("/login/failure?error=" + message);
        super.onAuthenticationFailure(request, response, exception);
    }
}
