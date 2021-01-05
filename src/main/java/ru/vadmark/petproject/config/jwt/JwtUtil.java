package ru.vadmark.petproject.config.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vadmark.petproject.entity.UserEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Author: Markitanov Vadim
 * Date: 05.01.2021
 */
@Slf4j
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generates a JWT token containing username as subject, and userId and roles as additional claims.
     * @param userEntity the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(UserEntity userEntity) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());

        final Claims claims = Jwts.claims().setSubject(userEntity.getUsername());
        claims.put("userId", userEntity.getId());
        claims.put("role", userEntity.getRoles());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            throw new RuntimeException("Token expired.");
        } catch (UnsupportedJwtException unsEx) {
            throw new RuntimeException("Unsupported JWT.");
        } catch (MalformedJwtException mjEx) {
            throw new RuntimeException("Malformed JWT.");
        } catch (SignatureException sEx) {
            throw new RuntimeException("Invalid signature.");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token.");
        }
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
