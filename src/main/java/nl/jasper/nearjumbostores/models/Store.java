package nl.jasper.nearjumbostores.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Schema(name = "Store")
@ToString
public class Store {
    @JsonProperty
    private String city;
    @JsonProperty
    private String postalCode;
    @JsonProperty
    private String street;
    @JsonProperty
    private String street2;
    @JsonProperty
    private String street3;
    @JsonProperty
    private String addressName;
    @JsonProperty
    private String uuid;
    @JsonProperty
    private double longitude;
    @JsonProperty
    private double latitude;
    @JsonProperty
    private String complexNumber;
    @JsonProperty
    private boolean showWarningMessage;
    @JsonProperty
    private String todayOpen;
    @JsonProperty
    private String locationType;
    @JsonProperty
    private boolean collectionPoint;
    @JsonProperty
    private String sapStoreID;
    @JsonProperty
    private String todayClose;
    @JsonProperty
    @Setter
    private double distance;
}
