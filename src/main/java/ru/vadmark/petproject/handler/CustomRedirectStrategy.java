package ru.vadmark.petproject.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.DefaultRedirectStrategy;
import ru.vadmark.petproject.config.property.ProjectProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Markitanov Vadim
 * @since 25.01.2021
 */
@RequiredArgsConstructor
public class CustomRedirectStrategy extends DefaultRedirectStrategy {
    private final ProjectProperties properties;

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        final String origin = request.getHeader(HttpHeaders.ORIGIN);

        if (properties.getClientUrl().equals(origin)) {
            return;
        }
        super.sendRedirect(request, response, url);
    }
}
