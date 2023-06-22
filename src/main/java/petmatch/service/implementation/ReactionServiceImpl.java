package petmatch.service.implementation;

import com.fasterxml.jackson.core.PrettyPrinter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petmatch.api.request.ReactionRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ReactionResponse;
import petmatch.configuration.exception.EntityNotFoundException;
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
        var createdBy = profileService.getProfileById(request.getCreatedBy());
        var reaction = reactionRepository.save(Reaction.builder()
                .comment(request.getComment())
                .createdBy(createdBy)
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
        var reactions = reactionRepository.findAllByProfileId(profileId)
                .orElse(Collections.emptyList());
        return reactions.stream().map(reaction -> {
            var res = modelMapper.map(reaction, ReactionResponse.class);
            res.setProfile(modelMapper.map(reaction.getCreatedBy(), ProfileDetailResponse.class));
            return res;
        }).toList();
    }

    @Override
    public ReactionResponse removeReaction(ReactionRequest request) {
        var reaction = reactionRepository
                .findByProfileIdAndCreatedBy(request.getProfileId(), request.getCreatedBy())
                .orElseThrow(() -> new EntityNotFoundException(Reaction.class, "Reaction", "Not found"));
        reactionRepository.delete(reaction);
        return ReactionResponse.builder()
                .profile(modelMapper.map(reaction.getCreatedBy(), ProfileDetailResponse.class))
                .createdTimestamp(reaction.getCreatedTimestamp())
                .updatedTimestamp(reaction.getUpdatedTimestamp())
                .build();
    }
}
