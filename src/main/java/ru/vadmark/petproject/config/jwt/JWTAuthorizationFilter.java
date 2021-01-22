package ru.vadmark.petproject.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vadmark.petproject.service.UserDetailsServiceImpl;

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
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws IOException, ServletException {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        log.info("Origin: {}. Request URI: {}. Method:{}.", origin, request.getRequestURI(), request.getMethod());

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "OPTIONS, POST, GET, DELETE");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Authorization");

        final String token = getTokenFromRequest(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        log.info("Token: " + token);
        try {
            String username = JWTUtil.getSubjectByToken(token);
            log.info("Username from token: {}.", username);
            userDetailsService.authenticationUser(username);
        } catch (JWTVerificationException jwtException) {
            log.error("Jwt decoder error: {}.", jwtException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT exception:" + jwtException.getMessage());
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
