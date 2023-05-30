package unit.petmatch.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import petmatch.util.DistanceUtil;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Test
    public void getGeoHashString() {
        double lat = 10.864105243235155;
        double lon = 106.7346579351765;
        System.out.println(DistanceUtil.toGeoHashBase(lat, lon));
    }
}
