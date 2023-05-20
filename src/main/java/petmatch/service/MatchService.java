package petmatch.service;

import petmatch.model.Match;
import petmatch.model.Profile;

import java.util.List;

public interface MatchService {
    List<Match> getPreviousMatches(Profile profile);
}
