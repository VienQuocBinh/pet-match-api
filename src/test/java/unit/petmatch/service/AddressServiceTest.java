package unit.petmatch.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import petmatch.repository.AddressRepository;
import petmatch.repository.UserRepository;
import petmatch.util.DistanceUtil;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private DistanceUtil distanceUtil;

    @Test
    public void getGeoHashString() {
        double lat = 10.864105243235155;
        double lon = 106.7346579351765;
        int precision = 12;
        System.out.println(distanceUtil.toGeoHashBase(lat, lon, precision));
        Assertions.assertEquals("w3gvuccb1b12", distanceUtil.toGeoHashBase(lat, lon, precision));
    }

    @Test
    public void testGetAllNearbyUsers() {
        double lat = 10.863363664967906;
        double lon = 106.74614785584288;
        int precision = 4;
        distanceUtil.findNearbyProfiles(lat, lon, precision).forEach(p -> System.out.println(p.getName()));
    }
}
