package ru.vadmark.petproject.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vadmark.petproject.entity.UserEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Markitanov Vadim
 * @since 20.01.2021
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    //    @Value("${jwt.secret}")
    private String secret = "sdfkgjdfgsdkfj";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserEntity userEntity = (UserEntity) authResult.getPrincipal();
        log.info("Successful authentication for user #{} {}.", userEntity.getId(), userEntity.getUsername());

        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String token = JWT.create()
                .withSubject(userEntity.getUsername())
                .withClaim("userId", userEntity.getId())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC512(secret));

        response.addHeader(AUTHORIZATION, TOKEN_PREFIX + token);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (super.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }

        super.getRememberMeServices().loginSuccess(request, response, authResult);
        if (super.eventPublisher != null) {
            super.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        super.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
