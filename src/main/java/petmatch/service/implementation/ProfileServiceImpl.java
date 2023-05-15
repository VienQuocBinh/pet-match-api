package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petmatch.api.request.ProfileRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ProfileResponse;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.configuration.exception.InvalidArgumentException;
import petmatch.model.Breed;
import petmatch.model.Profile;
import petmatch.repository.BreedRepository;
import petmatch.repository.ProfileRepository;
import petmatch.service.ProfileService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final BreedRepository breedRepository;

    @Override
    public List<ProfileResponse> getProfilesByUserId(String userId) {
        var profileList = profileRepository
                .findAllByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(Profile.class, "userId", userId));
        var mapper = new ModelMapper();
        var res = new ArrayList<ProfileResponse>();
        profileList.forEach(profile -> res.add(mapper.map(profile, ProfileResponse.class)));
        return res;
    }

    @Override
    public ProfileDetailResponse getProfileDetail(UUID profileId) {
        var profile = profileRepository.findById(profileId).orElseThrow(() -> new EntityNotFoundException(Profile.class, "profileId", profileId));
        var mapper = new ModelMapper();
        return mapper.map(profile, ProfileDetailResponse.class);
    }

    @Override
    public ProfileDetailResponse createProfileDetail(ProfileRequest request) {
        var mapper = new ModelMapper();
        validateRequest(request);
        var profile = mapper.map(request, Profile.class);
        var breed = breedRepository.findById(profile.getBreed().getId()).orElseThrow(() -> new EntityNotFoundException(Breed.class, "Breed", "not found"));
        profile.setBreed(breed);
        profile.setCreatedTimestamp(Date.from(Instant.now()));
        profile.setUpdatedTimestamp(Date.from(Instant.now()));
        var res = profileRepository.save(profile);
        return mapper.map(res, ProfileDetailResponse.class);
    }

    @Override
    public ProfileDetailResponse updateProfileDetail(ProfileRequest request) {
        var mapper = new ModelMapper();
        validateRequest(request);
        var profile = mapper.map(request, Profile.class);
        profileRepository.save(profile);
        return mapper.map(profile, ProfileDetailResponse.class);
    }

    public void validateRequest(ProfileRequest request) {
        String NullValueErrorMessage = "must not be null";
        String nonPositiveErrorMessage = "invalid number, must be positive number";
        if(request.getName() == null || request.getName().isBlank()) throw new InvalidArgumentException(String.class, "Name", NullValueErrorMessage);
        if(request.getBreed() == null) throw new InvalidArgumentException(Breed.class, "Breed", NullValueErrorMessage);
        if(request.getAvatar() == null || request.getAvatar().isBlank()) throw new InvalidArgumentException(String.class, "Avatar", NullValueErrorMessage);
        if(request.getHeight() == null) throw new InvalidArgumentException(Double.class, "Height", NullValueErrorMessage);
        if(request.getHeight() < 0) throw new InvalidArgumentException(Double.class, "Height", nonPositiveErrorMessage);
        if(request.getWeight() == null) throw new InvalidArgumentException(Double.class, "Weight", NullValueErrorMessage);
        if(request.getWeight() < 0) throw new InvalidArgumentException(Double.class, "Weight", nonPositiveErrorMessage);
        if(request.getGender() == null) throw new InvalidArgumentException(Breed.class, "Gender", NullValueErrorMessage);
        if(request.getBirthday() == null) throw new InvalidArgumentException(Date.class, "Birthday", NullValueErrorMessage);
    }

}
