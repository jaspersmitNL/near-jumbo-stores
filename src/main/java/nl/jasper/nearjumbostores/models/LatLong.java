package nl.jasper.nearjumbostores.models;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class LatLong {
    private final double latitude, longitude;

    public static LatLong fromStore(Store store) {
        return new LatLong(store.getLatitude(), store.getLongitude());
    }

    public Double distanceTo(LatLong point) {
        double lon = longitude - point.longitude;
        double lat = latitude - point.latitude;
        return Math.sqrt(lon * lon + lat * lat);
    }
}
