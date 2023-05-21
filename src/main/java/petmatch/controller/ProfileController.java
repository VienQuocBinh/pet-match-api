package petmatch.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petmatch.api.request.ProfileRequest;
import petmatch.api.request.ProfileSuggestRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ProfileResponse;
import petmatch.model.Profile;
import petmatch.service.ProfileService;
import petmatch.service.suggestion.SuggestionService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    private final ProfileService profileService;
    private final SuggestionService suggestionService;

    @GetMapping("/v1/profiles/user/{userId}")
    public ResponseEntity<List<ProfileResponse>> getProfileList(@PathVariable final String userId) {
        log.info("get profile list by id " + userId);
        return new ResponseEntity<>(profileService.getProfilesByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/v1/profiles/{profileId}")
    public ResponseEntity<ProfileDetailResponse> getProfileDetails(@PathVariable final UUID profileId) {
        log.info("get profile detail by id: " + profileId);
        return new ResponseEntity<>(profileService.getProfileDetail(profileId), HttpStatus.OK);
    }

    @PostMapping("/v1/profiles")
    public ResponseEntity<ProfileDetailResponse> createProfileDetail(
            @RequestBody @Valid ProfileRequest profile
    ) {
        log.info("Start create new profile");
        return new ResponseEntity<>(profileService.createProfileDetail(profile), HttpStatus.CREATED);
    }

    @PatchMapping("/v1/profiles")
    public ResponseEntity<ProfileDetailResponse> updateProfileDetail(
            @RequestBody @Valid ProfileRequest profile
    ) {
        log.info("Update info");
        return new ResponseEntity<>(profileService.updateProfileDetail(profile), HttpStatus.OK);
    }

    @PostMapping("/api/v1/profiles/suggestion")
    public ResponseEntity<List<ProfileResponse>> suggest(@RequestBody ProfileSuggestRequest request) {
        Profile profile = profileService.getProfileById(request.getProfileId());
        return new ResponseEntity<>(suggestionService.suggestProfiles(profile).stream()
                .map(p -> ProfileResponse.builder()
                        .profileId(p.getId())
                        .avatar(p.getAvatar())
                        .name(p.getName())
                        .build())
                .toList(),
                HttpStatus.OK);
    }
}
