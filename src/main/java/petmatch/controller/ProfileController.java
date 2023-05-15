package petmatch.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import petmatch.api.request.ProfileRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ProfileResponse;
import petmatch.service.ProfileService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    final ProfileService profileService;

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

}
