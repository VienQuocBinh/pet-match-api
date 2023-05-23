package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petmatch.api.request.ProfileRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ProfileResponse;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.mapper.ProfileMapper;
import petmatch.model.Breed;
import petmatch.model.Gallery;
import petmatch.model.Interests;
import petmatch.model.Profile;
import petmatch.repository.BreedRepository;
import petmatch.repository.GalleryRepository;
import petmatch.repository.InterestRepository;
import petmatch.repository.ProfileRepository;
import petmatch.repository.UserRepository;
import petmatch.service.ProfileService;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final BreedRepository breedRepository;
    private final InterestRepository interestRepository;
    private final GalleryRepository galleryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProfileResponse> getProfilesByUserId(String userId) {
        var profileList = profileRepository
                .findAllByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(Profile.class, "userId", userId));
        var res = new ArrayList<ProfileResponse>();
        profileList.forEach(profile -> res.add(modelMapper.map(profile, ProfileResponse.class)));
        return res;
    }

    @Override
    public ProfileDetailResponse getProfileDetail(UUID profileId) {
        var profile = profileRepository.findById(profileId).orElseThrow(() -> new EntityNotFoundException(Profile.class, "profileId", profileId));
        var interests = interestRepository.findAllByProfile(profile);
        var response = modelMapper.map(profile, ProfileDetailResponse.class);
        interests.ifPresent(interest -> response.setInterests(ProfileMapper.buildBreedsFromInterests(interest)));
        var galleries = galleryRepository.findAllByProfile(profile);
        galleries.ifPresent(gallery -> response.setGallery(ProfileMapper.buildGalleryResponse(gallery)));
        return response;
    }

    @Override
    public ProfileDetailResponse createProfileDetail(ProfileRequest request) {
//        validateRequest(request);
        var profile = modelMapper.map(request, Profile.class);
        var breed = breedRepository.findById(profile.getBreed().getId()).orElseThrow(() -> new EntityNotFoundException(Breed.class, "Breed", "not found"));
        profile.setBreed(breed);
        var user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException(petmatch.model.User.class, "userId", "not found"));
        profile.setUser(user);
        var res = profileRepository.save(profile);
        var response = modelMapper.map(res, ProfileDetailResponse.class);
        if (request.getInterests() != null && !request.getInterests().isEmpty()) {
            var interests = request.getInterests().stream().map(interest -> Interests.builder().breed(interest).profile(res).build()).toList();
            var interestsList = interestRepository.saveAll(interests);
            response.setInterests(ProfileMapper.buildBreedsFromInterests(interestsList));
        }
        if (request.getGallery() != null && !request.getGallery().isEmpty()) {
            var gallery = request.getGallery().stream().map(s -> Gallery.builder().gallery(s).profile(res).createdTimestamp(Date.from(Instant.now())).updatedTimestamp(Date.from(Instant.now())).build()).toList();
            var galleryList = galleryRepository.saveAll(gallery);
            response.setGallery(ProfileMapper.buildGalleryResponse(galleryList));
        }
        return response;
    }

    @Override
    public ProfileDetailResponse updateProfileDetail(ProfileRequest request) {
//        validateRequest(request);
        var profile = modelMapper.map(request, Profile.class);
        var breed = breedRepository.findById(profile.getBreed().getId()).orElseThrow(() -> new EntityNotFoundException(Breed.class, "Breed", "not found"));
        profile.setBreed(breed);
        var res = profileRepository.save(profile);
        var response = modelMapper.map(res, ProfileDetailResponse.class);
        if (request.getInterests() != null && !request.getInterests().isEmpty()) {
            var interests = request.getInterests().stream().map(interest -> Interests.builder().breed(interest).profile(res).build()).toList();
            var interestsList = interestRepository.saveAll(interests);
            response.setInterests(ProfileMapper.buildBreedsFromInterests(interestsList));
        }
        if (request.getGallery() != null && !request.getGallery().isEmpty()) {
            var gallery = request.getGallery().stream().map(s -> Gallery.builder().gallery(s).profile(res).createdTimestamp(Date.from(Instant.now())).updatedTimestamp(Date.from(Instant.now())).build()).toList();
            var galleryList = galleryRepository.saveAll(gallery);
            response.setGallery(ProfileMapper.buildGalleryResponse(galleryList));
        }
        return response;
    }

    @Override
    public List<Profile> getProfilesByInterest(Interests interests) {
        Optional<List<Profile>> profiles = profileRepository.findAllByInterests(interests);
        return profiles.orElse(Collections.emptyList());
    }

    @Override
    public Profile getProfileById(UUID id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Profile.class, "id", id));
    }
}
