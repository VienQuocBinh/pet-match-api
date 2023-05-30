package petmatch.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import petmatch.api.request.UserRequest;
import petmatch.api.response.UserResponse;
import petmatch.configuration.exception.EntityNotFoundException;
import petmatch.model.Address;
import petmatch.model.User;
import petmatch.repository.AddressRepository;
import petmatch.repository.UserRepository;
import petmatch.service.AddressService;
import petmatch.service.UserService;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, AddressRepository addressRepository, @Lazy AddressService addressService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.addressService = addressService;
    }


    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", email));

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse updateUser(UserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", request.getEmail()));
        var address = addressRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(Address.class, "email", request.getEmail()));
        address = addressService.updateAddress(
                modelMapper.map(request.getAddress(), Address.class),
                address.getId());

        user.setPhone(request.getPhone());
        user.setAddresses(List.of(address));
        user = userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }
}
