package nl.jasper.nearjumbostores.services;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jakarta.inject.Singleton;
import nl.jasper.nearjumbostores.models.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Singleton
public class StoreService {

    private final Logger logger = LoggerFactory.getLogger(StoreService.class);
    private List<Store> stores = new ArrayList<>();

    private void parseStoresJson() {

        logger.info("Parsing stores.json");
        Gson gson = new GsonBuilder().create();

        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/stores.json")))) {
            JsonObject root = gson.fromJson(reader, JsonObject.class);
            JsonArray stores = root.get("stores").getAsJsonArray();
            Type type = new TypeToken<List<Store>>() {
            }.getType();
            this.stores = gson.fromJson(stores, type);
            logger.info("Finished parsing json.");
        } catch (NullPointerException | IOException | JsonSyntaxException e) {
            logger.error("Failed to read stores.json", e);
        }

    }


    public List<Store> getStores() {

        if (stores.isEmpty()) {
            this.parseStoresJson();
        }

        // creating a copy to later sort the list
        List<Store> copy = new ArrayList<>(stores.size());
        copy.addAll(stores);

        return copy;
    }
}
