package io.github.nicolasyazman.teamtaskmanager.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import static org.assertj.core.api.Assertions.*;

import java.security.Key;
import java.util.Date;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_shouldReturnValidJwt() {
        String email = "test@example.com";

        String token = jwtService.generateToken(email);

        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo(email);
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        boolean isValid = jwtService.isTokenValid(token, email);

        assertThat(isValid).isTrue();
    }

    @Test
    void isTokenValid_shouldReturnFalseForInvalidEmail() {
        String token = jwtService.generateToken("test@example.com");

        boolean isValid = jwtService.isTokenValid(token, "wrong@example.com");

        assertThat(isValid).isFalse();
    }

    @Test
    void extractUsername_shouldReturnCorrectEmail() {
        String email = "sample@mail.com";
        String token = jwtService.generateToken(email);

        String extractedEmail = jwtService.extractUsername(token);

        assertThat(extractedEmail).isEqualTo(email);
    }
    
    @Test
    void isTokenValid_shouldReturnFalseWhenTokenIsExpiredButEmailMatches() {
        String email = "user@example.com";

        // Create a signing key (same logic as in JwtService)
        Key key = Keys.hmacShaKeyFor(
            "in-production-environment-this-should-be-replaced-by-an-environment-variable-with-your-super-secret-key-of-at-least-32-characters"
            .getBytes()
        );

        // Create an expired token (issued and expired 1 hour ago)
        Date now = new Date();
        Date issuedAt = new Date(now.getTime() - 3600_000);  // 1 hour ago
        Date expiredAt = new Date(now.getTime() - 1800_000); // 30 minutes ago

        String expiredToken = Jwts.builder()
            .setSubject(email)
            .setIssuedAt(issuedAt)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        // Act
        assertThat(jwtService.isTokenValid(expiredToken, email)).isFalse();

    }
}