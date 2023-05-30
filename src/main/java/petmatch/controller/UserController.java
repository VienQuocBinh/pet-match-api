package petmatch.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petmatch.api.request.UserRequest;
import petmatch.api.response.UserResponse;
import petmatch.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PatchMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }
}
