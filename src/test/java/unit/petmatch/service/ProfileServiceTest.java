package unit.petmatch.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import petmatch.api.request.ProfileRequest;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.api.response.ProfileResponse;
import petmatch.configuration.constance.Gender;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.model.Breed;
import petmatch.model.Profile;
import petmatch.model.Species;
import petmatch.model.User;
import petmatch.repository.*;
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

    @Mock
    private BreedRepository mockBreedRepository;

    @Mock
    private InterestRepository mockInterestRepository;

    @Mock
    private GalleryRepository mockGalleryRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Spy
    private ModelMapper modelMapper;

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
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email("test@test.com")
                .build();
        Breed breed = buildBreed();
        when(mockUserRepository.findById(any())).thenReturn(Optional.of(user));
        when(mockProfileRepository.save(any())).thenAnswer(invocation -> profile);
        when(mockBreedRepository.findById(any())).thenReturn(Optional.of(breed));
        //when
        var createdProfile = profileService.createProfileDetail(request);
        //then
        assertEquals(createdProfile.getProfileId(), profile.getId());
        assertEquals(profile.getBreed().getId(), createdProfile.getBreed().getId());
        verify(mockProfileRepository, times(1)).save(any());
    }

    @Test
    public void givenInvalidProfileRequest_whenCreateProfileDetails_thenThrowsError() {
        ProfileRequest request = buildProfileRequest();
        request.setName(null);
        when(mockBreedRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.createProfileDetail(request));
        verify(mockProfileRepository, times(0)).save(any());
    }

    @Test
    public void givenNullBreed_WhenCreateProfile_thenThrowError() {
        ProfileRequest request = buildProfileRequest();
        when(mockBreedRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.createProfileDetail(request));
        verify(mockProfileRepository, times(0)).save(any());
    }

    @Test
    public void givenValidProfile_whenUpdateProfile_thenReturn200OK() {
        //given
        ProfileRequest request = buildProfileRequest();
        var profileTo = buildProfileList().get(0);
        var breed = buildBreed();
        when(mockBreedRepository.findById(any())).thenReturn(Optional.of(breed));
        when(mockProfileRepository.save(any())).thenReturn(profileTo);
        //when
        var updatedProfile = profileService.updateProfileDetail(request);
        //then
        assertEquals(120d, updatedProfile.getHeight());
        assertEquals(20d, updatedProfile.getWeight());
        verify(mockProfileRepository, times(1)).save(any());
    }

    @Test
    public void givenInvalidRequest_whenUpdateProfile_thenthrowError() {
        ProfileRequest request = buildProfileRequest();
        when(mockBreedRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.updateProfileDetail(request));
        verify(mockProfileRepository, times(0)).save(any());
    }

    private ProfileRequest buildProfileRequest() {
        var breed = buildBreed();
        return ProfileRequest.builder()
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

    private Breed buildBreed() {
        Species species = Species.builder()
                .id(UUID.randomUUID())
                .name("Cho")
                .build();
        return Breed.builder()
                .id(UUID.randomUUID())
                .species(species)
                .name("Cho Shiba")
                .build();
    }

    private List<Profile> buildProfileList() {
        return List.of(
                Profile.builder()
                        .id(UUID.randomUUID())
                        .name("Dog")
                        .height(120d)
                        .weight(20d)
                        .gender(Gender.MALE)
                        .birthday(Date.from(Instant.now()))
                        .breed(buildBreed())
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
