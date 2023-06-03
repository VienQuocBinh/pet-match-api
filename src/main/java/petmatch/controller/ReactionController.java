package petmatch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ReactionResponse;
import petmatch.service.ReactionService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity<ReactionResponse> createReaction(@RequestBody ReactionRequest request) {
        log.info(String.format("Profile %s likes profile %s with comment: %s",
                request.getCreatedBy().toString(),
                request.getProfileId().toString(),
                request.getComment()));
        return ResponseEntity.ok(reactionService.createReaction(request));
    }

    @GetMapping("/like/{profileId}")
    public ResponseEntity<List<ReactionResponse>> getLikedProfiles(
            @PathVariable UUID profileId
    ){
        log.info(String.format("Get list profiles liked by profile id %s", profileId));
        return ResponseEntity.ok(reactionService.getLikedProfilesBy(profileId));
    }
}
