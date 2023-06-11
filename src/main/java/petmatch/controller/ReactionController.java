package petmatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ProfileResponse;
import petmatch.api.response.ReactionResponse;
import petmatch.service.ReactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity<ReactionResponse> createReaction(@RequestBody ReactionRequest request) {

        return ResponseEntity.ok(reactionService.createReaction(request));
    }

    @GetMapping("/likes/{profileId}")
    public ResponseEntity<List<ReactionResponse>> getReactionLike(@PathVariable UUID profileId) {
        return ResponseEntity.ok(reactionService.getPreviousReactions(profileId));
    }

    @PostMapping("/remove")
    public ResponseEntity<ReactionResponse> sendPass(@RequestBody ReactionRequest request) {
        return ResponseEntity.ok(reactionService.removeReaction(request));
    }
}
