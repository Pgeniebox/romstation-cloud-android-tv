package org.romstation.application;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.logging.Level;
import org.romstation.application.virtualcontroller.device.AbstractC0271a;
import org.romstation.application.virtualcontroller.device.C0272b;
import org.romstation.application.virtualcontroller.device.C0274d;
import org.romstation.application.virtualcontroller.device.DeviceNotFoundException;

/* JADX INFO: renamed from: org.romstation.application.df */
/* JADX INFO: compiled from: DeviceAdapter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/df.class */
public class C0194df implements JsonDeserializer<AbstractC0271a>, JsonSerializer<AbstractC0271a> {
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public AbstractC0271a deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject deviceObject = element.getAsJsonObject();
        switch (deviceObject.get("type").getAsInt()) {
            case 1:
                return C0274d.m1638f();
            case 2:
                try {
                    int index = deviceObject.has("index") ? deviceObject.get("index").getAsInt() : 0;
                    return C0272b.m1621a(index, deviceObject.get("name").getAsString());
                } catch (DeviceNotFoundException exception) {
                    RomStation.m42b().log(Level.WARNING, "failed to deserialize device", (Throwable) exception);
                    return null;
                }
            default:
                return null;
        }
    }

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public JsonElement serialize(AbstractC0271a device, Type type, JsonSerializationContext context) {
        if (device instanceof C0274d) {
            JsonObject deviceObject = new JsonObject();
            deviceObject.addProperty("type", 1);
            return deviceObject;
        }
        if (device instanceof C0272b) {
            JsonObject deviceObject2 = new JsonObject();
            deviceObject2.addProperty("type", 2);
            deviceObject2.addProperty("index", Integer.valueOf(((C0272b) device).m1614e()));
            deviceObject2.addProperty("name", ((C0272b) device).m1615f().getName());
            return deviceObject2;
        }
        return null;
    }
}
