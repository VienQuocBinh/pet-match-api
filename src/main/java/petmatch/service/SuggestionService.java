package petmatch.service;

import petmatch.model.Profile;

import java.util.List;

public interface SuggestionService {
    List<Profile> suggestProfiles(Profile myProfile);
}
