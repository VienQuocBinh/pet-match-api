package petmatch.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import petmatch.api.request.AddressRequest;
import petmatch.api.response.AddressResponse;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.model.Address;
import petmatch.model.User;
import petmatch.repository.AddressRepository;
import petmatch.service.AddressService;
import petmatch.service.UserService;
import petmatch.util.DistanceUtil;

import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    public AddressServiceImpl(AddressRepository addressRepository, @Lazy UserService userService, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.mapper = mapper;
    }


    @Override
    public AddressResponse createAddress(AddressRequest request) {
        var user = userService.getUserById(request.getUserId());
        var address = Address.builder()
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .geoHash(DistanceUtil.toGeoHashBase(request.getLatitude(), request.getLongitude()))
                .user(mapper.map(user, User.class))
                .build();
        address = addressRepository.save(address);
        return mapper.map(address, AddressResponse.class);
    }

    @Override
    public Address updateAddress(Address address, UUID id) {
        var adr = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Address.class, "id", id));
        adr.setAddress(address.getAddress());
        adr.setLatitude(address.getLatitude());
        adr.setLongitude(address.getLongitude());
        adr.setGeoHash(DistanceUtil.toGeoHashBase(address.getLatitude(), address.getLongitude()));
        adr = addressRepository.save(adr);
        return adr;
    }
}
