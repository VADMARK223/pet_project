package ru.vadmark.petproject.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Markitanov Vadim
 * @since 20.01.2021
 */
@Slf4j
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws IOException, ServletException {
        String token = getTokenFromRequest(request);
        log.info("Token: " + token);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(getAuthentication(token));
        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        return null;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = JWT.require(Algorithm.HMAC512("sdfkgjdfgsdkfj"))
                .build()
                .verify(token)
                .getSubject();
        log.info("User from token: {}.", username);
        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username, null);
        }
        return null;
    }
}
