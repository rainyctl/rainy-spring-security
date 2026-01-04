package cc.rainyctl.rainyspringsecurity.service;

import java.util.Map;

public interface JwtService {
    /**
     * Generate a JWT for a username with no extra claims.
     */
    String generateToken(String username);

    /**
     * Generate a JWT for a username with extra custom claims.
     */
    String generateToken(String username, Map<String, Object> claims);

    /**
     * Validate a token (signature + expiration).
     * Returns true if valid, false otherwise.
     */
    boolean validateToken(String token);

    /**
     * Extract username (subject) from token.
     */
    String extractUsername(String token);
}
