package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import petmatch.model.Interests;
import petmatch.model.Profile;
import petmatch.repository.InterestRepository;
import petmatch.service.InterestService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {
    private final InterestRepository interestRepository;

    @Override
    public List<Interests> getInterestsByProfile(Profile profile) {
        Optional<List<Interests>> interests = interestRepository.findAllByProfile(profile);
        return interests.orElse(Collections.emptyList());
    }
}
