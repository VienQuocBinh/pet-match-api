package petmatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petmatch.api.request.MatchRequest;
import petmatch.api.response.MatchResponse;
import petmatch.service.MatchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/match")
public class MatchController {
    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<MatchResponse> matchToProfile(@RequestBody MatchRequest request) {
        return ResponseEntity.ok(matchService.createMatchOfProfile(request.getMatchFromProfileId(), request.getMatchToProfileId()));
    }
}
