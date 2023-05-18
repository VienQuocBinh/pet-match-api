package petmatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import petmatch.api.request.AuthenticationRequest;
import petmatch.api.request.RegisterRequest;
import petmatch.api.response.AuthenticationResponse;
import petmatch.configuration.constance.Role;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.model.User;
import petmatch.repository.UserRepository;
import petmatch.service.firebase.UserManagementService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserManagementService userManagementService;

    public AuthenticationResponse register(RegisterRequest request) {
        try {
            var userFirebase = userManagementService.getFirebaseUserByEmail(request.getEmail());
            var user = User.builder()
                    .id(userFirebase.getId())
                    .email(request.getEmail())
//                    .password(passwordEncoder.encode(request.getPassword()))
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

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getEmail()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
