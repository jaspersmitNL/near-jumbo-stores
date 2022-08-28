package nl.jasper.nearjumbostores.cache;

import io.micronaut.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Cache<T> {
    public io.micronaut.caffeine.cache.Cache<String, T> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    /**
     * @param key      key to store in the cache
     * @param supplier provider for the value to store if it's not in the cache
     * @return the value either from cache or from supplier
     */
    public T remember(String key, Supplier<T> supplier) {
        return cache.get(key, s -> supplier.get());
    }
}
