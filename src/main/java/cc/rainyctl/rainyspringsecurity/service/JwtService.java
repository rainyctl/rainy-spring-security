package cc.rainyctl.rainyspringsecurity.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {
    /**
     * Generate a JWT for a user.
     */
    String generateToken(Long userId, String username);

    /**
     * Generate a JWT for a user.
     */
    String generateToken(Long userId, String username, Map<String, Object> claims);

    /**
     * Validate a token (signature + expiration).
     * Returns true if valid, false otherwise.
     */
    boolean validateToken(String token);

    /**
     * Extract username from token.
     */
    String extractUsername(String token);

    /**
     * Extract user id (subject) from token.
     */
    Long extractUserId(String token);

    /**
     * Parse token and return JWT claims.
     */
    Claims extractClaims(String token);
}
