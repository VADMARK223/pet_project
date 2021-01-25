package ru.vadmark.petproject.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws IOException, ServletException {
        final String uri = request.getRequestURI();
        if (!uri.startsWith("/js") && !uri.startsWith("/css") && !uri.startsWith("/images")) {
            log.info("Origin: {}. Request URI: {}. Method:{}.", request.getHeader(HttpHeaders.ORIGIN), uri, request.getMethod());
        }

        ServletResponseUtil.setHeaders(request, response);

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
