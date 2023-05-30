package petmatch.util;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Component;

@Component
public class DistanceUtil {
    public static String toGeoHashBase(double latitude, double longitude) {
        return GeoHash
                .withCharacterPrecision(latitude, longitude, 12)
                .toBase32();
    }
}
