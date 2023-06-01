package petmatch.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petmatch.api.request.MatchRequest;
import petmatch.api.response.MatchResponse;
import petmatch.service.MatchService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/match")
public class MatchController {
    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<MatchResponse> matchToProfile(@RequestBody MatchRequest request) {
        return ResponseEntity.ok(matchService.createMatchOfProfile(request.getMatchFromProfileId(), request.getMatchToProfileId()));
    }

    @GetMapping("/profile/{id}")
    @Operation(
            summary = "Get all matching profiles",
            description = "Including match from and to profile")
    public ResponseEntity<List<MatchResponse>> getMatchesProfile(@PathVariable UUID id) {
        var response = matchService.getAllMatches(id)
                .stream()
                .map(element -> MatchResponse.builder()
                        .id(element.getId())
                        .matchFrom(element.getMatchFrom().getId())
                        .matchTo(element.getMatchTo().getId())
                        .createdTimestamp(element.getCreatedTimestamp())
                        .updatedTimestamp(element.getUpdatedTimestamp())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }
}
