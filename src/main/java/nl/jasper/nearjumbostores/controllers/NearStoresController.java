package nl.jasper.nearjumbostores.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.problem.HttpStatusType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import nl.jasper.nearjumbostores.clients.OpenStreetMapClient;
import nl.jasper.nearjumbostores.models.LatLong;
import nl.jasper.nearjumbostores.models.Store;
import nl.jasper.nearjumbostores.services.NearStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zalando.problem.Problem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;


@Controller()
public class NearStoresController {

    private final Logger logger = LoggerFactory.getLogger(NearStoresController.class);

    @Inject
    private OpenStreetMapClient openStreetMapClient;

    @Inject
    private NearStoreService nearStoreService;


    /***
     * @param lat latitude
     * @param lon longitude
     * @param numOfStores number of stores to find near latitude/longitude
     * @return return list of N near stores
     */
    @Get("/latlong")
    @ApiResponse(
            responseCode = "200",
            description = "Near jumbo stores",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = Store.class))
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid address"
    )
    public HttpResponse<?> byLatLong(@QueryValue @NotNull double lat, @NotNull @QueryValue double lon, @QueryValue(defaultValue = "5") int numOfStores) {
        this.logger.info("byLatLong {} {}", lat, lon);
        return HttpResponse.ok(nearStoreService.getStoresByLatLong(new LatLong(lat, lon), numOfStores));
    }


    /***
     * @param address address to convert to latitude/longitude
     * @param numOfStores number of stores to find near latitude/longitude
     * @return return list of N near stores
     */
    @Get("/address")
    @ApiResponse(
            responseCode = "200",
            description = "Near jumbo stores",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = Store.class))
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid address"
    )
    public HttpResponse<?> byAddress(@QueryValue @NotBlank String address, @QueryValue(defaultValue = "5") int numOfStores) {
        this.logger.info("byAddress {}", address);

        var openStreetMapSearches = openStreetMapClient.search(address).blockingFirst();

        if (openStreetMapSearches.isEmpty()) {
            throw Problem.builder()
                    .withType(URI.create("badrequest"))
                    .withTitle("Bad Request")
                    .withStatus(new HttpStatusType(HttpStatus.BAD_REQUEST))
                    .withDetail(String.format("Address %s is invalid", address))
                    .with("address", address)
                    .build();
        }

        return HttpResponse.ok(nearStoreService.getStoresByLatLong(openStreetMapSearches.get(0).toLatLong(), numOfStores));
    }

}
