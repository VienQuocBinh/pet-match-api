package petmatch.service;

import petmatch.model.Profile;

import java.util.List;

public interface SuggestionService {
    /**
     * Suggest all the profiles which are not matched before and not belong to the current User.
     *
     * @param myProfile Profile
     * @return {@code List<Profile>}
     */
    List<Profile> suggestProfiles(Profile myProfile);
}
