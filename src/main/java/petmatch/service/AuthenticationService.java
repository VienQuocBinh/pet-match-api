package petmatch.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
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
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.model.User;
import petmatch.repository.UserRepository;
import petmatch.service.firebase.TokenVerifier;
import petmatch.service.firebase.UserManagementService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserManagementService userManagementService;
    private final TokenVerifier tokenVerifier;

    /**
     * Register new user with email and password (=email encrypted)
     *
     * @param request {@code RegisterRequest}
     * @return {@code AuthenticationResponse}
     */
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

    /**
     * Register new user with email, password (=email encrypted), role=USER
     *
     * @param request {@code RegisterRequest}
     * @return {@code AuthenticationResponse}
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String jwtToken;
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
            user = userRepository.findByEmail(userFirebase.getEmail()).orElse(new User());
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
        jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
