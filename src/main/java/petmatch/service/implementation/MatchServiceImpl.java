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
import petmatch.service.ProfileService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final ProfileService profileService;
    private final ModelMapper mapper;

    @Override
    public List<Match> getPreviousMatches(Profile profile) {
        return matchRepository.findAllByMatchTo(profile).orElse(Collections.emptyList());
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
        return m;
    }
}
