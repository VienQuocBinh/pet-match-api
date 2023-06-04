package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ReactionResponse;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.model.Reaction;
import petmatch.repository.ReactionRepository;
import petmatch.service.NotificationService;
import petmatch.service.ProfileService;
import petmatch.service.ReactionService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final ProfileService profileService;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;

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
                .profile(modelMapper.map(profile, ProfileDetailResponse.class))
                .createdTimestamp(reaction.getCreatedTimestamp())
                .updatedTimestamp(reaction.getUpdatedTimestamp())
                .build();
    }

    @Override
    public List<ReactionResponse> getPreviousReactions(UUID profileId) {
        var profile = profileService.getProfileById(profileId);
        var reactions = reactionRepository.findAllByCreatedBy(profileId)
                .orElse(Collections.emptyList());
        return reactions.stream().map(reaction -> modelMapper.map(reaction, ReactionResponse.class)).toList();
    }
}
