package petmatch.service;

import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ReactionResponse;

public interface ReactionService {
    /**
     * Create a reaction for the specified profile.
     * Not allow to comment on the profiles belonging to the same User
     *
     * @param request ReactionRequest
     * @return ReactionResponse
     */
    ReactionResponse createReaction(ReactionRequest request);
}
