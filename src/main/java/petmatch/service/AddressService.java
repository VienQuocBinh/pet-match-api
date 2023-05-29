package petmatch.service;

import petmatch.api.request.AddressRequest;
import petmatch.api.response.AddressResponse;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);
}
