package org.romstation.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/* JADX INFO: renamed from: org.romstation.application.de */
/* JADX INFO: compiled from: ConfigAdapter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/de.class */
public class C0193de implements JsonDeserializer<C0160cZ>, JsonSerializer<C0160cZ> {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0160cZ deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject configJson = element.getAsJsonObject();
        C0160cZ config = new C0160cZ(configJson.get("name").getAsString());
        if (!configJson.get("image").isJsonNull()) {
            config.m724b(configJson.get("image").getAsString());
        }
        config.m726a((C0190db) context.deserialize(configJson.getAsJsonObject("template"), C0190db.class));
        for (JsonElement jsonElement : configJson.getAsJsonArray("profiles")) {
            config.m729e().add(context.deserialize(jsonElement, C0190db.class));
        }
        try {
            if (!configJson.get("current_profile").isJsonNull()) {
                config.m728b(config.m729e().get(configJson.get("current_profile").getAsInt()));
            }
        } catch (IndexOutOfBoundsException exception) {
            exception.printStackTrace();
        }
        return config;
    }

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public JsonElement serialize(C0160cZ config, Type type, JsonSerializationContext context) {
        int index;
        JsonObject configJson = new JsonObject();
        configJson.addProperty("name", config.m721a());
        configJson.addProperty("image", config.m723b());
        configJson.add("template", context.serialize(config.m725c(), C0190db.class));
        JsonArray profilesArray = new JsonArray();
        for (C0190db profile : config.m729e()) {
            profilesArray.add(context.serialize(profile, C0190db.class));
        }
        configJson.add("profiles", profilesArray);
        configJson.add("current_profile", (JsonElement) null);
        if (config.m727d() != null && !config.m729e().isEmpty() && (index = config.m729e().indexOf(config.m727d())) != -1) {
            configJson.addProperty("current_profile", Integer.valueOf(index));
        }
        return configJson;
    }
}
