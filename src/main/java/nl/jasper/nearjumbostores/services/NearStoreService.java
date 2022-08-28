package nl.jasper.nearjumbostores.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import nl.jasper.nearjumbostores.cache.Cache;
import nl.jasper.nearjumbostores.models.LatLong;
import nl.jasper.nearjumbostores.models.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class NearStoreService {

    private final Logger logger = LoggerFactory.getLogger(NearStoreService.class);
    // In production, it would be better to cache into a memory DB like Redis. (For simplicity it's just a simple Caffeine cache
    private final Cache<List<Store>> cache = new Cache<>();

    @Inject
    private StoreService storeService;


    /**
     * @param target         location to get n the nearest jumbo stores
     * @param numberOfStores number of stores to find
     * @return list of near stores
     */
    public List<Store> getStoresByLatLong(LatLong target, int numberOfStores) {
        // the cache key is the latlong:numberOfStores
        return this.cache.remember(String.format("%s:%d", target.toString(), numberOfStores), () -> {
            // we sort the list so that all near stores are lower in the list to be able to return a subList of 0-N
            return storeService.getStores().stream().sorted((store1, store2) -> {
                LatLong point1 = LatLong.fromStore(store1);
                LatLong point2 = LatLong.fromStore(store2);

                // store the distance from the target to the store
                store1.setDistance(target.distanceTo(point1));
                store2.setDistance(target.distanceTo(point2));

                return target.distanceTo(point1).compareTo(target.distanceTo(point2));
            }).toList().subList(0, numberOfStores);
        });

    }

}
