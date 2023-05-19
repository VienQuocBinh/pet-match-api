package petmatch.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    /**
     * Get user email from token
     *
     * @param token {@code String}
     * @return user email - {@code String}
     */
    String extractUsername(String token);

    /**
     * Generate access token from UserDetails
     *
     * @param userDetails {@code UserDetails}
     * @return access token - {@code String}
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generate refresg token from UserDetails
     *
     * @param userDetails {@code UserDetails}
     * @return refresh token - {@code String}
     */
    String generateRefreshToken(UserDetails userDetails);

    /**
     * Check the email and expiration time of the token is valid
     *
     * @param token       {@code String}
     * @param userDetails {@code UserDetails}
     * @return {@code boolean}
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
