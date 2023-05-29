package petmatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ReactionResponse;
import petmatch.service.ReactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity<ReactionResponse> createReaction(@RequestBody ReactionRequest request) {

        return ResponseEntity.ok(reactionService.createReaction(request));
    }
}
