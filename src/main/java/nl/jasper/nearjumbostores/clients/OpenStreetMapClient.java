package nl.jasper.nearjumbostores.clients;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.rxjava3.core.Flowable;
import nl.jasper.nearjumbostores.models.OpenStreetMapSearch;

import java.util.List;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;


@Client("https://nominatim.openstreetmap.org/")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = " application/json")
/*
    API Client for OpenStreetMap
 */
public interface OpenStreetMapClient {

    /**
     * @param address address to lookup
     * @return OpenStreetMapSearch ({@link nl.jasper.nearjumbostores.models.LatLong}
     */
    @Get(value = "/search?q={address}&format=json&polygon=1&addressdetails=1")
    Flowable<List<OpenStreetMapSearch>> search(String address);

}
