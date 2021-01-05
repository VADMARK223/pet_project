package ru.vadmark.petproject.config.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@Slf4j
@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String username) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
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
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
