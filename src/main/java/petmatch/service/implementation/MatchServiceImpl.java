package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petmatch.api.response.MatchResponse;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.model.Match;
import petmatch.model.Profile;
import petmatch.repository.MatchRepository;
import petmatch.service.MatchService;
import petmatch.service.NotificationService;
import petmatch.service.ProfileService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final ProfileService profileService;
    private final ModelMapper mapper;
    private final NotificationService notificationService;

    @Override
    public List<Match> getPreviousMatches(Profile profile) {
        return matchRepository.findAllByMatchFrom(profile).orElse(Collections.emptyList());
    }

    @Override
    public List<Match> getAllMatches(UUID profileId) {
        var profile = profileService.getProfileById(profileId);
        return Stream.concat(profile.getMatchTo().stream(), profile.getMatchFrom().stream())
                .distinct()
                .toList();
    }

    @Override
    public MatchResponse createMatchOfProfile(UUID fromProfileId, UUID toProfileId) {
        var fromProfile = profileService.getProfileById(fromProfileId);
        var toProfile = profileService.getProfileById(toProfileId);
        var matchesTo = toProfile.getMatchTo();
        // check conditions whether matched against
        matchesTo.forEach(match -> {
            if (match.getMatchTo().getId().equals(toProfileId))
                throw new InternalServerErrorException(fromProfileId + " matched to " + toProfileId + " already");
        });
        var match = Match.builder()
                .matchFrom(fromProfile)
                .matchTo(toProfile)
                .build();
        match = matchRepository.save(match);
        var m = mapper.map(match, MatchResponse.class);
        m.setMatchFrom(fromProfileId);
        m.setMatchTo(toProfileId);

        notificationService.sendNotification(toProfile, "You got a new matched!");
        return m;
    }
}
