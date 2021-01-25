package ru.vadmark.petproject.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import ru.vadmark.petproject.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Markitanov Vadim
 * @since 25.01.2021
 */
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public AuthSuccessHandler() {
        super("/settings");
        setRedirectStrategy(new CustomRedirectStrategy());
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("handle: {}.", request.getRequestURI());
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("Target url: {}.", targetUrl);


        super.handle(request, response, authentication);
    }
}
