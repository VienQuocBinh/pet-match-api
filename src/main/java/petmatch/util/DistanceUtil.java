package petmatch.util;

import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Component;

@Component
public class DistanceUtil {
    public static String toGeoHashBase(double latitude, double longitude, int precision) {
        return GeoHash
                .withCharacterPrecision(latitude, longitude, precision)
                .toBase32();
    }
}
