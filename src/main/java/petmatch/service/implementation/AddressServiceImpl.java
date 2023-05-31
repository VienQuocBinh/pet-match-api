package petmatch.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import petmatch.api.request.AddressRequest;
import petmatch.api.response.AddressResponse;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.model.Address;
import petmatch.repository.AddressRepository;
import petmatch.service.AddressService;
import petmatch.service.ProfileService;
import petmatch.util.DistanceUtil;

import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ProfileService profileService;
    private final ModelMapper mapper;
    private final DistanceUtil distanceUtil;

    public AddressServiceImpl(AddressRepository addressRepository, ProfileService profileService, ModelMapper mapper, DistanceUtil distanceUtil) {
        this.addressRepository = addressRepository;
        this.profileService = profileService;
        this.mapper = mapper;
        this.distanceUtil = distanceUtil;
    }


    @Override
    public AddressResponse createAddress(AddressRequest request) {
        int precision = 12;
        var profile = profileService.getProfileById(request.getProfileId());
        var address = Address.builder()
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .geoHash(distanceUtil.toGeoHashBase(request.getLatitude(), request.getLongitude(), precision))
                .profile(profile)
                .build();
        address = addressRepository.save(address);
        return mapper.map(address, AddressResponse.class);
    }

    @Override
    public Address updateAddress(Address address, UUID id) {
        int precision = 12;
        var adr = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Address.class, "id", id));
        adr.setAddress(address.getAddress());
        adr.setLatitude(address.getLatitude());
        adr.setLongitude(address.getLongitude());
        adr.setGeoHash(distanceUtil.toGeoHashBase(address.getLatitude(), address.getLongitude(), precision));
        adr = addressRepository.save(adr);
        return adr;
    }
}
