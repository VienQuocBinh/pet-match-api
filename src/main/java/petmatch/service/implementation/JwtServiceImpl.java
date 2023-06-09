package petmatch.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import petmatch.api.domain.UserInfo;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.model.User;
import petmatch.repository.UserRepository;
import petmatch.service.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;
    @Value("${security.jwt.expiration}")
    private long JWT_EXPIRED_TIME;
    @Value("${security.jwt.refresh-token.expiration}")
    private long REFRESH_TOKEN_EXPIRED_TIME;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = buildClaims(userDetails);

        return buildToken(claims, userDetails, JWT_EXPIRED_TIME);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = buildClaims(userDetails);

        return buildToken(claims, userDetails, REFRESH_TOKEN_EXPIRED_TIME);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails,
                              long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String, Object> buildClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", userDetails.getUsername()));
        // User information: email, role
        var info = UserInfo.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        claims.put("user", info);
        return claims;
    }
}
