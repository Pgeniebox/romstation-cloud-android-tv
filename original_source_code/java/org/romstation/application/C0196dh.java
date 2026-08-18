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
import org.romstation.application.virtualcontroller.device.AbstractC0271a;

/* JADX INFO: renamed from: org.romstation.application.dh */
/* JADX INFO: compiled from: ProfileAdapter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dh.class */
public class C0196dh implements JsonDeserializer<C0190db>, JsonSerializer<C0190db> {
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0190db deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject profileObject = element.getAsJsonObject();
        C0190db profile = new C0190db(profileObject.get("name").getAsString());
        if (!profileObject.get("device").isJsonNull()) {
            profile.m768a((AbstractC0271a) context.deserialize(profileObject.getAsJsonObject("device"), AbstractC0271a.class));
        }
        for (JsonElement jsonElement : profileObject.getAsJsonArray("inputs")) {
            AbstractC0199dk input = (AbstractC0199dk) context.deserialize(jsonElement, AbstractC0199dk.class);
            if (input != null) {
                profile.m769c().add(input);
            }
        }
        return profile;
    }

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public JsonElement serialize(C0190db profile, Type type, JsonSerializationContext context) {
        JsonObject profileObject = new JsonObject();
        profileObject.addProperty("name", profile.m765a());
        profileObject.add("device", context.serialize(profile.m767b(), AbstractC0271a.class));
        JsonArray inputsArray = new JsonArray();
        for (AbstractC0199dk input : profile.m769c()) {
            inputsArray.add(context.serialize(input, AbstractC0199dk.class));
        }
        profileObject.add("inputs", inputsArray);
        return profileObject;
    }
}
