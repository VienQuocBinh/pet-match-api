package petmatch.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petmatch.api.request.SubscriptionRequest;
import petmatch.service.SubscriptionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(
            summary = "Create a subscription with name: STANDARD/PREMIUM",
            description = "name: STANDARD/PREMIUM")
    public ResponseEntity<Object> create(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseEntity
                .status(201)
                .body(subscriptionService.create(request));
    }
}
