package nl.jasper.nearjumbostores.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OpenStreetMapSearch {
    @JsonProperty
    public double lat;
    @JsonProperty
    public double lon;
    @JsonProperty
    public String display_name;


    public LatLong toLatLong() {
        return new LatLong(lat, lon);
    }
}
