package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ReactionResponse;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.model.Reaction;
import petmatch.repository.ReactionRepository;
import petmatch.service.NotificationService;
import petmatch.service.ProfileService;
import petmatch.service.ReactionService;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final ProfileService profileService;
    private final NotificationService notificationService;

    @Override
    public ReactionResponse createReaction(ReactionRequest request) {
        // Not allow to comment on the profiles belonging to the same User
        var profile = profileService.getProfileById(request.getProfileId());
        var createdProfile = profileService.getProfileById(request.getCreatedBy());
        if (profile.getUser().getId().equals(createdProfile.getUser().getId())) {
            throw new InternalServerErrorException("Can not create reaction on the same User");
        }

        var reaction = reactionRepository.save(Reaction.builder()
                .comment(request.getComment())
                .createdBy(request.getCreatedBy())
                .profile(profile)
                .build());

        notificationService.sendNotification(profile, "You got a new comment!");

        return ReactionResponse.builder()
                .comment(request.getComment())
                .createdBy(request.getCreatedBy())
                .profileId(request.getProfileId())
                .createdBy(request.getCreatedBy())
                .createdTimestamp(reaction.getCreatedTimestamp())
                .updatedTimestamp(reaction.getUpdatedTimestamp())
                .build();
    }
}
