package petmatch.service;

import petmatch.api.request.SubscriptionRequest;
import petmatch.api.response.SubscriptionResponse;

public interface SubscriptionService {
    SubscriptionResponse create(SubscriptionRequest request);
}
