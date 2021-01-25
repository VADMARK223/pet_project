package ru.vadmark.petproject.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.DefaultRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Markitanov Vadim
 * @since 25.01.2021
 */
@Slf4j
public class CustomRedirectStrategy extends DefaultRedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        final String origin = request.getHeader(HttpHeaders.ORIGIN);
        if ("http://localhost:5000".equals(origin)) {
            log.info("Not redirect for client.");
            return;
        }
        super.sendRedirect(request, response, url);
    }
}
