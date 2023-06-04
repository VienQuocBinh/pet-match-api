package petmatch.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petmatch.api.request.AddressRequest;
import petmatch.api.response.AddressResponse;
import petmatch.service.AddressService;
import petmatch.util.DistanceUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;
    private final DistanceUtil distanceUtil;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody @Valid AddressRequest request) {
        return ResponseEntity.status(201).body(addressService.createAddress(request));
    }

    @GetMapping
    public ResponseEntity<Object> getNearbyAddress(double latitude, double longitude, int precision) {
        return ResponseEntity.ok(distanceUtil.findNearbyProfiles(latitude, longitude, precision));
    }
}
