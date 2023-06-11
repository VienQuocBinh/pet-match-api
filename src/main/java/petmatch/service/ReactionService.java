package petmatch.service;

import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ReactionResponse;
import petmatch.model.Profile;
import petmatch.model.Reaction;

import java.util.List;
import java.util.UUID;

public interface ReactionService {
    /**
     * Create a reaction for the specified profile.
     * Not allow to comment on the profiles belonging to the same User
     *
     * @param request ReactionRequest
     * @return ReactionResponse
     */
    ReactionResponse createReaction(ReactionRequest request);

    List<ReactionResponse> getPreviousReactions(UUID profileId);

    ReactionResponse removeReaction(ReactionRequest request);
}
