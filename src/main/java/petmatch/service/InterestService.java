package petmatch.service;

import petmatch.model.Interests;
import petmatch.model.Profile;

import java.util.List;

public interface InterestService {
    List<Interests> getInterestsByProfile(Profile profile);
}
