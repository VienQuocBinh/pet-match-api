package petmatch.service;

import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ReactionResponse;
import petmatch.model.Profile;
import petmatch.model.Reaction;

import java.util.List;

public interface ReactionService {
    /**
     * Create a reaction for the specified profile.
     * Not allow to comment on the profiles belonging to the same User
     *
     * @param request ReactionRequest
     * @return ReactionResponse
     */
    ReactionResponse createReaction(ReactionRequest request);

    List<Reaction> getPreviousReactions(Profile profile);
}
