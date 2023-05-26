package petmatch.service;

import petmatch.api.response.MatchResponse;
import petmatch.model.Match;
import petmatch.model.Profile;

import java.util.List;
import java.util.UUID;

public interface MatchService {
    List<Match> getPreviousMatches(Profile profile);

    MatchResponse createMatchOfProfile(UUID fromProfileId, UUID toProfileId);
}
