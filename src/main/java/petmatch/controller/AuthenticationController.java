package petmatch.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petmatch.api.request.AuthenticationRequest;
import petmatch.api.request.RegisterRequest;
import petmatch.api.response.AuthenticationResponse;
import petmatch.service.AuthenticationService;
import petmatch.service.JwtService;
import petmatch.service.UserService;
import petmatch.service.firebase.TokenVerifier;
import petmatch.service.firebase.UserManagementService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserManagementService userManagementService;
    private final TokenVerifier tokenVerifier;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/google")
    @SecurityRequirement(name = "google")
    public ResponseEntity<AuthenticationResponse> loginGoogle(String idTokenString) {
        // Validate the idTokenString
        GoogleIdToken.Payload payload = tokenVerifier.validate(idTokenString);
        return ResponseEntity.ok(authenticationService.authenticate(AuthenticationRequest.builder()
                .email(payload.getEmail())
//                .password(payload.getEmail())
                .build()));
    }
}
