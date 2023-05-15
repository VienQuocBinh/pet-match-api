package unit.petmatch.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import petmatch.api.request.ProfileRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ProfileResponse;
import petmatch.configuration.constance.Gender;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.configuration.exception.InvalidArgumentException;
import petmatch.model.Breed;
import petmatch.model.Profile;
import petmatch.model.Species;
import petmatch.repository.ProfileRepository;
import petmatch.service.implementation.ProfileServiceImpl;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    public static final String userId = "123";

    private static final UUID profileId = UUID.randomUUID();

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository mockProfileRepository;

    @Test
    public void givenValidUserId_whenGetProfileList_thenReturnProfilesResponses() throws Exception {
        //given
        List<Profile> response = buildProfileList();
        when(mockProfileRepository.findAllByUserId(any())).thenAnswer(invocation -> Optional.of(response));

        //when
        List<ProfileResponse> res = profileService.getProfilesByUserId(userId);
        //then
        Assertions.assertEquals(3, res.size());
    }

    @Test
    public void givenInvalidUserId_whenGetProfilesList_thenThrowsError() {
        //given
        when(mockProfileRepository.findAllByUserId(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.getProfilesByUserId(userId));
        verify(mockProfileRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void givenProfileRequest_whenGetDetailProfile_thenReturnDetailResponse() throws Exception {
        //given
        ProfileRequest request = buildProfileRequest();
        var profileId = UUID.randomUUID();
        Profile profile = Profile.builder()
                .id(profileId)
                .name("Dog")
                .gender(Gender.MALE)
                .birthday(Date.from(Instant.now()))
                .build();
        when(mockProfileRepository.findById(any())).thenReturn(Optional.of(profile));

        //when
        ProfileDetailResponse response = profileService.getProfileDetail(profileId);

        //then
        assertEquals(profileId, response.getProfileId());
        assertEquals("Dog", response.getName());
        assertEquals(Gender.MALE, response.getGender());
    }

    @Test
    public void givenInvalidRequest_whenGetProfileDetail_thenThrowsError() {
        ProfileRequest request = buildProfileRequest();
        when(mockProfileRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.getProfileDetail(profileId));
        verify(mockProfileRepository, times(1)).findById(profileId);
    }

    @Test
    public void givenProfileRequest_whenCreateProfileDetail_thenReturn200OK() throws Exception {
        //given
        ProfileRequest request = buildProfileRequest();
        Profile profile = buildProfileList().get(0);
        when(mockProfileRepository.save(any())).thenAnswer(invocation -> profile);
        //when
        var createdProfile = profileService.createProfileDetail(request);
        //then
        assertEquals(createdProfile.getProfileId(), profile.getId());
        verify(mockProfileRepository, times(1)).save(any());
    }

    @Test
    public void givenInvalidProfileRequest_whenCreateProfileDetails_thenThrowsError() {
        ProfileRequest request = buildProfileRequest();
        request.setName(null);
        assertThrows(InvalidArgumentException.class, () -> profileService.createProfileDetail(request));
        verify(mockProfileRepository, times(0)).save(any());
    }

    private ProfileRequest buildProfileRequest() {
        Species species = Species.builder()
                .id(UUID.randomUUID())
                .name("Cho")
                .build();
        Breed breed = Breed.builder()
                .id(UUID.randomUUID())
                .species(species)
                .name("Cho Shiba")
                .build();
        return ProfileRequest.builder()
                .userId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454")
                .profileId(UUID.randomUUID())
                .breed(breed)
                .gender(Gender.MALE)
                .name("Dog")
                .birthday(Date.from(Instant.now()))
                .height(120.9)
                .weight(32.9)
                .interests(List.of(breed))
                .avatar("firebaseAvatar.com")
                .build();
    }

    private List<Profile> buildProfileList() {
        return List.of(
                Profile.builder()
                        .id(UUID.randomUUID())
                        .name("Dog")
                        .build(),
                Profile.builder()
                        .id(UUID.randomUUID())
                        .name("Dog")
                        .build(),
                Profile.builder()
                        .id(UUID.randomUUID())
                        .name("Dog")
                        .build()
                );
    }
}
