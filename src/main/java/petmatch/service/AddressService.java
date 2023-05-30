package petmatch.service;

import petmatch.api.request.AddressRequest;
import petmatch.api.response.AddressResponse;
import petmatch.model.Address;

import java.util.UUID;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);

    Address updateAddress(Address address, UUID id);
}
