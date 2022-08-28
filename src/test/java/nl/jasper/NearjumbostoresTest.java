package nl.jasper;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import nl.jasper.nearjumbostores.clients.OpenStreetMapClient;
import nl.jasper.nearjumbostores.models.LatLong;
import nl.jasper.nearjumbostores.models.Store;
import nl.jasper.nearjumbostores.services.NearStoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@MicronautTest
class NearjumbostoresTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    NearStoreService nearStoreService;

    @Inject
    private OpenStreetMapClient openStreetMapClient;

    @Test
    void testAppRunning() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void testByLatLong() {
        // test coords in rotterdam
        List<Store> near = this.nearStoreService.getStoresByLatLong(new LatLong(51.92323985646912, 4.55061161344126), 3);
        Logger logger = LoggerFactory.getLogger(NearjumbostoresTest.class);

        Assertions.assertEquals("Rotterdam", near.get(0).getCity());
        Assertions.assertEquals("SE8KYx4XuW4AAAFIoMYYwKxJ", near.get(0).getUuid());

        Assertions.assertEquals("Capelle aan den IJssel", near.get(1).getCity());
        Assertions.assertEquals("tOcKYx4XRn8AAAFIVBsYwKxK", near.get(1).getUuid());

        Assertions.assertEquals("Rotterdam", near.get(2).getCity());
        Assertions.assertEquals("ItMKYx4XHmYAAAFIFnAYwKxK", near.get(2).getUuid());

    }

    @Test
    void testByAddress() {
        var streetMapSearch = openStreetMapClient.search("Boschdijk 262, Eindhoven").blockingFirst().get(0);
        List<Store> near = this.nearStoreService.getStoresByLatLong(streetMapSearch.toLatLong(), 2);
        Logger logger = LoggerFactory.getLogger(NearjumbostoresTest.class);

        Assertions.assertEquals("Eindhoven", near.get(0).getCity());
        Assertions.assertEquals("4oMKYx4Xt9oAAAFImNEYwKxK", near.get(0).getUuid());

        Assertions.assertEquals("Eindhoven", near.get(1).getCity());
        Assertions.assertEquals("_XMKYx4XNGEAAAFIpUsYwKxK", near.get(1).getUuid());

    }



}
