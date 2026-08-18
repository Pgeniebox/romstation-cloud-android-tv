package org.romstation.application;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/* JADX INFO: renamed from: org.romstation.application.dg */
/* JADX INFO: compiled from: InputAdapter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/dg.class */
public class C0195dg implements JsonDeserializer<AbstractC0199dk>, JsonSerializer<AbstractC0199dk> {
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public AbstractC0199dk deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        AbstractC0199dk input;
        JsonObject inputObject = element.getAsJsonObject();
        switch (inputObject.get("type").getAsInt()) {
            case 1:
                input = new C0198dj(inputObject.get("id").getAsInt(), inputObject.get("name").getAsString(), inputObject.get("threshold").getAsFloat());
                break;
            case 2:
                input = new C0197di(inputObject.get("id").getAsInt(), inputObject.get("name").getAsString(), inputObject.get("sensitivity").getAsFloat(), inputObject.get("deadZone").getAsFloat());
                break;
            default:
                return null;
        }
        if (!inputObject.get("binding").isJsonNull()) {
            input.m808a(C0159cY.m716a(inputObject.get("binding").getAsString()));
        }
        return input;
    }

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public JsonElement serialize(AbstractC0199dk input, Type type, JsonSerializationContext context) {
        JsonObject inputObject = new JsonObject();
        inputObject.addProperty("id", Integer.valueOf(input.m805c()));
        inputObject.addProperty("name", input.m806d());
        if (input instanceof C0198dj) {
            inputObject.addProperty("type", 1);
            inputObject.addProperty("threshold", Float.valueOf(((C0198dj) input).m803a()));
        } else {
            inputObject.addProperty("type", 2);
            inputObject.addProperty("sensitivity", Float.valueOf(((C0197di) input).m798a()));
            inputObject.addProperty("deadZone", Float.valueOf(((C0197di) input).m800b()));
        }
        if (input.m807e() != null) {
            inputObject.addProperty("binding", input.m807e().toString());
        } else {
            inputObject.addProperty("binding", (String) null);
        }
        return inputObject;
    }
}
