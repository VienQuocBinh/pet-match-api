package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import petmatch.api.request.AddressRequest;
import petmatch.api.response.AddressResponse;
import petmatch.model.Address;
import petmatch.model.User;
import petmatch.repository.AddressRepository;
import petmatch.service.AddressService;
import petmatch.service.UserService;
import petmatch.util.DistanceUtil;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ModelMapper mapper;
    @Value("${address.distance.precision}")
    private int precision;

    @Override
    public AddressResponse createAddress(AddressRequest request) {
        var user = userService.getUserById(request.getUserId());
        var address = Address.builder()
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .geoHash(DistanceUtil.toGeoHashBase(request.getLatitude(), request.getLongitude(), precision))
                .user(mapper.map(user, User.class))
                .build();
        address = addressRepository.save(address);
        return AddressResponse.builder()
                .id(address.getId())
                .address(address.getAddress())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .geoHash(address.getGeoHash())
                .userId(user.getId())
                .build();
    }
}
