package petmatch.service.implementation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import petmatch.model.User;
import petmatch.repository.TokenRepository;
import petmatch.repository.UserRepository;
import petmatch.service.JwtService;
import petmatch.service.LogoutService;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService, LogoutHandler {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String jwtToken = authHeader.replace("Bearer ", "");
        var storedToken = tokenRepository.findByToken(jwtToken).orElse(null);
        if (storedToken != null) {
            var userEmail = jwtService.extractUsername(jwtToken);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("Email " + userEmail + " not found"));
            user.setIsOnline(false);
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            userRepository.save(user);
            SecurityContextHolder.clearContext();
        }
    }
}
