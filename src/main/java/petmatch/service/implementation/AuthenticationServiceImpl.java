package petmatch.service.implementation;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import petmatch.api.request.AuthenticationRequest;
import petmatch.api.request.RegisterRequest;
import petmatch.api.response.AuthenticationResponse;
import petmatch.api.response.UserResponse;
import petmatch.configuration.constance.Role;
import petmatch.configuration.constance.TokenType;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.model.Token;
import petmatch.model.User;
import petmatch.repository.TokenRepository;
import petmatch.repository.UserRepository;
import petmatch.service.AuthenticationService;
import petmatch.service.JwtService;
import petmatch.service.firebase.TokenVerifier;
import petmatch.service.firebase.UserManagementService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserManagementService userManagementService;
    private final TokenVerifier tokenVerifier;
    private final TokenRepository tokenRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        try {
            var userFirebase = userManagementService.getFirebaseUserByEmail(request.getEmail());
            var user = User.builder()
                    .id(userFirebase.getId())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getEmail()))
                    .phone(request.getPhone())
                    .role(Role.USER)
                    .build();
            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getClass().getName() + ". " + e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UserResponse userFirebase = new UserResponse();
        User user;
        try {
            // Validate the idTokenString
            GoogleIdToken.Payload payload = tokenVerifier.validate(request.getIdTokenString());
            userFirebase = userManagementService.getFirebaseUserByEmail(payload.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            payload.getEmail(),
                            payload.getEmail())).getPrincipal();
            user = userRepository.findByEmail(userFirebase.getEmail()).orElseThrow();
        } catch (AuthenticationException e) {
            // create new user
            user = userRepository.save(User.builder()
                    .id(userFirebase.getId())
                    .email(userFirebase.getEmail())
                    .password(passwordEncoder.encode(userFirebase.getEmail()))
                    .phone(userFirebase.getPhone())
                    .role(Role.USER)
                    .build());
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getClass().getName() + ". " + e.getMessage());
        }
        user.setIsOnline(true);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InternalServerErrorException("Invalid auth header: not a Bearer");
        }
        refreshToken = authHeader.replace("Bearer ", "");
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                // generate new access token
                var accessToken = jwtService.generateToken(user);
                // disable all previous access tokens
                revokeAllUserTokens(user);
                // add new access token to the user
                saveUserToken(user, accessToken);
                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        throw new InternalServerErrorException("Can not extract email from refresh token");
    }

    /**
     * Add new access tokens to the User
     *
     * @param user     User
     * @param jwtToken String
     */
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Disable all previous tokens of the User
     *
     * @param user User
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
