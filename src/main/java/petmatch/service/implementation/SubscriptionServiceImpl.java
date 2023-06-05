package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import petmatch.api.request.SubscriptionRequest;
import petmatch.api.response.SubscriptionResponse;
import petmatch.configuration.constance.SubscriptionName;
import petmatch.configuration.constance.SubscriptionStatus;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.model.Subscription;
import petmatch.model.User;
import petmatch.repository.SubscriptionRepository;
import petmatch.service.SubscriptionService;
import petmatch.service.UserService;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final ModelMapper mapper;
    @Value("${subscriptions.duration.premium}")
    private int premiumDuration;
    @Value("${subscriptions.duration.standard}")
    private int standardDuration;

    @Override
    public SubscriptionResponse create(SubscriptionRequest request) {
        var user = userService.getUserById(request.getUserId());

        var subscription = Subscription.builder();

        if (request.getName().equals(SubscriptionName.PREMIUM.name())) {
            subscription.name(SubscriptionName.PREMIUM.name())
                    .startFrom(new Date())
                    .duration(premiumDuration);
        } else if (request.getName().equals(SubscriptionName.STANDARD.name())) {
            subscription.name(SubscriptionName.STANDARD.name())
                    .startFrom(new Date())
                    .duration(standardDuration);
        } else {
            throw new InternalServerErrorException("Invalid subscription");
        }
        subscription.status(SubscriptionStatus.ACTIVE.name())
                .user(mapper.map(user, User.class));
        var savedSubscription = subscriptionRepository.save(subscription.build());

        return mapper.map(savedSubscription, SubscriptionResponse.class);
    }

    @Override
    public SubscriptionResponse getDetails(UUID id) {
        var subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SubscriptionService.class, "id", id));
        return mapper.map(subscription, SubscriptionResponse.class);
    }
}
