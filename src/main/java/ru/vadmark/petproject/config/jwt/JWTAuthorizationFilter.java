package ru.vadmark.petproject.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vadmark.petproject.service.UserDetailsServiceImpl;
import ru.vadmark.petproject.util.ServletResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Markitanov Vadim
 * @since 20.01.2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Value("${server.port}")
    private String serverPort;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws IOException, ServletException {
        final String ORIGIN = request.getHeader(HttpHeaders.ORIGIN);
        final String URI = request.getRequestURI();
        final String METHOD = request.getMethod();
        final String TOKEN = getTokenFromRequest(request);
        final String LOCALHOST = "http://localhost:" + serverPort;

        if (ORIGIN == null || LOCALHOST.equals(ORIGIN)) {
            chain.doFilter(request, response);
            return;
        }

        log.info("From:{}. URI({}):{}. Token:'{}'", ORIGIN, METHOD, URI, TOKEN);

        ServletResponseUtil.setHeaders(request, response);

        if (HttpMethod.OPTIONS.matches(METHOD)) {
            log.info("Skip filter for OPTIONS method.");
            chain.doFilter(request, response);
            return;
        }

        if (TOKEN == null || TOKEN.equals("null")) {
            if (!"/svelte/auth".equals(URI) && !"/svelte/login".equals(URI) && !"/svelte/registration".equals(URI)) {
                final String ERROR_MESSAGE = "Token not found.";
                log.warn(ERROR_MESSAGE);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_MESSAGE);
            }
            chain.doFilter(request, response);
            return;
        }
        try {
            userDetailsService.authenticationUser(JWTUtil.getSubjectByToken(TOKEN));
        } catch (JWTVerificationException jwtException) {
            final String jwtErrorMessage = String.format("Jwt decoder error: '%s'.", jwtException.getMessage());
            log.warn(jwtErrorMessage);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, jwtErrorMessage);
        } finally {
            chain.doFilter(request, response);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith(JWTUtil.BEARER_)) {
            return bearer.substring(7);
        }

        return null;
    }
}
