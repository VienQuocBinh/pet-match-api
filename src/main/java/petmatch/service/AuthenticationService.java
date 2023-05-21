package petmatch.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import petmatch.api.request.AuthenticationRequest;
import petmatch.api.request.RegisterRequest;
import petmatch.api.response.AuthenticationResponse;

import java.io.IOException;

public interface AuthenticationService {
    /**
     * Register new user with email and password (=email encrypted)
     *
     * @param request {@code RegisterRequest}
     * @return {@code AuthenticationResponse}
     */
    AuthenticationResponse register(RegisterRequest request);

    /**
     * Register new user with email, password (=email encrypted), role=USER
     *
     * @param request {@code RegisterRequest}
     * @return {@code AuthenticationResponse}
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

    /**
     * Generates a refresh token for the specified user
     *
     * @param request  {@code  HttpServletRequest}
     * @param response {@code HttpServletResponse}
     * @throws IOException exception if an I/O error occurs
     */
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
