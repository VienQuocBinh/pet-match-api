package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import petmatch.model.Match;
import petmatch.model.Profile;
import petmatch.repository.MatchRepository;
import petmatch.service.MatchService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;

    @Override
    public List<Match> getPreviousMatches(Profile profile) {
        return matchRepository.findAllByMatchTo(profile).orElse(Collections.emptyList());
    }
}
