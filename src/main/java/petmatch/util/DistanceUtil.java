package petmatch.util;

import ch.hsr.geohash.GeoHash;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petmatch.api.response.ProfileDetailResponse;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.mapper.ProfileMapper;
import petmatch.model.Address;
import petmatch.repository.AddressRepository;
import petmatch.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistanceUtil {
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final ModelMapper mapper;

    public String toGeoHashBase(double latitude, double longitude, int precision) {
        return GeoHash
                .withCharacterPrecision(latitude, longitude, precision)
                .toBase32();
    }

    public List<ProfileDetailResponse> findNearbyProfiles(double latitude, double longitude, int precision) {
        var addresses = addressRepository.findAll();
        String location = toGeoHashBase(latitude, longitude, precision);

        List<ProfileDetailResponse> results = new ArrayList<>();
        // Get all addresses in the range of current location based on the precision
        for (Address address : addresses) {
            if (address.getGeoHash().startsWith(location)) {
                var nearbyProfile = profileRepository.findByAddress_Id(address.getId())
                        .orElseThrow(() -> new InternalServerErrorException("Could not find any profile of address id: " + address.getId()));
                var breeds = ProfileMapper.buildBreedsFromInterests(nearbyProfile.getInterests());
                var galleries = ProfileMapper.buildGalleryResponse(nearbyProfile.getGallery());
                var proRes = mapper.map(nearbyProfile, ProfileDetailResponse.class);
                proRes.setGallery(galleries);
                proRes.setInterests(breeds);
                results.add(proRes);
            }
        }
        return results;
    }
}
