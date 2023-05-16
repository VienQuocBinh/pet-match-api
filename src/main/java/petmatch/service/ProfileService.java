package petmatch.service;

import petmatch.api.request.ProfileRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ProfileResponse;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
    List<ProfileResponse> getProfilesByUserId(String userId);
    ProfileDetailResponse getProfileDetail(UUID profileId);
    ProfileDetailResponse createProfileDetail(ProfileRequest request);
    ProfileDetailResponse updateProfileDetail(ProfileRequest request);
}
