package petmatch.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petmatch.api.request.SubscriptionRequest;
import petmatch.api.response.SubscriptionResponse;
import petmatch.service.SubscriptionService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> getDetails(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(subscriptionService.getDetails(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a subscription with name: STANDARD/PREMIUM",
            description = "name: STANDARD/PREMIUM")
    public ResponseEntity<SubscriptionResponse> create(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseEntity
                .status(201)
                .body(subscriptionService.create(request));
    }
}
