package petmatch.service;

import petmatch.api.request.SubscriptionRequest;
import petmatch.api.response.SubscriptionResponse;

import java.util.UUID;

public interface SubscriptionService {
    SubscriptionResponse create(SubscriptionRequest request);

    SubscriptionResponse getDetails(UUID id);
}
